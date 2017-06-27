import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
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
    private int udpPort;

    private TankClient tc;
    private Socket s = null;

    DatagramSocket ds = null;

    public NetClient(TankClient tc) {
        this.tc = tc;

    }

    public void connect(String IP, int port) {
        try {
            ds = new DatagramSocket(udpPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
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

        new Thread(new UDPRecvThread()).start();
    }


    /**
     * @param msg 多态：继承，重写，父类引用指向子类对象
     *            多态的使用
     */
    public void send(Msg msg) {
        //public void send(TankNewMsg msg) {
        msg.send(ds, "127.0.0.1", TankServer.UDP_PORT);
    }

    public int getUdpPort() {
        return udpPort;
    }

    public void setUdpPort(int udpPort) {
        this.udpPort = udpPort;
    }

    private class UDPRecvThread implements Runnable {

        byte[] buf = new byte[1024];

        @Override
        public void run() {
            while (ds != null) {

                DatagramPacket dp = new DatagramPacket(buf, buf.length);
                try {
                    ds.receive(dp);

                    parse(dp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        private void parse(DatagramPacket dp) {
            ByteArrayInputStream bais = new ByteArrayInputStream(buf, 0, dp.getLength());
            DataInputStream dis = new DataInputStream(bais);
            int msgType = 0;
            try {
                msgType = dis.readInt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            /**
             * 1.9.5父类引用指向子类对象
             * 对多态的使用！！！
             */
            Msg msg = null;
            switch (msgType) {
                case Msg.TANK_NEW_MSG:
                    System.out.println("a TANK_NEW_MSG packet receive from server!");

                    msg = new TankNewMsg(NetClient.this.tc);//1.9.4_3在一个内部类里访问封装类的对象，直接写tc也可以
                    msg.parse(dis);
                    break;
                case Msg.TANK_MOVE_MSG:
                    System.out.println("a TANK_MOVE_MSG packet receive from server!");
                    msg = new TankMoveMsg(NetClient.this.tc);
                    msg.parse(dis);
                    break;
            }
        }
    }
}
