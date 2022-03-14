package zsy.jason.util;

import zsy.jason.exception.SyntaxException;
import zsy.jason.util.ast.AbstractSyntaxTree;
import zsy.jason.util.ast.NodeType;
import zsy.jason.util.parser.JasonLexer;
import zsy.jason.util.parser.JasonToken;
import zsy.jason.util.parser.JasonTokenType;

import java.util.ArrayList;
import java.util.List;

import static zsy.jason.util.parser.JasonTokenType.*;

public class JasonParser {
    private final String content;
    private int position = 0;

    public JasonParser(String content) {
        this.content = content;
    }

    public AbstractSyntaxTree parse() throws SyntaxException {
        var tokens = JasonLexer.lex(content);
        if (tokens.isEmpty()) {
            throw new SyntaxException("unexpected empty json");
        }
        AbstractSyntaxTree root = parseObject(tokens, "jason_root");
        return root;
    }

    private JasonToken eat(List<JasonToken> tokens, JasonTokenType t) throws SyntaxException {
        JasonToken tmp = tokens.get(position);
        if (tmp.getType() == t) {
            position++;
            return tmp;
        } else {
            throw new SyntaxException();
        }
    }

    private AbstractSyntaxTree parseObject(List<JasonToken> tokens, String name) throws SyntaxException {
        AbstractSyntaxTree tree = new AbstractSyntaxTree(NodeType.OBJECT, name);
        eat(tokens, JasonTokenType.LEFT_BRACE);
        if (tokens.get(position).getType() == JasonTokenType.RIGHT_BRACE) {
            position++;
            return tree;
        }
        parseDefinition(tokens, tree);
        while (true) {
            if (tokens.get(position).getType() == JasonTokenType.RIGHT_BRACE) {
                position++;
                break;
            }
            eat(tokens, JasonTokenType.COMMA);
            parseDefinition(tokens, tree);
        }
        return tree;
    }

    private void parseDefinition(List<JasonToken> tokens, AbstractSyntaxTree tree) throws SyntaxException {
        JasonToken name = eat(tokens, STRING);
        eat(tokens, JasonTokenType.COLON);
        switch (tokens.get(position).getType()) {
            case STRING -> {
                tree.getNodes().add(parseString(tokens, name.getValue()));
            }
            case NUMBER -> {
                tree.getNodes().add(parseNumber(tokens, name.getValue()));
            }
            case LEFT_BRACKET -> {
                tree.getNodes().add(parseArray(tokens, name.getValue()));
            }
            case LEFT_BRACE -> {
                tree.getNodes().add(parseObject(tokens, name.getValue()));
            }
            default -> {
                throw new SyntaxException();
            }
        }
    }

    private AbstractSyntaxTree parseString(List<JasonToken> tokens, String name) throws SyntaxException {
        AbstractSyntaxTree tree = new AbstractSyntaxTree(NodeType.STRING, name);
        tree.setStrValue(eat(tokens, JasonTokenType.STRING).getValue());
        return tree;
    }

    private AbstractSyntaxTree parseNumber(List<JasonToken> tokens, String name) throws SyntaxException {
        AbstractSyntaxTree tree = new AbstractSyntaxTree(NodeType.NUMBER, name);
        tree.setStrValue(eat(tokens, JasonTokenType.NUMBER).getValue());
        return tree;
    }

    private AbstractSyntaxTree parseArray(List<JasonToken> tokens, String name) throws SyntaxException {
        AbstractSyntaxTree tree = new AbstractSyntaxTree(NodeType.ARRAY, name);
        eat(tokens, JasonTokenType.LEFT_BRACKET);
        if (tokens.get(position).getType() == JasonTokenType.RIGHT_BRACKET) {
            position++;
            return tree;
        }
        tree.getArrValue().addAll(parseArrayItem(tokens));
        while (true) {
            if (tokens.get(position).getType() == JasonTokenType.RIGHT_BRACKET) {
                position++;
                break;
            }
            eat(tokens, JasonTokenType.COMMA);
            tree.getArrValue().addAll(parseArrayItem(tokens));
        }
        return tree;
    }

    private List<AbstractSyntaxTree> parseArrayItem(List<JasonToken> tokens) throws SyntaxException {
        var array = new ArrayList<AbstractSyntaxTree>();
        JasonToken token = tokens.get(position);
        switch (token.getType()) {
            case NUMBER -> {
                array.add(parseNumber(tokens, "[anonymous]"));
            }
            case STRING -> {
                array.add(parseString(tokens, "[anonymous]"));
            }
            case LEFT_BRACKET -> {
                array.add(parseArray(tokens, "[anonymous]"));
            }
            case LEFT_BRACE -> {
                array.add(parseObject(tokens, "[anonymous]"));
            }
            default -> {
                throw new SyntaxException();
            }
        }
        return array;
    }
}
