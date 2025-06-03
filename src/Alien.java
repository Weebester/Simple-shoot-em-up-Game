import javax.swing.*;
import java.awt.*;

public class Alien {
    private int x;
    private int y;
    private int dx;
    private Image image;
    boolean Super;

    public Alien(int x, int y, int dx, boolean Super) {
        this.Super = Super;
        this.x = x;
        if (Super){
            setImage("Assets/Aliens/tank.png");
            this.y=620;
        }
        else{
            setImage("Assets/Aliens/Alien.png");
            this.y = y;

        }

        setDx(dx);

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
        x -= dx;
        if (x < -100) {
            return true;
        }
        return false;
    }

    public Image getImage() {
        return image;
    }
}
