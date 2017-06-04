import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by think on 2017/6/4.
 */
public class TankClient extends Frame{

    int x = 50;
    int y = 50;

    Image offScreenImage = null;

    //0.3重写paint方法,这个方法不用调用，重画的时候会自己调用。
    @Override
    public void paint(Graphics g) {
        //默认前景色为黑色
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillOval(x,y,30,30);//x,y,w,h
        g.setColor(c);//不要改变原来的前景色

        y += 5;
    }

    //0.4双缓冲技术
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(800,600);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();

        //擦除原画
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0,0,800,600);
        gOffScreen.setColor(c);

        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }

    //0.1创建一个方法
    public void launchFrame(){
        setLocation(200,100);
        setSize(800,600);
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
    }

    public static void  main(String[] args){
        new TankClient().launchFrame();
    }

    //0.4电影怎么动的，就模拟怎么动
    //0.4可能会出现闪烁问题，使用双缓冲解决
    private class PaintTread implements Runnable{

        @Override
        public void run() {
            while (true){
                repaint();//外部包装类的repaint();方法
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
