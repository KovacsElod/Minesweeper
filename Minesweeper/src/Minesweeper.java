import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.Random;

public class Minesweeper
{
    private final static int cols = 16;
    private final static int rows = 16;
    private final int bombs = 16;
    private boolean finish;
    private boolean dead;
    private final Random random;
    private final Cell[] [] cells;
    private int R_bombs=bombs;
    private final JLabel label;
    private boolean fclick=false;
    private AudioLoader a1;
    private AudioLoader a2;
    private AudioLoader a3;

    public Minesweeper()
    {
        random = new Random();
        label=new JLabel();
        cells = new Cell[cols] [rows];

        for(int x = 0;x < cols;x++)
        {
            for(int y = 0;y < rows;y++)
            {
                BufferedImage bomb = Loader.scale(Objects.requireNonNull(Loader.load("Images/bomb.png")), Cell.getWidth(), Cell.getHeight());
                BufferedImage flag = Loader.scale(Objects.requireNonNull(Loader.load("Images/flag.png")), Cell.getWidth(), Cell.getHeight());
                BufferedImage pressed = Loader.scale(Objects.requireNonNull(Loader.load("Images/pressed.png")), Cell.getWidth(), Cell.getHeight());
                BufferedImage normal = Loader.scale(Objects.requireNonNull(Loader.load("Images/normal.png")), Cell.getWidth(), Cell.getHeight());
                cells[x] [y] = new Cell(x, y, normal, bomb, pressed, flag);
            }
        }

        reset();
    }

    private void placeBombs()
    {
        for(int i = 0;i < bombs;i++)
        {
            placeBomb();
        }
    }

    private void placeBomb()
    {
        int x = random.nextInt(cols);
        int y = random.nextInt(rows);

        if(!cells[x][y].isBomb())
            cells[x] [y].setBomb(true);
        else placeBomb();
    }


    private void setN_NearBomb()
    {
        for (int i=0;i<cols;i++){
            for(int j=0;j<rows;j++){
                int N_NearBombs=0;
                if(i-1>=0&&j-1>=0&&cells[i-1][j-1].isBomb())
                    N_NearBombs++;
                if(i-1>=0&&cells[i-1][j].isBomb())
                    N_NearBombs++;
                if(i-1>=0&&j+1<rows&&cells[i-1][j+1].isBomb())
                    N_NearBombs++;
                if(i+1<cols&&j-1>=0&&cells[i+1][j-1].isBomb())
                    N_NearBombs++;
                if(j-1>=0&&cells[i][j-1].isBomb())
                    N_NearBombs++;
                if(j+1<rows&&cells[i][j+1].isBomb())
                    N_NearBombs++;
                if(i+1<cols&&cells[i+1][j].isBomb())
                    N_NearBombs++;
                if(i+1<cols&&j+1<rows&&cells[i+1][j+1].isBomb())
                    N_NearBombs++;
                cells[i][j].setN_NearBombs(N_NearBombs);
            }
        }
    }

    public void clickedLeft(int x, int y)
    {
        if(!dead&&!finish)
        {
            int X = x/ Cell.getWidth();
            int Y = y/ Cell.getHeight();

            if(!cells[X] [Y].isFlag())
            {
                cells[X] [Y].setUncovered(true);
                if(cells[X] [Y].isBomb()) {
                    dead = true;
                    revealAllBombs();
                }
                else
                {
                    if(cells[X] [Y].getN_NearBombs() == 0)
                    {
                        open(X, Y);
                    }
                }

                checkFinish();
            }
        }
    }

    public void clickedRight(int x, int y)
    {
        if(!dead&&!finish)
        {
            int X = x/ Cell.getWidth();
            int Y = y/ Cell.getHeight();
            cells[X] [Y].placeFlag();
            if(cells[X][Y].isFlag())
                R_bombs--;
            else if(!cells[X][Y].isUncovered())
                R_bombs++;
            label.setText(Integer.toString(R_bombs));
            checkFinish();
        }
    }

    private void open(int i,int j)
    {
        cells[i][j].setUncovered(true);
        if(cells[i][j].getN_NearBombs()==0){
            if(i-1>=0&&j-1>=0&&cells[i-1][j-1].canUncover())
                open(i-1,j-1);
            if(i-1>=0&&cells[i-1][j].canUncover())
                open(i-1,j);
            if(i-1>=0&&j+1<rows&&cells[i-1][j+1].canUncover())
                open(i-1,j+1);
            if(i+1<cols&&j-1>=0&&cells[i+1][j-1].canUncover())
                open(i+1,j-1);
            if(j-1>=0&&cells[i][j-1].canUncover())
                open(i,j-1);
            if(j+1<rows&&cells[i][j+1].canUncover())
                open(i,j+1);
            if(i+1<cols&&cells[i+1][j].canUncover())
                open(i+1,j);
            if(i+1<cols&&j+1<rows&&cells[i+1][j+1].canUncover())
                open(i+1,j+1);
        }
    }

    private void checkFinish()
    {
        finish = true;
        ell : for(int x = 0;x < cols;x++)
        {
            for(int y = 0;y < rows;y++)
            {
                if(!(cells[x] [y].isUncovered()||(cells[x] [y].isBomb()&& cells[x] [y].isFlag())))
                {
                    finish = false;
                    break ell;
                }
            }
        }
    }

    public void reset()
    {
        for(int x = 0;x < cols;x++)
        {
            for(int y = 0;y < rows;y++)
            {
                cells[x][y].reset();
            }
        }
        dead = false;
        finish = false;
        if(a1!=null){
        a1.stopAudio();}
        if(a2!=null){
        a2.stopAudio();}
        if(a3!=null){
        a3.stopAudio();}
        fclick=false;
        R_bombs=bombs;
        label.setText(Integer.toString(R_bombs));
        placeBombs();
        setN_NearBomb();
    }

    public void draw(Graphics g)
    {
        for(int x = 0;x < cols;x++)
        {
            for(int y = 0;y < rows;y++)
            {
                cells[x][y].draw(g);
            }
        }
        if(dead&&!fclick)
        {
            label.setText("GAME OVER! PRESS R TO RESTART");
            fclick=true;
            int x=random.nextInt(6)+1;
            a1=new AudioLoader("src/Audio/"+x+".wav");

        }
        else if(finish&&!fclick)
        {
            label.setText("YOU WON! PRESS R TO RESTART");
            a2=new AudioLoader("src/Audio/w1.wav");
            a3=new AudioLoader("src/Audio/w2.wav");
            fclick=true;
        }
    }

    public void revealAllBombs(){
        for(int i=0;i<cols;i++){
            for(int j=0;j<rows;j++){
                if(cells[i][j].isBomb())
                    cells[i][j].setUncovered(true);
            }
        }
    }

    public static int getcols()
    {
        return cols;
    }

    public static int getrows()
    {
        return rows;
    }

    public JLabel getLabel() {
        return label;
    }

    public boolean isFinish() {
        return finish;
    }

    public boolean isDead() {
        return dead;
    }
}