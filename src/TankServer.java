import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by think on 2017/6/23.
 */
public class TankServer {
    /**
     * tcp 用来连接的
     * udp 用来传输游戏数据的
     */

    public static final int TCP_PORT = 8888;

    public void launch(){
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(TCP_PORT);
            while (true){
                Socket s = ss.accept();
System.out.println("a client connect! Addr:" + s.getInetAddress() + ":" + s.getPort() );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args){
        new TankServer().launch();
    }
}
