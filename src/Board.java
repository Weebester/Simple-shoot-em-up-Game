import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Board extends JPanel implements ActionListener {

    public void start() {
        alive = true;
        player = new Player();
        bulletGen = 0;
        bulletGenRate = 10;
        AliensGen = 0;
        AlienSpeed = 3;
        AliensGenRate = 50;
        SuperEntropy = 50;
        bullets.clear();
        Aliens.clear();
        time = 0;
        score = 0;
        dif = 0;
        this.remove(GO);
        this.remove(retry);
        this.remove(Time);
        this.remove(Score);
        gameLoop.start();

    }


    JLabel GO = new JLabel("Game Over");
    JLabel Time = new JLabel("");
    JLabel Score = new JLabel("");
    JLabel retry = new JLabel("press Enter to retry");
    int score, time, dif;
    boolean alive = false;
    Random R = new Random();

    //player
    private Player player;
    private ArrayList<bullet> bullets = new ArrayList<>();
    private int bulletGen;
    private int bulletGenRate;
    private boolean shooting = false;

    private ArrayList<Alien> Aliens = new ArrayList<>();
    private int AliensGen;
    private int AliensGenRate;
    private int AlienSpeed;
    private int SuperEntropy;

    private Music SSE = new Music("Assets/sounds/gunSound.wav");
    //other
    private Timer gameLoop = new Timer(10, this);
    private ImageIcon img = new ImageIcon("Assets/Sky.png");


    public Board() {
        setFocusable(true);
        addKeyListener(KA);
        setDoubleBuffered(true);
        start();
        GO.setLocation(380, 100);
        GO.setFont(new Font("Arial", Font.BOLD, 100));
        GO.setSize(800, 100);
        GO.setForeground(Color.ORANGE);

        Time.setLocation(520, 200);
        Time.setFont(new Font("Arial", Font.BOLD, 40));
        Time.setSize(400, 100);
        Time.setForeground(Color.ORANGE);

        Score.setLocation(520, 300);
        Score.setFont(new Font("Arial", Font.BOLD, 40));
        Score.setSize(400, 100);
        Score.setForeground(Color.ORANGE);


        retry.setLocation(450, 400);
        retry.setSize(400, 100);
        retry.setFont(new Font("Arial", Font.BOLD, 40));
        retry.setForeground(Color.ORANGE);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(img.getImage(), 0, 0, this);
        g2d.drawImage(player.getSprite(), player.getX(), player.getY(), 160, 60, this);
        for (int i = 0; i < bullets.size(); i++) {
            g2d.drawImage(bullets.get(i).getImage(), bullets.get(i).getX(), bullets.get(i).getY(), this);
        }
        for (int i = 0; i < Aliens.size(); i++) {
            g2d.drawImage(Aliens.get(i).getImage(), Aliens.get(i).getX(), Aliens.get(i).getY(), 50, 66, this);
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        time++;

        bulletGen += 1;
        if (bulletGen > bulletGenRate && shooting) {
            bulletGen = 0;
            bullets.add(new bullet(player.getX() + 120, player.getY() + 32, false));

        }
        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).move()) {
                bullets.remove(i);
                i--;
            } else {
                for (int j = 0; j < Aliens.size(); j++) {

                    int BMinX = bullets.get(i).getX();
                    int BMaxX = BMinX + 20;
                    int BMinY = bullets.get(i).getY();
                    int BMaxY = BMinY + 10;

                    int AMinX = Aliens.get(j).getX();
                    int AMaxX = AMinX + 50;
                    int AMinY = Aliens.get(j).getY();
                    int AMaxY = AMinY + 66;

                    int InterXMin = Math.max(BMinX, AMinX);
                    int InterXMax = Math.min(BMaxX, AMaxX);
                    int InterYMin = Math.max(BMinY, AMinY);
                    int InterYMax = Math.min(BMaxY, AMaxY);

                    if (InterXMin < InterXMax && InterYMin < InterYMax) {
                        if (!Aliens.get(j).Super) {
                            Aliens.remove(j);
                            score++;
                        } else if (bullets.get(i).Super) {
                            Aliens.remove(j);
                            score++;
                        }
                        bullets.remove(i);
                        i--;
                        break;
                    }

                }
            }
        }

        AliensGen += 1;
        if (AliensGen > AliensGenRate) {
            AliensGen = 0;
            Aliens.add(new Alien(1280, (R.nextInt(1, 55)) * 10, AlienSpeed, R.nextInt(0, 100) > SuperEntropy));
        }
        for (int i = 0; i < Aliens.size(); i++) {
            if (Aliens.get(i).move()) {
                Aliens.remove(i);
                i--;
            } else {
                int PMinX = player.getX() + 30;
                int PMaxX = PMinX + 100;
                int PMinY = player.getY() + 10;
                int PMaxY = PMinY + 40;

                int AMinX = Aliens.get(i).getX() + 10;
                int AMaxX = AMinX + 30;
                int AMinY = Aliens.get(i).getY() + 10;
                int AMaxY = AMinY + 46;

                int InterXMin = Math.max(PMinX, AMinX);
                int InterXMax = Math.min(PMaxX, AMaxX);
                int InterYMin = Math.max(PMinY, AMinY);
                int InterYMax = Math.min(PMaxY, AMaxY);

                if (InterXMin < InterXMax && InterYMin < InterYMax) {
                    gameLoop.stop();
                    alive = false;
                    this.add(retry);
                    this.add(GO);
                    Score.setText("Score : " + score);
                    this.add(Score);
                    Time.setText("Time : " + (time / 100.0));
                    this.add(Time);
                }
            }
            // System.out.println(Aliens.size());
        }

        dif = (dif + 1) % 501;
        if (dif == 500) {
            if (AlienSpeed < 20) {
                AlienSpeed++;
            }
            if (AliensGenRate > 5)
                AliensGenRate--;
            if (SuperEntropy > 0)
                SuperEntropy--;

            System.out.println("GR:" + AliensGenRate);
            System.out.println("ZB:" + AlienSpeed);
            System.out.println("ER:" + SuperEntropy);
        }


        player.move();
        this.repaint();
    }

    KeyAdapter KA = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> player.setDY(-4);
                case KeyEvent.VK_DOWN -> player.setDY(4);
                case KeyEvent.VK_LEFT -> player.setDX(-4);
                case KeyEvent.VK_RIGHT -> player.setDX(4);
                case KeyEvent.VK_Z -> player.setAC(2);
                case KeyEvent.VK_SPACE -> {
                    shooting = true;
                    SSE.play();
                }

            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP -> player.setDY(0);
                case KeyEvent.VK_DOWN -> player.setDY(0);
                case KeyEvent.VK_LEFT -> player.setDX(0);
                case KeyEvent.VK_RIGHT -> player.setDX(0);
                case KeyEvent.VK_Z -> player.setAC(1);
                case KeyEvent.VK_SPACE -> {
                    shooting = false;
                    SSE.stop();
                }
                case KeyEvent.VK_ENTER -> {
                    if (!alive)
                        start();
                }
                case KeyEvent.VK_X -> bullets.add(new bullet(player.getX() + 120, player.getY() + 32, true));

            }
        }


    };
}

