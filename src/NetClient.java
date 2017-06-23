import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by think on 2017/6/23.
 */
public class NetClient {

    /**UDP端口如何定义（冲突问题）
     *
     */
    private static int UDP_PORT_START = 2223;
    private int udpPort;

    private TankClient tc;
    private Socket s = null;

    public NetClient() {
        this.udpPort = UDP_PORT_START++;
    }

    public void connect(String IP, int port){
        try {
           s = new Socket(IP, port);
System.out.println("connect to server!");
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (s != null) s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
