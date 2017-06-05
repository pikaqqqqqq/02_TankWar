import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by think on 2017/6/5.
 */
//写一个项目前要考虑这个项目大概有哪些类哪些属性（真正面向对象的思维）
public class Tank {
    public static final int XSPEED = 5;
    public static final int YSPEED = 5;

    private int x;
    private int y;

    enum Direction {L, LU, U, RU, R, RD, D, LD, STOP}//枚举类型

    private Direction dir = Direction.STOP;

    private boolean bL = false;
    private boolean bU = false;
    private boolean bR = false;
    private boolean bD = false;


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
        move();
    }

    public void move() {
        switch (dir) {
            case L:
                x -= XSPEED;
                break;
            case LU:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case U:
                y -= YSPEED;
                break;
            case RU:
                x += XSPEED;
                y -= YSPEED;
                break;
            case R:
                x += XSPEED;
                break;
            case RD:
                x += XSPEED;
                y += YSPEED;
                break;
            case D:
                y += YSPEED;
                break;
            case LD:
                x -= XSPEED;
                y += YSPEED;
                break;
            case STOP:
                break;
        }
    }

    //0.8实现八方向操作
    public void KeyPressed(KeyEvent e) {
        //如果是八方向应该怎么写
//        switch (key) {
//            case KeyEvent.VK_LEFT:
//                x -= 5;
//                break;
//            case KeyEvent.VK_UP:
//                y -= 5;
//                break;
//            case KeyEvent.VK_RIGHT:
//                x += 5;
//                break;
//            case KeyEvent.VK_DOWN:
//                y += 5;
//                break;
//        }

        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = true;
                break;
            case KeyEvent.VK_UP:
                bU = true;
                break;
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;
            case KeyEvent.VK_DOWN:
                bD = true;
                break;
        }
        locateDirection();
    }

    //0.8处理八方向行走bug
    public void KeyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_LEFT:
                bL = false;
                break;
            case KeyEvent.VK_UP:
                bU = false;
                break;
            case KeyEvent.VK_RIGHT:
                bR = false;
                break;
            case KeyEvent.VK_DOWN:
                bD = false;
                break;
        }
        locateDirection();
    }

    public void locateDirection() {
        if (bL && !bU && !bR && !bD) {
            dir = Direction.L;
        } else if (bL && bU && !bR && !bD) {
            dir = Direction.LU;
        } else if (!bL && bU && !bR && !bD) {
            dir = Direction.U;
        } else if (!bL && bU && bR && !bD) {
            dir = Direction.RU;
        } else if (!bL && !bU && bR && !bD) {
            dir = Direction.R;
        } else if (!bL && !bU && bR && bD) {
            dir = Direction.RD;
        } else if (!bL && !bU && !bR && bD) {
            dir = Direction.D;
        } else if (bL && !bU && !bR && bD) {
            dir = Direction.LD;
        } else if (!bL && !bU && !bR && !bD) {
            dir = Direction.STOP;
        }

    }

}
