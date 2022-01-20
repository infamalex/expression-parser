package exparser;

import java.util.Collections;
import java.util.List;

public abstract class Lexeme extends Node{

    private String value;

    public Lexeme(String value){

        this.value = value;
    }
    @Override
    public final List<Node> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static class Mult extends Lexeme{

        public Mult(String value) {
            super(value);
        }
    }
    public static class Op extends Lexeme{

        public Op(String value) {
            super(value);
        }
    }
    public static class Div extends Lexeme{

        public Div(String value) {
            super(value);
        }
    }
    public static class Int extends Lexeme{

        public Int(String value) {
            super(value);
        }
    }
    public static class LBrac extends Lexeme{

        public LBrac(String value) {
            super(value);
        }
    }
    public static class RBrac extends Lexeme{

        public RBrac(String value) {
            super(value);
        }
    }



}
