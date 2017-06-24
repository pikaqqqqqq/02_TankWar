import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by think on 2017/6/23.
 */
public class NetClient {

    /**
     * 1.9.2 UDP端口如何定义（冲突问题）
     * 1.9.3_2 怎样产生一个独一无二的ID
     * 使用java.util.UUID
     * 自己生成ID
     * 是否需要写synchronized 给id上锁，如果像chat那样接收客户端就需要
     */
    private static int UDP_PORT_START = 2223;
    private int udpPort;

    private TankClient tc;
    private Socket s = null;

    DatagramSocket ds = null;

    public NetClient(TankClient tc) {
        this.udpPort = UDP_PORT_START++;
        this.tc = tc;
        try {
            ds = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void connect(String IP, int port) {
        try {
            s = new Socket(IP, port);
            DataOutputStream dos = new DataOutputStream(s.getOutputStream());
            dos.writeInt(udpPort);
            DataInputStream dis = new DataInputStream(s.getInputStream());
            int ID = dis.readInt();
            System.out.println("connect to server!and get an ID:" + ID);
            tc.myTank.setID(ID);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (s != null) {
                    s.close();
                    s = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        TankNewMsg msg = new TankNewMsg(tc.myTank, tc);
        send(msg);
    }

    public void send(TankNewMsg msg) {
        msg.send(ds, "127.0.0.1", TankServer.UDP_PORT);
    }

}
