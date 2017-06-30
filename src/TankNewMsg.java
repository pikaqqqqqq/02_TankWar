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
 * 1.9.4_1 发送和解析udp数据
 * 1.9.4 怎么用多态来封装这些个msg
 * 1.9.4_2 udp环路（数据发送与解析）
 */


public class TankNewMsg implements Msg {

    int msgType = Msg.TANK_NEW_MSG;

    Tank tank = null;
    TankClient tc; //学了设计模式还会有比持有对方引用更好的办法

    public TankNewMsg(Tank tank, TankClient tc) {
        this.tank = tank;
        this.tc = tc;
    }

    public TankNewMsg(TankClient tc) {
        this.tc = tc;
    }

    public void send(DatagramSocket ds, String IP, int udpPort) {
        //ByteArrayOutputStream new出来以后内存中就默认出现了一个32位的字节数组，还可以自动增长
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
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

    public void parse(DataInputStream dis) {

        try {
            int id = dis.readInt();
            if (tc.myTank.getID() == id) return;
            int x = dis.readInt();
            int y = dis.readInt();
            Direction dir = Direction.values()[dis.readInt()];
            boolean good = dis.readBoolean();
            //System.out.println(id + " " + x + " " + y + " " + dir + " " + good);
            boolean exit = false;
            for (int i = 0; i < tc.tanks.size(); i++) {
                if (id == tc.tanks.get(i).getID()) {
                    exit = true;
                    break;
                }
            }
            if (!exit) {
                TankNewMsg tnMsg = new TankNewMsg(tc.myTank, tc);
                tc.nc.send(tnMsg);
                Tank t = new Tank(x, y, good, dir, tc);
                t.setID(id);
                tc.tanks.add(t);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
