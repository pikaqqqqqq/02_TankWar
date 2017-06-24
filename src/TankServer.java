import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;
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
    public static final int UDP_PORT = 6666;
    List<Client> clients = new ArrayList<Client>();

    public void start() {

        new Thread(new UDPThread()).start();

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

    private class UDPThread implements Runnable {

        byte[] buf = new byte[1024];

        @Override
        public void run() {
            DatagramSocket ds = null;
            try {
                ds = new DatagramSocket(UDP_PORT);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            System.out.println("UDP thread start at:" + UDP_PORT);
            //接收udp数据
            while (ds != null) {
                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);
                    System.out.println("a packet recevive!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new TankServer().start();
    }
}
