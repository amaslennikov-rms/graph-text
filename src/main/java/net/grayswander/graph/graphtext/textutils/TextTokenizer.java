package net.grayswander.graph.graphtext.textutils;

import lombok.SneakyThrows;
import lombok.Value;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class TextTokenizer implements Iterator<TextTokenizer.Token> {
    private BufferedReader reader;
    private long index = 0;
    private long start = 0;
    private long current = 0;
    private StringBuffer textBuffer;
    private Queue<Token> queue = new LinkedList<>();

    public TextTokenizer(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @SneakyThrows
    protected void prepareNextToken() {
        this.start = this.current;
        this.textBuffer = new StringBuffer();


        int character;
        while( queue.size() == 0 && (character = reader.read()) != -1) {

            String symbol = Character.toString(character);

            switch (symbol) {
                case " ":
                case "\n":
                case "\t":
                    if(textBuffer.length() > 0) {
                        this.queue.add(new Token(textBuffer.toString(), Token.Type.WORD, ++index, start, current));
                    }
                    start = current+1;
                    this.textBuffer = new StringBuffer();
                    break;
                case ".":
                case "!":
                case "?":
                    if(textBuffer.length() > 0) {
                        this.queue.add(new Token(textBuffer.toString(), Token.Type.WORD, ++index, start, current));
                    }
                    this.queue.add(new Token(symbol, Token.Type.SENTENCE_END_PUNCTUATION, ++index, current+1,current+1));
                    start = current+1;
                    this.textBuffer = new StringBuffer();
                    break;
                case  ",":
                case ";":
                case ":":
                    if(textBuffer.length() > 0) {
                        this.queue.add(new Token(textBuffer.toString(), Token.Type.WORD, ++index, start, current));
                    }
                    this.queue.add(new Token(symbol, Token.Type.IN_SENTENCE_PUNCTUATION, ++index, current+1,current+1));
                    start = current+1;
                    this.textBuffer = new StringBuffer();
                    break;
                case  "\"":
                case "'":
                case "`":
                    if(textBuffer.length() > 0) {
                        this.queue.add(new Token(textBuffer.toString(), Token.Type.WORD, ++index, start, current));
                    }
                    this.queue.add(new Token(symbol, Token.Type.QUOTES, ++index, current+1,current+1));
                    start = current+1;
                    this.textBuffer = new StringBuffer();
                    break;
                case  "(":
                case ")":
                case "[":
                case "]":
                case "{":
                case "}":
                    if(textBuffer.length() > 0) {
                        this.queue.add(new Token(textBuffer.toString(), Token.Type.WORD, ++index, start, current));
                    }
                    this.queue.add(new Token(symbol, Token.Type.PARENTHESES, ++index, current+1,current+1));
                    start = current+1;
                    this.textBuffer = new StringBuffer();
                    break;
                default:
                    textBuffer.append(symbol);
                    break;
            }

            if (character == -1) {
                if(textBuffer.length() > 0) {
                    this.queue.add(new Token(textBuffer.toString(), Token.Type.WORD, ++index, start, current));
                }
            }

            current++;
        }
    }

    @Override
    public boolean hasNext() {
        this.prepareNextToken();
        return this.queue.size() > 0;
    }

    @Override
    public Token next() {
        Token r = queue.remove();
        this.prepareNextToken();
        return r;
    }


    @Value
    public static class Token {
        private String text;
        private Type type;
        private long index;
        private long start;
        private long end;

        public enum Type {WORD, IN_SENTENCE_PUNCTUATION, SENTENCE_END_PUNCTUATION, QUOTES, PARENTHESES, EOF}
//        private static enum Capitalization {LOWER, UPPER, Capitalized}
    }

}
