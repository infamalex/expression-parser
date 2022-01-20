package exparser;

public interface LexemeFormat {
    public String getFormat();
    boolean matches(String input);
    TreeNode createLexeme(String input);
}
