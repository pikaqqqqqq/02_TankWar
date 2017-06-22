import java.awt.*;

/**
 * Created by think on 2017/6/18.
 */
/**Explode.class——java反射机制
 * class运行：放到内存里，然后找到main方法
 * 类装载器classLoader，把class文件咣当一下放到内存里，对于类装载器来说.class就是一个Class对象，然后开始运行
 * class类描述的是我们编译好的class文件的信息,是类的metainfo/medadata元信息，用来描述数据/对象的数据
 * getClassLoader()通过我们这个对象拿到我们类装载器的那个对象
 * 类装载器再getResource
 * 反射特别重要，可以为程序增加很大的灵活性，以后的各种框架都涉及到反射
 *src目录会被默认的被编译到bin目录下，所以images这个目录就在classPath下，这样我们就不依赖于绝对路径或者相对路径
 * 将来我们放到哪里都可以用
 * 这是非常非常常用的一种方式，所以一定得记住
 */

/**为什么第一个爆炸显示不出来？
 * 设计模式：虚代理模式
 * 采用了异步的io所以在图片还没有放到内存中的时候，程序就开始往下执行了
 */

//1.7.1根据面向对象的思维，爆炸也应该是一个类，同时也会持有TankClient的引用
public class Explode {
    private int x;
    private int y;
    private boolean live = true;

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private boolean init = false;

    private static Image[] imgs = {
            tk.getImage(Explode.class.getClassLoader().getResource("images/1.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/2.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/3.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/4.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/5.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/6.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/7.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/8.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/9.jpg")),
            tk.getImage(Explode.class.getClassLoader().getResource("images/10.jpg"))
    };
    //private int[] diameter = {4, 7, 12, 18, 26, 32, 49, 30, 14, 6};
    private int step = 0;

    public TankClient tc;

    public Explode(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    public void draw(Graphics g){
        if(!init){
            for (int i = 0;i < imgs.length;i++){
                g.drawImage(imgs[i],-100,-100,null);
            }
            init = true;
        }

        if(!live) {
            tc.explodes.remove(this);
            return;
        }

        if(step == imgs.length){
            live = false;
            step = 0;
            return;
        }
//        Color c = g.getColor();
//        g.setColor(Color.ORANGE);
//        g.fillOval(x,y,diameter[step],diameter[step]);
//        g.setColor(c);
        g.drawImage(imgs[step],x,y,null);//null观察者模式
        step++;
    }

}
