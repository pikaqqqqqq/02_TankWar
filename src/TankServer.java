import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by think on 2017/6/23.
 */
public class TankServer {
    /**
     * tcp 用来连接的
     * udp 用来传输游戏数据的
     */
    private static int ID = 100;
    public static final int TCP_PORT = 8888;
    List<Client> clients = new ArrayList<Client>();

    public void start() {
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Socket s = null;
        while (true) {
            try {
                s = ss.accept();
                String IP = s.getInetAddress().getHostAddress();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                int udpPort = dis.readInt();
                Client client = new Client(IP, udpPort);
                clients.add(client);
                DataOutputStream dos = new DataOutputStream(s.getOutputStream());
                dos.writeInt(ID++);//1.9.3_2是否需要写synchronized 给id上锁，如果像chat那样接收客户端就需要
                System.out.println("a client connect! Addr:" + s.getInetAddress().getHostAddress() + ":" + s.getPort() +
                        "-------UPD Port:" + udpPort);

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (s != null) {
                    try {
                        s.close();//1.9.3_2握手的机制
                        s = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private class Client {
        String IP;
        int udpPort;

        public Client(String IP, int udpPort) {
            this.IP = IP;
            this.udpPort = udpPort;
        }
    }

    public static void main(String[] args) {
        new TankServer().start();
    }
}
