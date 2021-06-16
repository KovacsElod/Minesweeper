import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Cell
{
    private final BufferedImage norm_img;
    private final BufferedImage uncovered_img;
    private final BufferedImage flag_img;
    private final BufferedImage bomb_img;


    private final int x;
    private final int y;
    private boolean bomb;
    private boolean uncovered;
    private boolean flag;
    private int N_NearBombs;

    private static final int width = Frame.getDisplayWidth()/Minesweeper.getcols();
    private static final int height = Frame.getDisplayHeight()/Minesweeper.getrows();

    public Cell(int x, int y, BufferedImage norm_img, BufferedImage bomb, BufferedImage uncovered_img, BufferedImage flag)
    {
        this.x = x;
        this.y = y;
        this.norm_img = norm_img;
        this.bomb_img = bomb;
        this.uncovered_img = uncovered_img;
        this.flag_img = flag;
    }


    public void setUncovered(boolean uncovered)
    {
        this.uncovered = uncovered;
    }

    public boolean isUncovered()
    {
        return uncovered;
    }

    public boolean canUncover()
    {
        return !uncovered&&!bomb;
    }

    public void setBomb(boolean bomb)
    {
        this.bomb = bomb;
    }

    public boolean isBomb()
    {
        return bomb;
    }

    public void setN_NearBombs(int amountOfNearBombs)
    {
        this.N_NearBombs = amountOfNearBombs;
    }

    public int getN_NearBombs()
    {
        return N_NearBombs;
    }

    public void placeFlag()
    {
        if(flag)
            flag = false;
        else
        {
            if(!uncovered) flag = true;
        }
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void reset()
    {
        flag = false;
        bomb = false;
        uncovered = false;
    }

    public void draw(Graphics g)
    {
        if(!uncovered)
        {
            if(!flag) g.drawImage(norm_img, x * width, y * height, null);
            else g.drawImage(flag_img, x * width, y * height, null);
        }
        else
        {
            if(bomb) g.drawImage(bomb_img, x * width, y * height, null);
            else
            {
                g.drawImage(uncovered_img, x * width, y * height, null);
                if(N_NearBombs > 0)
                {
                    g.setColor(Color.BLACK);
                    g.drawString("" + N_NearBombs, x * width+10, y * height + height-10);
                }
            }
        }
    }

    public static int getWidth()
    {
        return width;
    }

    public static int getHeight()
    {
        return height;
    }
}