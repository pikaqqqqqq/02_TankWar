import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by think on 2017/6/24.
 * 1.9.4_1 发送和解析udp数据
 */


public class TankNewMsg {
    Tank tank = null;
    TankClient tc;

    public TankNewMsg(Tank tank, TankClient tc) {
        this.tank = tank;
        this.tc = tc;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        //ByteArrayOutputStream new出来以后内存中就默认出现了一个32位的字节数组，还可以自动增长
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(tank.getID());
            dos.writeInt(tank.x);
            dos.writeInt(tank.y);
            dos.writeInt(tank.dir.ordinal());//ordinal()获取数组的下标值
            dos.writeBoolean(tank.isGood());
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = baos.toByteArray();
        DatagramPacket dp;
        try {
            dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
            ds.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
