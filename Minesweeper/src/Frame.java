import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

public class Frame extends JFrame implements MouseListener, KeyListener
{
    private static final int width = 500;
    private static final int height = 500;
    private final Display display;
    private final Minesweeper Minesweeper=new Minesweeper();
    private int t=0;
    private final JLabel ido=new JLabel();
    private Timer time=new Timer(t,Minesweeper,ido);
    private Thread T=new Thread(time);

    private final int insetLeft;
    private final int insetRight;
    private final int insetTop;
    private final int insetBottom;

    public Frame()
    {
        super("Minesweeper-keam0278");
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        addMouseListener(this);
        addKeyListener(this);
        display = new Display();
        add(display);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = Minesweeper.getLabel();
        panel.add(label, BorderLayout.NORTH);
        T.start();
        panel.add(ido,BorderLayout.SOUTH);
        add(panel,BorderLayout.SOUTH);
        pack();
        insetLeft = getInsets().left;
        insetRight=getInsets().right;
        insetTop = getInsets().top;
        insetBottom=getInsets().bottom;
        setSize(width + insetLeft + insetRight, height + insetBottom + insetTop+30);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) { }
    @Override
    public void mouseEntered(MouseEvent e) { }
    @Override
    public void mouseExited(MouseEvent e) {   }
    @Override
    public void mousePressed(MouseEvent e)    { }

    @Override
    public void mouseReleased(MouseEvent e)
    {
        if(e.getY()<height+insetTop-insetBottom&&e.getX()<width+insetLeft-insetRight){
        if(e.getButton() == 1)
            Minesweeper.clickedLeft(e.getX() - insetLeft, e.getY() - insetTop);
        if(e.getButton() == 3)
            Minesweeper.clickedRight(e.getX() - insetLeft, e.getY() - insetTop);
        display.repaint();}
    }
    @Override
    public void keyPressed(KeyEvent e) { }

    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_R)
        {
            Minesweeper.reset();
            T.stop();
            t=0;
            time=new Timer(t,Minesweeper,ido);
            T=new Thread(time);
            T.start();
            repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e){}

    public class Display extends JPanel
    {
        @Override
        public void paintComponent(Graphics g)
        {
            Minesweeper.draw(g);
        }
    }

    public static int getDisplayWidth()
    {
        return width;
    }

    public static int getDisplayHeight() { return height;
    }

}