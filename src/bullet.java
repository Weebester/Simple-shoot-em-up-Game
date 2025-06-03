import javax.swing.*;
import java.awt.*;

public class bullet {
    private int x;
    private int y;
    private int dx;
    private Image image;
    boolean Super;

    public bullet(int x, int y, boolean Super) {
        this.Super = Super;
        if (Super)
            setImage("Assets/projectiles/zuberBullet.png");
        else
            setImage("Assets/projectiles/bullet.png");
        this.x = x;
        this.y = y;
        setDx(6);

    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setImage(String path) {
        ImageIcon i = new ImageIcon(path);
        this.image = i.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean move() {
        if (Super) {
            y += dx;
            return y > 720;
        } else {
            x += dx;
            return x > 1261;
        }
    }

    public Image getImage() {
        return image;
    }
}
