import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by think on 2017/6/4.
 */
public class TankClient extends Frame {

    int x = 50;
    int y = 50;
    public static final int GAME_HIGH = 600;
    public static final int GAME_WIDTH = 800;

    Image offScreenImage = null;

    //0.3重写paint方法,这个方法不用调用，重画的时候会自己调用。
    @Override
    public void paint(Graphics g) {
        //默认前景色为黑色
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x, y, 30, 30);//x,y,w,h
        g.setColor(c);//不要改变原来的前景色

        //y += 5;
    }

    //0.4双缓冲技术
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HIGH);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();

        //擦除原画
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HIGH);
        gOffScreen.setColor(c);

        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    //0.1创建一个方法
    public void launchFrame() {
        setLocation(200, 100);
        setSize(GAME_WIDTH, GAME_HIGH);
        //0.2设置窗口属性
        setTitle("TankWar");
        setResizable(false);//不让窗口改变大小
        this.setBackground(Color.black);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
        new Thread(new PaintTread()).start();
        this.addKeyListener(new KeyMonitor());
    }

    public static void main(String[] args) {
        new TankClient().launchFrame();
    }

    //0.4电影怎么动的，就模拟怎么动
    //0.4可能会出现闪烁问题，使用双缓冲解决
    private class PaintTread implements Runnable {

        @Override
        public void run() {
            while (true) {
                repaint();//外部包装类的repaint();方法
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //0.6写程序要循序渐进，写一点测试一点
    private class KeyMonitor extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

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

            if (key == KeyEvent.VK_RIGHT) {
                x += 5;
            } else if (key == KeyEvent.VK_LEFT) {

            }
            if (key == KeyEvent.VK_UP) {

            }

            if (key == KeyEvent.VK_DOWN) {

            }
        }
    }
}
