import javax.swing.*;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    public static void main(String[] args)
    {
        JFrame Game=new JFrame("Minesweeper");
        Game.setResizable(false);
        Game.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Game.setSize(420,420);
        Game.setLocationRelativeTo(null);
        Game.add(new Menu(Game));
        Game.setVisible(true);
    }
}
