package zsy.jason.util.ast;

import java.util.ArrayList;

public class AbstractSyntaxTree {
    private NodeType type;
    private String name;
    private ArrayList<AbstractSyntaxTree> nodes;
    private String strValue;
    private ArrayList<AbstractSyntaxTree> arrValue;

    public AbstractSyntaxTree(NodeType type, String name) {
        this.type = type;
        this.name = name;
        if (type == NodeType.OBJECT)
            this.nodes = new ArrayList<>();
        else if (type == NodeType.STRING || type == NodeType.NUMBER)
            this.strValue = "";
        else if (type == NodeType.ARRAY)
            this.arrValue = new ArrayList<>();
    }

    public NodeType getType() {
        return type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public ArrayList<AbstractSyntaxTree> getNodes() {
        return nodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public ArrayList<AbstractSyntaxTree> getArrValue() {
        return arrValue;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("name=").append(name).append(", ");
        stringBuilder.append("type=").append(type);
        if (type == NodeType.STRING) {
            stringBuilder.append(", strValue=(").append(strValue).append(")");
        } else if (type == NodeType.NUMBER) {
            stringBuilder.append(", value=(").append(strValue).append(")");
        } else if (type == NodeType.ARRAY) {
            stringBuilder.append(", array=(");
            for (int i = 0; i < arrValue.size(); i++) {
                stringBuilder.append(arrValue.get(i));
                if (i != (arrValue.size() - 1)) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(")");
        } else if (type == NodeType.OBJECT) {
            stringBuilder.append(", value=(");
            for (int i = 0; i < nodes.size(); i++) {
                stringBuilder.append(nodes.get(i));
                if (i != (nodes.size() - 1)) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append(")");
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
