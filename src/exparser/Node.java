package exparser;

import java.util.Collections;
import java.util.List;

public abstract class Node implements TreeNode{
    public abstract Object getValue();
    public List<Node> getChildren(){
        return Collections.emptyList();
    }
    public static abstract class ExpNode extends Node{

    }
    public static class IntNode extends ExpNode{
        Node value;
        public IntNode(Node[] n){
            //value = Integer.valueOf((String)n[0].getValue());
            value = n[0];
        }

        @Override
        public List<Node> getChildren() {
            return List.of(value);
        }

        @Override
        public Object getValue() {
            return "Integer";
        }

        @Override
        public String toString() {
            return "Integer";
        }
    }

    public static class OpNode extends ExpNode{
        private Node left,right, op;
        public OpNode(Node[] i){
            op = i[1];
            left = i[0];
            right = i[2];
        }

        @Override
        public List<Node> getChildren() {
            return List.of(left,op,right);
        }

        @Override
        public Object getValue() {
            return "Operator";
        }

        @Override
        public String toString() {
            return String.format("(%s %s %s)",left, op,right);
        }
    }

    public static class Brackets extends ExpNode{
        Node left,exp,right;
        public Brackets(Node[] n){
            left= n[0];
            exp=n[1];
            right=n[2];
        }
        @Override
        public Object getValue() {
            return "Brackets";
        }

        @Override
        public List<Node> getChildren() {
            return List.of(left,exp,right);
        }

        @Override
        public String toString() {
            return getValue().toString();
        }
    }

    public static class UnaryNode extends ExpNode{

        private final Node sign;
        private final Node exp;

        public UnaryNode(Node[] n){
            sign = n[0];
            exp = n[1];
        }
        @Override
        public Object getValue() {
            return "Unary";
        }

        @Override
        public List<Node> getChildren() {
            return List.of(sign,exp);
        }
    }
    public static class TerminalNode extends Node{

        @Override
        public Object getValue() {
            return null;
        }
    }
}
