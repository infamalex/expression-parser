package exparser;

import java.util.List;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Stream;
import static exparser.ParserRule.rule;

public class Parser {
    List<ParserRule> rules = List.of(
            rule((Function<Node[], Node.IntNode>) (Node.IntNode::new), Lexeme.Int.class,Node.class),
            rule(Node.ExpNode.class, Lexeme.Op.class, Node.ExpNode.class, Lexeme.Div.class),
            rule(Node.ExpNode.class, Lexeme.Op.class, Node.ExpNode.class, Lexeme.Mult.class),
            rule(Node.ExpNode.class, Lexeme.Mult.class, Node.ExpNode.class, Lexeme.Div.class),
            rule(Node.Brackets::new, Lexeme.LBrac.class, Node.ExpNode.class, Lexeme.RBrac.class, Node.class),
            rule(Node.OpNode::new, Node.ExpNode.class, Lexeme.Op.class, Node.ExpNode.class, Node.class),
            rule(Node.OpNode::new, Node.ExpNode.class, Lexeme.Mult.class, Node.ExpNode.class, Node.class),
            rule(Node.OpNode::new, Node.ExpNode.class, Lexeme.Div.class, Node.ExpNode.class, Node.class),
            rule(Node.UnaryNode::new, Lexeme.Op.class, Node.ExpNode.class, Node.class)

            );
    private Lexer l;

    public Parser(Lexer l){

        this.l = l;
    }
    public TreeNode parse(String input){
        Stack<TreeNode> nodes = new Stack<>();
        var iter = Stream.concat(l.getParsed(input).stream(),Stream.generate(Node.TerminalNode::new)).iterator();
        nodes.push(iter.next());
        TreeNode lookAhead = iter.next();
        outer: while (true){
            for (ParserRule rule: rules) {
                if(nodes.size()<rule.getArgLen()) continue ;
                TreeNode[] tokens = nodes.subList(nodes.size() - rule.getArgLen(), nodes.size()).toArray(Node[]::new);
                if(rule.Test(tokens,lookAhead)) {
                    if (rule.getAction() == ParserRule.Action.PUSH) {
                        nodes.push(lookAhead);
                            lookAhead = iter.next();
                    } else {
                        for (int i = 0; i < rule.getArgLen(); i++)
                            nodes.pop();
                            nodes.push(rule.reduce(tokens));

                    }

                    continue outer;
                }
            }
            if(!(lookAhead instanceof Node.TerminalNode))
                nodes.push(lookAhead);
            lookAhead = iter.next();

            if(nodes.size() == 1)break;
        }
        return nodes.pop();
    }
}
