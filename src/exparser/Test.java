package exparser;

import javax.swing.*;
import java.util.Arrays;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Lexer l = new Lexer(Arrays.asList(LexemeType.values()));
        JFrame frame = new JFrame();
        Scanner sc = new Scanner(System.in);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(100,100,500,500);
        String input = "(5+24)*-4";

        frame.setVisible(true);
        
        while (true) {
            frame.setContentPane(new TreeView(new Parser(l).parse(input)));
            frame.pack();
            input=sc.nextLine();
        }
    }
}
