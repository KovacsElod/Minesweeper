import javax.swing.*;

public class Timer implements Runnable{
    private int time;
    private final Minesweeper game;
    private final JLabel label;

    public Timer(int time, Minesweeper game, JLabel label)
    {
        this.time=time;
        this.game=game;
        this.label=label;
    }

    @Override
    public void run() {
        while(!game.isFinish()&&!game.isDead())
        {
            label.setText("Time: "+time);
            time++;
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();}
        }
    }
}
