import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by think on 2017/6/24.
 * 1.9.5 换方向的时候 发送这个信息
 */

public class TankMoveMsg implements Msg {
    int msgType = Msg.TANK_MOVE_MSG;
    Direction dir;
    TankClient tc;
    int id;

    public TankMoveMsg(int id, Direction dir, TankClient tc) {
        this.dir = dir;
        this.tc = tc;
        this.id = id;
    }

    public TankMoveMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(id);
            dos.writeInt(dir.ordinal());//ordinal()获取数组的下标值
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] buf = baos.toByteArray();
        try {
            DatagramPacket dp = new DatagramPacket(buf, buf.length, new InetSocketAddress(IP, udpPort));
            ds.send(dp);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void parse(DataInputStream dis) {
        try {
            int id = dis.readInt();
            if (tc.myTank.getID() == id) return;
            Direction dir = Direction.values()[dis.readInt()];
            System.out.println("id + dir:" + id + " " + dir);
            boolean exist = false;
            for (int i = 0; i < tc.tanks.size(); i++) {
                Tank t = tc.tanks.get(i);
                if (t.getID() == id) {
                    t.dir = dir;
                    exist = true;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
