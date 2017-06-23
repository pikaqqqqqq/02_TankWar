import java.io.IOException;
import java.net.Socket;

/**
 * Created by think on 2017/6/23.
 */
public class NetClient {

    public static final int UDP_PORT = 5566;

    private TankClient tc;
    private Socket s = null;

    public void connect(String IP, int port){
        try {
            Socket s = new Socket(IP, port);
System.out.println("connect to server!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
