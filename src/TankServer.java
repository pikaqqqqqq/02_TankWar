import java.io.DataInputStream;
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

    public static final int TCP_PORT = 8888;
    List<Client> clients = new ArrayList<Client>();

    public void start(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);
            while (true){
                Socket s = ss.accept();
System.out.println("a client connect! Addr:" + s.getInetAddress().getHostAddress() + ":" + s.getPort() );
                String IP = s.getInetAddress().getHostAddress();
                DataInputStream dis = new DataInputStream(s.getInputStream());
                int udpPort = dis.readInt();
                Client client = new Client(IP, udpPort);
                clients.add(client);
                s.close();//握手的机制
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private class Client{
        String IP;
        int udpPort;

        public Client(String IP, int udpPort) {
            this.IP = IP;
            this.udpPort = udpPort;
        }
    }

    public static void main(String[] args){
        new TankServer().start();
    }
}
