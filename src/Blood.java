import java.awt.*;

/**
 * Created by think on 2017/6/21.
 */
public class Blood {
    int x;
    int y;
    int w = 10;
    int h = 10;

    TankClient tc;

    int step = 0;

    private boolean live = true;
    private int[][] pos = {{350, 300},{360, 300},{375, 275},{400, 200},{360, 270},{365, 290},{340, 280}};

    public Blood(TankClient tc){
        this.tc = tc;
        x = pos[0][0];
        y = pos[0][1];
    }

    public void draw(Graphics g){
        if(!live) return;
        Color c = g.getColor();
        g.setColor(Color.pink);
        g.fillRect(x, y, w, h);
        g.setColor(c);
        
        move();
    }

    private void move() {
        step++;
        if (step == pos.length){
            step = 0;
        }
        x = pos[step][0];
        y = pos[step][1];
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, w, h);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
