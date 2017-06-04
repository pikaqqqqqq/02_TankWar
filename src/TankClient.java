import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by think on 2017/6/4.
 */
public class TankClient extends Frame{
    public static void  main(String[] args){
        new TankClient().launch();
    }

    public void launch(){
        setLocation(200,100);
        setSize(800,600);
        setTitle("TankWar");
        setResizable(false);//不让窗口改变大小
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }
}
