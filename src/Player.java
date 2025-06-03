import javax.swing.*;
import java.awt.*;

public class Player {
    private int x;
    private int y;
    private int dx;
    private int dy;
    private double ac;
    private int frame;
    private Image[] sprite = new Image[8];

    public Player() {
        for (int i = 0; i < sprite.length; i++) {
            ImageIcon t = new ImageIcon("Assets/Player/" + i + ".png");
            sprite[i] = t.getImage();
        }
        x = 100;
        y = 150;
        frame=0;
        dx = 0;
        dy = 0;
        ac = 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setDX(int t) {
        dx = t;
    }

    public void setDY(int t) {
        dy = t;
    }

    public void setAC(double t) {
        ac = t;
        System.out.println(ac);
    }

    public void move() {
        y += (dy * ac);
        if (y < -10) {
            y = -10;
        } else if (y > 630) {
            y = 630;
        }
        x += (dx * ac);
        if (x < -10) {
            x = -10;
        } else if (x > 1120) {
            x = 1120;
        }

    }

    public Image getSprite() {
        frame++;
        frame%=8;
        return sprite[frame];

    }
}
