import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Help extends JPanel{
    private final BufferedImage bck=Loader.scale(Objects.requireNonNull(Loader.load("Images/Background.png")), 400, 400);

    Help(JFrame frame)
    {
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        JButton back=new JButton("Back");
        this.add(Box.createRigidArea(new Dimension(0,35)));
        JLabel help=new JLabel("<html>Contrary to popular belief, it's actually quite simple." +
                " Click a square, you get a number." +
                " That number is the number of how many mines are surrounding it." +
                " If you find the mine, you can open \"unopened\" squares around it, opening more areas.<br>" +
                "Press right click to place a flag over a mine and press left click to open squares.</html>");
        help.setOpaque(true);
        this.add(help);
        this.add(Box.createRigidArea(new Dimension(0,30)));
        back.addActionListener(event -> {
            frame.getContentPane().removeAll();
            frame.add(new Menu (frame));
            frame.revalidate();
            frame.repaint();
            });
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new AudioLoader("src/Audio/menu.wav");
            }
        });
        this.add(back);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bck,0,0,null);
    }
}
