package exparser;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TreeView extends JPanel {

    private final JLabel label;
    private final List<TreeView> childNodes;
    private final static Color bg = new Color(0, true);

    public TreeView(TreeNode root){
        super(new GridBagLayout());
        Border b = BorderFactory.createEtchedBorder();
        setBorder(b);
        setBackground(bg);
        List<? extends TreeNode> children = root.getChildren();
        var childCounts = children.stream().mapToInt(TreeNode::childCount).toArray();
        double totalChildren = Arrays.stream(childCounts).sum();

        Insets insets = new Insets(0, 0, 0, 0);
        GridBagConstraints valuec = new GridBagConstraints(0, 0, children.size(), 1, 1, 0.1, GridBagConstraints.CENTER, GridBagConstraints.BOTH, insets, 5, 5);
        label = new JLabel(root.getValue().toString() );
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, valuec);
        childNodes = new ArrayList<>();

        Iterator<Rectangle> layoutStream;
        if(children.size() == 3)
            layoutStream = Stream.of(
                    new Rectangle(0, 2, 1, 1),
                    new Rectangle(0,childCounts[0] < childCounts[1] ? 3 : 1, 2, 1),
                    new Rectangle(1, 2, 1, 1)).iterator();

        else
            layoutStream = IntStream.iterate(0, n -> n + 1).mapToObj(n -> new Rectangle(n, 1, 1, 1)).iterator();

        for (int i = 0; i < children.size(); i++) {
            Rectangle r = layoutStream.next();
                GridBagConstraints child = new GridBagConstraints(r.x, r.y, r.width, r.height, 
                    childCounts[i]/totalChildren, 10, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    insets, 5, 5);
            TreeView comp = new TreeView(children.get(i));
            add(comp, child);
            childNodes.add(comp);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //label.setText(String.format("%d %d",getLocation().x,label.getHeight()) );
        int linePadding = 15;
        int centreX = label.getWidth()/2;
        int centreY = label.getHeight()/2+linePadding;
        for (TreeView t:childNodes) {
            g.drawLine(centreX, centreY, t.getX()+t.getWidth()/2, t.getY()+t.label.getHeight()/2-linePadding);
        }
    }
}
