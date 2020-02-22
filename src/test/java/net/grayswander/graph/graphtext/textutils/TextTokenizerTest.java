package net.grayswander.graph.graphtext.textutils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class TextTokenizerTest {

    private TextTokenizer tokenizer;
    private String text = "The Spring framework is a very vast project that supports building modern applications, mainly enterprise web server applications. Have a look through a list of in-depth tutorials on the various Spring modules, including Spring MVC, Spring Security, Spring Cloud and many more.";

    private InputStream inputStream;

    @BeforeEach
    void setUp() {
        inputStream = new ByteArrayInputStream(text.getBytes());
        tokenizer = new TextTokenizer(inputStream);
    }

    @Test
    public void tokenizerTest() {
        while (tokenizer.hasNext()) {
            System.out.println(tokenizer.next());
        }
    }

    @SneakyThrows
    @AfterEach
    void tearDown() {
        inputStream.close();
    }
}