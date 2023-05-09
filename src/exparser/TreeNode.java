package exparser;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface TreeNode {

    Object getValue();
    List<? extends TreeNode> getChildren();

    default int childCount(){
        return getChildren().stream().mapToInt(TreeNode::childCount).sum()+1;
    }
    
    public static <T> TreeNode createTreeNode(T t, Function<T,?> getValue, Function<T,List<T>> getChildren){
        return new TreeNode() {
            @Override
            public Object getValue() {
                return getValue.apply(t);
            }

            @Override
            public List<TreeNode> getChildren() {
                return getChildren.apply(t).stream().map(n->createTreeNode(n,getValue,getChildren)).collect(Collectors.toList());
            }
        };
    }
}
