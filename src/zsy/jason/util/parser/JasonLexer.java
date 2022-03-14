package zsy.jason.util.parser;

import zsy.jason.exception.SyntaxException;

import java.util.ArrayList;
import java.util.List;

public class JasonLexer {
    private JasonLexer() {
    }

    public static List<JasonToken> lex(String content) throws SyntaxException {
        var tokens = new ArrayList<JasonToken>();
        var filechrs = content.toCharArray();
        int i = 0;
        while (i < filechrs.length) {
            switch (filechrs[i]) {
                case ' ', '\r', '\n', '\f', '\t' -> i++;
                case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    i = readNumber(filechrs, i, tokens);
                }
                case '{' -> {
                    tokens.add(new JasonToken(JasonTokenType.LEFT_BRACE, "{"));
                    i++;
                }
                case '}' -> {
                    tokens.add(new JasonToken(JasonTokenType.RIGHT_BRACE, "}"));
                    i++;
                }
                case '[' -> {
                    tokens.add(new JasonToken(JasonTokenType.LEFT_BRACKET, "["));
                    i++;
                }
                case ']' -> {
                    tokens.add(new JasonToken(JasonTokenType.RIGHT_BRACKET, "]"));
                    i++;
                }
                case '"' -> {
                    i = readString(filechrs, i + 1, tokens);
                }
                case ',' -> {
                    tokens.add(new JasonToken(JasonTokenType.COMMA, ","));
                    i++;
                }
                case ':' -> {
                    tokens.add(new JasonToken(JasonTokenType.COLON, ":"));
                    i++;
                }
                case '\'' -> throw new SyntaxException("unexpected ' symbol");
                default -> throw new SyntaxException();
            }
        }
        tokens.add(new JasonToken(JasonTokenType.END, "END"));
        return tokens;
    }

    private static int readString(char[] filechrs, int position, ArrayList<JasonToken> tokens) throws SyntaxException {
        StringBuilder str = new StringBuilder();
        while (position < filechrs.length) {
            switch (filechrs[position]) {
                case '\\' -> {
                    if (!((position + 1) < filechrs.length)) {
                        throw new SyntaxException();
                    }
                    position++;
                    if (position == 'n') str.append('\n');
                    else if (position == 't') str.append('\t');
                    else if (position == 'r') str.append('\r');
                    else if (position == 'f') str.append('\f');
                    else if (position == '"') str.append('\"');
                    else if (position == '\'') str.append('\'');
                    position++;
                }
                case '"' -> {
                    JasonToken token = new JasonToken(JasonTokenType.STRING, str.toString());
                    tokens.add(token);
                    position++;
                    return position;
                }
                default -> {
                    str.append(filechrs[position]);
                    position++;
                }
            }
        }
        throw new SyntaxException();
    }

    private static int readNumber(char[] filechrs, int position, ArrayList<JasonToken> tokens) throws SyntaxException {
        StringBuilder numStr = new StringBuilder();
        boolean negative = false;
        boolean hasPoint = false;
        while (position < filechrs.length) {
            switch (filechrs[position]) {
                case '-' -> {
                    if (!negative) {
                        negative = true;
                        numStr.append(filechrs[position]);
                        position++;
                    } else {
                        throw new SyntaxException();
                    }
                }
                case '.' -> {
                    if (!hasPoint) {
                        hasPoint = true;
                        numStr.append(filechrs[position]);
                        position++;
                    } else {
                        throw new SyntaxException();
                    }
                }
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    numStr.append(filechrs[position]);
                    position++;
                }
                default -> {
                    JasonToken token = new JasonToken(JasonTokenType.NUMBER, numStr.toString());
                    tokens.add(token);
                    return position;
                }
            }
        }
        throw new SyntaxException();
    }
}
