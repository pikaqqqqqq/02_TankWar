import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by think on 2017/6/5.
 */
//写一个项目前要考虑这个项目大概有哪些类哪些属性（真正面向对象的思维）
public class Tank {
    private int x;
    private int y;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        //默认前景色为黑色
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);//x,y,w,h
        g.setColor(c);//不要改变原来的前景色
    }

    public void KeyPressed(KeyEvent e){
        int key = e.getKeyCode();
        //如果是八方向应该怎么写
        switch (key) {
            case KeyEvent.VK_LEFT:
                x -= 5;
                break;
            case KeyEvent.VK_UP:
                y -= 5;
                break;
            case KeyEvent.VK_RIGHT:
                x += 5;
                break;
            case KeyEvent.VK_DOWN:
                y += 5;
                break;
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}
