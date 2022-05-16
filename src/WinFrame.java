/*
    Sarkozi Tamas-David, 524
    Minesweeper
 */
import javax.swing.*;
import java.awt.*;

public class WinFrame extends JFrame {

    //Nyeres eseten jelenik meg
    public WinFrame (double gameTime) {
        this.setLayout(new GridLayout(1,0));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setBounds(500,50,350,150);
        this.setTitle("Result");

        JPanel winner = new JPanel();
        winner.setBackground(new Color(0x06C524));
        this.add(winner);
        winner.setLayout(new BoxLayout(winner,BoxLayout.Y_AXIS));

        JLabel win = new JLabel("You Won!");
        win.setFont(new Font("Ariel",Font.PLAIN,25));
        win.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel congrats = new JLabel("Congratulations!");
        congrats.setFont(new Font("Ariel",Font.PLAIN,25));
        congrats.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel time = new JLabel(String.valueOf(gameTime));
        time.setFont(new Font("Ariel",Font.PLAIN,25));
        time.setAlignmentX(Component.CENTER_ALIGNMENT);

        winner.add(win);
        winner.add(congrats);
        winner.add(time);
    }
}
