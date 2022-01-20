package exparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Lexer {

    private final List<LexemeFormat> formats;
    private final Pattern p;

    public Lexer(List<LexemeFormat> formats) {
        this.formats = formats;

        CharSequence[] format = formats.stream().map(LexemeFormat::getFormat).toArray(CharSequence[]::new);
        p = Pattern.compile(String.join("|", format));

    }

    public List<TreeNode> getParsed(String input) {
        Matcher m = p.matcher(input);
        List<TreeNode> lexed = new ArrayList<>();
        while (m.find())
            for(LexemeFormat lex: formats)
                if(lex.matches(m.group())){
                    lexed.add(lex.createLexeme(m.group()));
                    break;
                }
        return lexed;
    }


}
