package com.tedu.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Random;

import javax.swing.ImageIcon;

import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.GameLoad;

public class Map extends ElementObj {

    private int hp = 0; // 墙的血量，默认值为0
    private int type = 0; // 图片的类型，默认值为0，0代表地板，1代表可摧毁物，2代表障碍物
    private int sx = 0; // 图片分割的起点x轴
    private int sy = 0; // 图片分割的起点y轴
    private int dx = 0; // 图片分割的终点x轴
    private int dy = 0; // 图片分割的终点y轴

    @Override
    public void showElement(Graphics g) {
        // 做图片的分割
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getX() + 48, this.getY() + 48,
                sx, sy,
                dx, dy,
                null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        ImageIcon icon = null;
        // 这里是两张图的需要，分别对应GameData.pro里的map1和map2
        if (split[0].equals("tree") || split[0].equals("yellowhouse") || split[0].equals("bluehouse")) {
            this.type = 2;
            this.hp = 99999;
            this.setSort(2);
            setimage(split[0], "map2");
            icon = GameLoad.imgMap.get("map2");
        } else {
            if (split[0].equals("greenfloor")) {
                this.type = 0;
                this.setSort(1);
            } else {
                this.type = 1;
                this.hp = 1;
                this.setSort(2);
            }
            setimage(split[0], "map1");
            icon = GameLoad.imgMap.get("map1");
        }
        this.setX(Integer.parseInt(split[1]) * 48);
        this.setY(Integer.parseInt(split[2]) * 48);
        this.setIcon(icon);
        return this;
    }

    private static Properties pro = new Properties();

    private void setimage(String imageName, String map) {
        String image = null;
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        // 先去map(num).pro里读取图片截取的位置和图片大小
        InputStream maps = classLoader.getResourceAsStream("com/tedu/text/" + map + ".pro");
        if (maps == null) {
            System.out.println("配置文件读取异常,请重新安装");
            return;
        }
        try {
            pro.clear();
            pro.load(maps);
            Enumeration<?> names = pro.propertyNames();
            while (names.hasMoreElements()) {
                String key = names.nextElement().toString();
                if (key.equals(imageName)) {
                    String[] split = pro.getProperty(key).split(",");
                    this.sx = Integer.parseInt(split[0]);
                    this.sy = Integer.parseInt(split[1]);
                    this.setW(Integer.parseInt(split[2]));
                    this.setH(Integer.parseInt(split[3]));
                    this.dx = sx + this.getW();
                    this.dy = sy + this.getH();
                    break;
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void setLive(boolean live) {
        if (this.type == 0) {
            return;
        } else if (this.type == 1) {
            this.hp--;
            if (this.hp > 0) {
                return;
            }
        }
        super.setLive(live);
    }

    @Override
    public int compareTo(ElementObj o) {
        return this.getSort() - o.getSort();
    }

    @Override
    public Rectangle getRectangle() {
        // TODO Auto-generated method stub
        return new Rectangle(this.getX(), this.getY(), 48, 48);
    }

    public int getType() {
        return type;
    }

    @Override
    public void die(long gametime) {
        if (!this.isLive()) {
            Random random = new Random();
            int r = random.nextInt(100);
            String str = this.toString(r);
            if (r <= 40 && r > 0) {
                ElementObj obj = new Tool().createElement(str);
                ElementManager.getManager().addElement(obj, GameElement.TOOL);
                return;
            } else {
                return;
            }
        }
    }

    public String toString(int r) {
        String str = "x:" + this.getX() + ",y:" + this.getY() + ",n:" + r;
        return str;
    }
}
