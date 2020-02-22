package net.grayswander.graph.graphtext.service;

import net.grayswander.graph.graphtext.textutils.TextTokenizer;
import org.neo4j.driver.*;
import org.springframework.stereotype.Service;

import static org.neo4j.driver.Values.parameters;

@Service
public class TextLoaderService {
    private final Driver driver;

    public TextLoaderService(Driver driver) {
        this.driver = driver;
    }

    public void load(String name, TextTokenizer tokenizer) {
        try (Session session = driver.session()) {
            session.writeTransaction(new TransactionWork<Void>() {
                @Override
                public Void execute(Transaction transaction) {
                    Record textNodeRecord = transaction.run(
                            "MERGE (s: Sequence {name: \"TextSequence\"})\n" +
                                    "ON CREATE SET s.current = 0\n" +
                                    "ON MATCH SET s.current = s.current + 1\n" +
                                    "WITH s.current AS nextTextId\n" +
                                    "CREATE (t:Text{name: $name, textId: nextTextId})\n" +
                                    "RETURN id(t), t.textId",
                            parameters("name", name)
                    ).single();

                    long id = textNodeRecord.get(0).asLong();
                    long textId = textNodeRecord.get(1).asLong();

                    long index = 0;
                    for (TextTokenizer it = tokenizer; it.hasNext(); ) {
                        TextTokenizer.Token token = it.next();

                        id = transaction.run(
                                "MATCH (h) WHERE ID(h) = $id MERGE (t:Token {value: $value, type: $type}) CREATE (h)-[e:Next {textId: $textId, index: $index}]->(t) RETURN id(t)",
                                parameters(
                                        "id", id,
                                        "textId", textId,
                                        "index", ++index,
                                        "value", token.getText(),
                                        "type", token.getType().name()
                                        )
                        ).single().get(0).asLong();

                        System.out.println(id);
                    }

                    transaction.run(
                            "MATCH (h) WHERE ID(h) = $id MERGE (t:End) CREATE (h)-[e:Next {textId: $textId, index: $index}]->(t) RETURN id(t)",
                            parameters(
                                    "id", id,
                                    "textId", textId,
                                    "index", ++index

                            )
                    );


                    return null;
                }
            });
        }
    }
}
