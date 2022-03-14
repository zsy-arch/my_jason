package zsy.jason.util.parser;

public class JasonToken {
    private JasonTokenType type;
    private String value;

    public JasonToken(JasonTokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public JasonTokenType getType() {
        return type;
    }

    public void setType(JasonTokenType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "JasonToken{" +
                "type=" + type +
                ", value='" + value + '\'' +
                '}';
    }
}
