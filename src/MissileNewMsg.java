import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by think on 2017/6/29.
 */
public class MissileNewMsg implements Msg {
    int msgType = MISSILE_NEW_MSG;
    TankClient tc;
    Missile m;

    public MissileNewMsg(Missile m, TankClient tc) {
        this.m = m;
        this.tc = tc;
    }

    public MissileNewMsg(TankClient tc) {
        this.tc = tc;
    }

    @Override
    public void send(DatagramSocket ds, String IP, int udpPort) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeInt(msgType);
            dos.writeInt(m.getTankID());
            dos.writeInt(m.getX());
            dos.writeInt(m.getY());
            dos.writeBoolean(m.isGood());
            dos.writeInt(m.getDir().ordinal());

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

    @Override
    public void parse(DataInputStream dis) {
        try {
            int tankId = dis.readInt();
            if(tankId == tc.myTank.getID()) return;
            int x = dis.readInt();
            int y = dis.readInt();
            boolean good = dis.readBoolean();
            Direction dir = Direction.values()[dis.readInt()];

            Missile m = new Missile(tankId, x, y, good, dir, tc);
            tc.missiles.add(m);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
