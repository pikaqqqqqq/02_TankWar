# 02_TankWar
```
通过做游戏这种喜闻乐见的形式，去复习J2SE
```
* 0.0 设计模式指的是，接固定问题的固定模式
* 0.4 使用线程重画界面，使得重画的更加均匀，相对于按键重画方式，解决了子弹不能自己飞行的问题。
* 0.4.1使用双缓冲技术消除闪烁现象
```java
产生闪烁的原因：
    刷新频率太快
解决方法：
    将所有的东西画在虚拟的图片上，一次性显示出来
repaint()：
    先调用update()方法，update()在调用pain()，所以在update方法中做些操作就可以了    
关键代码：

    public void update(Graphics g) {
        if(offScreenImage == null){
            offScreenImage = this.createImage(800,600);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();

        //擦除原画
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.black);
        gOffScreen.fillRect(0,0,800,600);
        gOffScreen.setColor(c);

        paint(gOffScreen);
        g.drawImage(offScreenImage,0,0,null);
    }
```
