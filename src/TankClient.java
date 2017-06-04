import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by think on 2017/6/4.
 */
public class TankClient extends Frame{

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
    }

    //0.3重写paint方法,这个方法不用调用，重画的时候会自己调用。
    @Override
    public void paint(Graphics g) {
        //默认前景色为黑色
        Color c = g.getColor();
        g.setColor(Color.RED);
        //x,y,w,h
        g.fillOval(50,50,30,30);
        g.setColor(c);//不要改变原来的前景色
    }

    public static void  main(String[] args){
        new TankClient().launchFrame();
    }


}
