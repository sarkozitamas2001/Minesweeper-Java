/*
    Sarkozi Tamas-David, 524
    Minesweeper
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

//A fomenu amely a jatekot inditja. Itt lehet kivalasztani an nehezseget es lehet betolteni egy lementet jatekot
public class Main extends JFrame implements ActionListener {
    private JButton b1, b2, b3, b4;
    private JPanel panel;
    private JFileChooser file;

    public Main () {
        setBounds(100,100,350,200);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Minesweeper");
        panel = new JPanel();

        panel.setLayout(new BoxLayout(panel,BoxLayout.X_AXIS));

        //b1,b2,b3 a jatek nehezsegek
        b1=new JButton("Easy");
        b2=new JButton("Medium");
        b3=new JButton("Hard");
        //Betolt egy lementet jatekot
        b4=new JButton("Load Game");

        file = new JFileChooser(new File("C:\\Users\\sarko\\Desktop\\Java\\Projekt\\src"));

        panel.setBorder(new EmptyBorder(new Insets(0, 18, 0, 0)));

        panel.add(b1);
        panel.add(b2);
        panel.add(b3);
        panel.add(b4);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);
        b4.addActionListener(this);

        this.add(panel);
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==b1){
            new Minesweeper(10,8).setVisible(true);
        }
        else if(e.getSource()==b2){
            new Minesweeper(18,14).setVisible(true);
        }
        else if(e.getSource()==b3){
            new Minesweeper(24,20).setVisible(true);
        }
        else{
            //Az altalunk kivalasztott file-bol kiolvassa az adatokat
            int r = file.showOpenDialog(null);

            if(r == JFileChooser.APPROVE_OPTION){
                String fname = file.getSelectedFile().getAbsolutePath();
                BufferedReader bf;
                try {
                    Scanner sc = new Scanner(new BufferedReader(new FileReader(fname)));
                    int x, y, pontok;
                    int [][] ertekek;
                    int [][] ertekekEredeti;

                    //A Scanner segitsegevel beolvasunk egy sort ugy hogy a space-ek szerint tobb stringre osztjuk fel a sort
                    String[] line = sc.nextLine().trim().split(" ");
                    x=Integer.parseInt(line[0]);
                    y=Integer.parseInt(line[1]);

                    ertekek = new int[x][y];
                    ertekekEredeti = new int[x][y];

                    line = sc.nextLine().trim().split(" ");
                    pontok = Integer.parseInt(line[0]);
                    for(int i=0;i<x;i++){
                        line = sc.nextLine().trim().split(" ");
                        for(int j=0;j<y;j++){
                            ertekek[i][j]=Integer.parseInt(line[j]);
                        }
                    }
                    for(int i=0;i<x;i++){
                        line = sc.nextLine().trim().split(" ");
                        for(int j=0;j<y;j++){
                            ertekekEredeti[i][j]=Integer.parseInt(line[j]);
                        }
                    }

                    new Minesweeper(x,y,pontok,ertekek,ertekekEredeti).setVisible(true);
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        }
    }
}
