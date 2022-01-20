package exparser;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static exparser.Lexeme.*;
public enum LexemeType implements LexemeFormat{
    OP("[+-]",Op::new),
    MULT("[*]",Mult::new),
    DIV("[/]",Div::new),
    INT("[0-9]{1,9}", Int::new),
    L_BRAC("[(]", LBrac::new),
    R_BRAC("[)]", RBrac::new);

    String format;
    Pattern p;
    private Function<String, Lexeme> createNode;

    LexemeType(String s, Function<String,Lexeme> createNode) {
        format =s;
        p = Pattern.compile(s);
        this.createNode = createNode;
    }
    public Matcher match(CharSequence input){
        return p.matcher(input);
    }

    public String getFormat() {
        return format;
    }

    public boolean matches(String input){
        return input.matches(format);
    }

    public Lexeme createLexeme(String input){
        return createNode.apply(input);
    }
}
