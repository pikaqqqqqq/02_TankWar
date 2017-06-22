import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by think on 2017/6/5.
 */
//写一个项目前要考虑这个项目大概有哪些类哪些属性（真正面向对象的思维）
public class Tank {
    public static final int XSPEED = 5;
    public static final int YSPEED = 5;

    public static final int WIDTH = 60;
    public static final int HEIGHT = 60;

    TankClient tc;

    //1.5添加敌方坦克,因为都是坦克所以不需要新建一个敌人类
    private boolean good;

    private boolean live = true;
    private int life = 100;

    private int x;
    private int y;
    private int oldX;
    private int oldY;


    private static Random r = new Random();//1.9随机数产生器

    //enum Direction {L, LU, U, RU, R, RD, D, LD, STOP}//枚举类型

    private Direction dir = Direction.STOP;
    private Direction ptDir = Direction.D;

    private boolean bL = false;
    private boolean bU = false;
    private boolean bR = false;
    private boolean bD = false;

    private int step = r.nextInt(12) + 3;

    private BloodBar bb = new BloodBar();

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] tankImages = null;
    private static Map<String,Image> imgs = new HashMap<String,Image>();

    static {
        /**静态执行一段代码，当这个class第一次被loader到内存里，首先执行的一段代码，这种用法更有优势，可以直接执行一句语句
         * 静态代码区，最适合用于一些变量的初始化
         */
        tankImages = new Image[]{
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankL.jpg")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankLU.jpg")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankU.jpg")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankRU.png")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankR.png")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankRD.png")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankD.jpg")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/tankLD.jpg")),
        };
        imgs.put("L",tankImages[0]);
        imgs.put("LU",tankImages[1]);
        imgs.put("U",tankImages[2]);
        imgs.put("RU",tankImages[3]);
        imgs.put("R",tankImages[4]);
        imgs.put("RD",tankImages[5]);
        imgs.put("D",tankImages[6]);
        imgs.put("LD",tankImages[7]);
    }

    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.good = good;
    }

    //1.1持有对方引用
    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);//调用上面的构造方法
        this.tc = tc;
        this.dir = dir;
    }

    public void draw(Graphics g) {
        if (!live) {
            if (!good) {
                tc.tanks.remove(this);
            }
            return;
        }

        if(good){
            bb.draw(g);
        }

        switch (ptDir) {
            case L:
                g.drawImage(imgs.get("L"),x,y,null);
                break;
            case LU:
                g.drawImage(imgs.get("LU"),x,y,null);
                break;
            case U:
                g.drawImage(imgs.get("U"),x,y,null);
                break;
            case RU:
                g.drawImage(imgs.get("RU"),x,y,null);
                break;
            case R:
                g.drawImage(imgs.get("R"),x,y,null);
                break;
            case RD:
                g.drawImage(imgs.get("RD"),x,y,null);
                break;
            case D:
                g.drawImage(imgs.get("D"),x,y,null);
                break;
            case LD:
                g.drawImage(imgs.get("LD"),x,y,null);
                break;
        }
        move();

        if (!good) {
            Direction[] dirs = Direction.values();
            if (step == 0) {
                //1.9.2让坦克移动随机的距离
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(dirs.length);
                this.dir = dirs[rn];
            }
            step--;
            if (r.nextInt(40) > 37) {
                this.fire();
            }
        }
    }

    public void move() {

        this.oldX = x;
        this.oldY = y;

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
        if (this.dir != Direction.STOP) {
            this.ptDir = this.dir;
        }

        //1.4.1坦克不能出界
        if (x < 0) x = 0;
        if (y < 30) y = 30;
        if (x > TankClient.GAME_WIDTH - Tank.WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
        if (y > TankClient.GAME_HEIGHT - Tank.HEIGHT) y = TankClient.GAME_HEIGHT - Tank.HEIGHT;

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
            case KeyEvent.VK_F2:
                if(!live){
                    life = 100;
                    live = true;
                }
                break;
            case KeyEvent.VK_CONTROL:
                fire();
                break;
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
            case KeyEvent.VK_A:
                superFire();
                break;
        }
        locateDirection();
    }

    public Missile fire() {
        if(!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.WIDTH / 2;
        Missile m = new Missile(x, y, good, ptDir, tc);
        tc.missiles.add(m);
        return m;
    }

    public Missile fire(Direction dir){
        if(!live) return null;
        int x = this.x + Tank.WIDTH / 2 - Missile.WIDTH / 2;
        int y = this.y + Tank.HEIGHT / 2 - Missile.WIDTH / 2;
        Missile m = new Missile(x, y, good, dir, tc);
        tc.missiles.add(m);
        return m;
    }

    public void superFire(){
        Direction[] dirs = Direction.values();
        for (int i = 0; i < 8; i++){
            fire(dirs[i]);
        }
    }

    public boolean eatBlood(Blood b){
        if(this.live && b.isLive() && this.getRect().intersects(b.getRect())){
            this.life = 100;
            b.setLive(false);
            return true;
        }
        return false;
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

    public Rectangle getRect() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    private void stay(){
        x = oldX;
        y = oldY;
    }

    public  boolean collidesWithWall(Wall w){//第三人称问题
        if(this.live && this.getRect().intersects(w.getRect())){
            stay();
            return true;
        }
        return false;
    }

    public boolean collidesWithTanks(List<Tank> tanks){
        for (int i = 0; i < tanks.size(); i++){
            Tank t = tanks.get(i);
            if(this != t){
                if(this.live && t.live && this.getRect().intersects(t.getRect())){
                    this.stay();
                    t.stay();
                    return false;
                }
            }
        }
        return false;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isGood() {
        return good;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    private class BloodBar{
        public  void draw(Graphics g){
            Color c = g.getColor();
            g.setColor(Color.red);
            g.drawRect(x, y - 10, WIDTH, 5);
            int w = WIDTH * life/100;
            g.fillRect(x, y - 10, w, 5);
            g.setColor(c);
        }
    }
}
