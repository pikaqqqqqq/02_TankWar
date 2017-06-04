import java.awt.*;

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
        setVisible(true);
    }
}
