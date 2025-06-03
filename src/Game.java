import javax.swing.*;

public class Game extends JFrame {

    Board board = new Board();
    Music music = new Music("Assets/sounds/BGM.wav");


    public Game() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setTitle("Shooting Craft ");
        setResizable(false);

        add(board);
        music.play();

        setVisible(true);

    }
}
