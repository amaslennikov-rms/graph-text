package net.grayswander.graph.graphtext.service;

import lombok.SneakyThrows;
import net.grayswander.graph.graphtext.textutils.TextTokenizer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

class TextLoaderServiceTest {

    private TextTokenizer tokenizer1;
    private String text1 = "The Spring framework is a very vast project that supports building modern applications, mainly enterprise web server applications. Have a look through a list of in-depth tutorials on the various Spring modules, including Spring MVC, Spring Security, Spring Cloud and many more.";

    private InputStream inputStream1;

    private TextTokenizer tokenizer2;
    private String text2 = "She was looking at the lack of in-system traffic between Novo Lar and Cova, the uninhabitable fourth planet that served as the industrial heart of the system. She was looking at the debris patterns from several short and ugly engagements between the Interstellar Navy of the Republic of Faith and Reason and the Royal Martian Navy of the Protectorate of the Mage-King of Mars. Stand was there to scout the system for the Royal Martian Navy, to see if the Republic Interstellar Navy had reinforced the battered survivors of the Battle of Ardennes.";

    private InputStream inputStream2;

    private Driver driver;

    private TextLoaderService textLoaderService;

    @BeforeEach
    void setUp() {
        inputStream1 = new ByteArrayInputStream(text1.getBytes());
        tokenizer1 = new TextTokenizer(inputStream1);
        inputStream2 = new ByteArrayInputStream(text2.getBytes());
        tokenizer2 = new TextTokenizer(inputStream2);
        driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "123456"));
        textLoaderService = new TextLoaderService(driver);
    }


    @SneakyThrows
    @AfterEach
    void tearDown() {
        inputStream1.close();
        inputStream2.close();
        driver.close();
    }

    @Test
    void load() {
        textLoaderService.load("Text One", tokenizer1);
        textLoaderService.load("Text Two", tokenizer2);
    }
}