package zsy.jason.util.parser;

public enum Token {
    // {
    LEFT_BRACE,
    // }
    RIGHT_BRACE,
    // [
    LEFT_BRACKET,
    // ]
    RIGHT_BRACKET,
    // ,
    COMMA,
    // :
    COLON,
    // ".*"
    STRING,
    // [0-9]+
    NUMBER,
    END;

    public String value;
}
