package exparser;

import java.util.function.BiPredicate;
import java.util.function.Function;

public class ParserRule {
    private final Action action;
    private final BiPredicate test;
    private final Function reduce;
    private final int argLen;
    private Class[] types;

    public enum Action{PUSH,REDUCE}
    private ParserRule(Action action, BiPredicate test, Function reduce, Class...types) {

        this.action = action;
        this.test = test;
        this.reduce = reduce;
        this.argLen = types.length - 1;
        this.types = types;
    }
    /*public static ParserRule rule(BiPredicate<? extends TreeNode[],? extends TreeNode> test,
                                  Function<? extends TreeNode[],? extends TreeNode> reduce, Class<? extends TreeNode>...types){
        return  new ParserRule(Action.REDUCE,test,reduce,types);
    }
    public static ParserRule rule(BiPredicate<? extends TreeNode[],? extends TreeNode> test,
                                  Class<? extends TreeNode>...types){
        return  new ParserRule(Action.PUSH,test,null,types);
    }*/
    public static ParserRule rule(Class<? extends TreeNode>...types){
        return  new ParserRule(Action.PUSH,(u,v)->true,null,types);
    }
    public static <T> ParserRule rule(Function<T[],? extends T> reduce, Class<? extends T>...types){
        return  new ParserRule(Action.REDUCE,(u,v)->true,reduce,types);
    }


    public Action getAction() {
        return action;
    }

    public boolean Test(TreeNode[] tokens, TreeNode lookAhead) {
        for (int i = 0; i < argLen; i++) {
            if (!types[i].isAssignableFrom(tokens[i].getClass()))
                return false;
        }
        return types[argLen].isAssignableFrom(lookAhead.getClass())&&tokens.length == argLen && test.test(tokens,lookAhead);
    }

    public TreeNode reduce(TreeNode[] tokens) {
        if(tokens.length!=argLen || reduce==null)
            throw new IllegalArgumentException();
        return (TreeNode)reduce.apply(tokens);
    }

    public int getArgLen() {
        return argLen;
    }
}
