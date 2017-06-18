import java.awt.*;

/**
 * Created by think on 2017/6/18.
 */
public class Wall {
    private int x;
    private int y;

    private int width;
    private int height;

    private TankClient tc;

    public Wall(int x, int y, int width, int height, TankClient tc){
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
        this.tc = tc;
    }

    public void draw(Graphics g){
        Color c = g.getColor();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x,y,width,height);
        g.setColor(c);
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }

}
