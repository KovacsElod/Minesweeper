import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Menu extends JPanel{
    private final BufferedImage bck=Loader.scale(Objects.requireNonNull(Loader.load("Images/Background.png")), 400, 400);

    protected Menu(JFrame frame){
        this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
        this.add(Box.createRigidArea(new Dimension(125,40)));
        JButton StartGame=new JButton("Start Game");
        StartGame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new AudioLoader("src/Audio/menu.wav");
            }
        });
        StartGame.addActionListener(e -> new Frame());
        this.add(StartGame);
        this.add(Box.createRigidArea(new Dimension(0,40)));
        JButton HTP=new JButton("How To Play");
        HTP.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                new AudioLoader("src/Audio/menu.wav");
            }
        });
        HTP.addActionListener(e -> {
        frame.getContentPane().removeAll();
        frame.add(new Help (frame));
        frame.revalidate();
        frame.repaint();
        });
        this.add(HTP);
        this.add(Box.createRigidArea(new Dimension(0,40)));
        JButton Quit=new JButton("Quit Game");
        Quit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
               new AudioLoader("src/Audio/menu.wav");
            }
        });
        Quit.addActionListener(e -> System.exit(1));
        this.add(Quit);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bck,0,0,null);
    }
}
