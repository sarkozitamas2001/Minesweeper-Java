/*
    Sarkozi Tamas-David, 524
    Minesweeper
 */
import javax.swing.*;
import java.awt.*;

public class LoseFrame extends JFrame {

    //Vesztes eseten jelenik meg
    public LoseFrame(double gameTime) {

        this.setLayout(new GridLayout(1, 0));
        this.setBounds(500, 50, 350, 150);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("Result");

        JPanel loser = new JPanel();
        loser.setBackground(new Color(0xC40404));
        this.add(loser);
        loser.setLayout(new BoxLayout(loser, BoxLayout.Y_AXIS));

        JLabel boom = new JLabel("Boom!");
        boom.setFont(new Font("Ariel", Font.PLAIN, 25));
        boom.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lost = new JLabel("You lost!");
        lost.setFont(new Font("Ariel", Font.PLAIN, 25));
        lost.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel time = new JLabel(String.valueOf(gameTime));
        time.setFont(new Font("Ariel", Font.PLAIN, 25));
        time.setAlignmentX(Component.CENTER_ALIGNMENT);

        loser.add(boom);
        loser.add(lost);
        loser.add(time);
    }
}
