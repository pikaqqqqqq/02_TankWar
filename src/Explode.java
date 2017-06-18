import java.awt.*;

/**
 * Created by think on 2017/6/18.
 */

//1.7.1根据面向对象的思维，爆炸也应该是一个类，同时也会持有TankClient的引用
public class Explode {
    private int x;
    private int y;
    private boolean live = true;

    private int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
    private int step = 0;

    public TankClient tc;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){
        if(!live) {
            tc.explodes.remove(this);
            return;
        }

        if(step == diameter.length){
            live = false;
            step = 0;
            return;
        }
        Color c = g.getColor();
        g.setColor(Color.ORANGE);
        g.fillOval(x,y,diameter[step],diameter[step]);
        g.setColor(c);

        step++;
    }

}
