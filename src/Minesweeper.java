/*
    Sarkozi Tamas-David, 524
    Minesweeper
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;

//Maga a jatek. Itt kezelodik a jatek es itt lehet lementeni az adott jatekot
public class Minesweeper extends JFrame implements ActionListener {
    private JButton [][] cellak;
    private JPanel [][] panelek;
    private int [][] ertekek;
    private int [][] ertekekEredeti;
    private Random random;
    private int x, y, n, pontok;
    private WinFrame win;
    private LoseFrame lose;
    private Instant start, finish;
    private double timeElapsed;
    private JMenuBar menu;
    private JMenuItem save;
    private JFileChooser file;

    public Minesweeper (int x, int y) {
        this.x=x;
        this.y=y;
        this.setTitle("Minesweeper");
        pontok = x*y; //A pontok valtozo minden biztonsagos zona eltavolitasa utan csokken, ha egyenlo lesz az aknak szamaval akkor nyertunk;

        if(x==10){
            setBounds(500,50,360,360);
            n=10;
        }
        else if(x==18){
            setBounds(500,50,600,600);
            n=40;
        }
        else{
            setBounds(500,25,800,800);
            n=60;
        }

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(x,y));

        file = new JFileChooser(new File("C:\\Users\\sarko\\Desktop\\Java\\Projekt\\src"));

        //A menuben levo save Item segitsegevel lementhetunk egy jatekot
        menu = new JMenuBar();
        save = new JMenuItem("Save");
        menu.add(save);

        this.setJMenuBar(menu);

        save.addActionListener(this);

        cellak = new JButton[x][y];
        ertekek = new int[x][y];
        ertekekEredeti= new int[x][y]; //ertekekEredeti tartalmazni fogja az ertekek matrix eredeti valtozatat. Ez segiteni fog mikor egy mar lementet jatekot toltunk be
        panelek = new JPanel[x][y];
        random = new Random();

        //Veletlenszeruen lerak n aknat, az aknakat -1 reprezantalja az ertekek matrixban
        for(int i=0;i<n;i++){
            int rx, ry;
            rx = random.nextInt(0,x);
            ry = random.nextInt(0,y);

            //Nem rak aknat olyan cellaba mely mar egy masik akna teruleten van
            while(ertekek[rx][ry]!=0) {
                rx = random.nextInt(0, x);
                ry = random.nextInt(0, y);
            }

            ertekek[rx][ry]=-1;
            ertekekEredeti[rx][ry]=-1;

            //Az aknat korbevevo nyolc cellat noveli eggyel
            if(rx-1 >= 0)
                if(ertekek[rx-1][ry] != -1) {
                    ertekek[rx - 1][ry]++;
                    ertekekEredeti[rx - 1][ry]++;
                }
            if(rx+1 < x)
                if(ertekek[rx+1][ry] != -1) {
                    ertekek[rx + 1][ry]++;
                    ertekekEredeti[rx + 1][ry]++;
                }
            if(ry-1 >= 0)
                if(ertekek[rx][ry-1] != -1) {
                    ertekek[rx][ry - 1]++;
                    ertekekEredeti[rx][ry - 1]++;
                }
            if(ry+1 < y)
                if(ertekek[rx][ry+1] != -1) {
                    ertekek[rx][ry + 1]++;
                    ertekekEredeti[rx][ry + 1]++;
                }
            if(rx-1 >=0 && ry-1 >= 0)
                if(ertekek[rx-1][ry-1] != -1) {
                    ertekek[rx - 1][ry - 1]++;
                    ertekekEredeti[rx - 1][ry - 1]++;
                }
            if(rx+1 < x && ry-1 >= 0)
                if(ertekek[rx+1][ry-1] != -1) {
                    ertekek[rx + 1][ry - 1]++;
                    ertekekEredeti[rx + 1][ry - 1]++;
                }
            if(rx-1 >=0 && ry+1 < y)
                if(ertekek[rx-1][ry+1] != -1) {
                    ertekek[rx - 1][ry + 1]++;
                    ertekekEredeti[rx - 1][ry + 1]++;
                }
            if(rx+1 < x && ry+1 < y)
                if(ertekek[rx+1][ry+1] != -1) {
                    ertekek[rx + 1][ry + 1]++;
                    ertekekEredeti[rx + 1][ry + 1]++;
                }
        }

        for (int i = 0; i<x; i++){
            for (int j=0;j<y;j++){

                if(ertekek[i][j]!=-1) {
                    panelek[i][j] = new JPanel();
                    panelek[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                    panelek[i][j].setBackground(new Color(0x06C524));
                    panelek[i][j].setLayout(new GridLayout(1, 0));
                    this.add(panelek[i][j]);
                }
                else{
                    panelek[i][j] = new JPanel();
                    panelek[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                    panelek[i][j].setBackground(new Color(0xC40404));
                    panelek[i][j].setLayout(new GridLayout(1, 0));
                    this.add(panelek[i][j]);
                }

                cellak[i][j]=new JButton("");
                cellak[i][j].setBackground(new Color(0x1E6503));
                panelek[i][j].add(cellak[i][j]);
                cellak[i][j].addActionListener(this);
            }
        }

        start = Instant.now();
    }

    //Ez a konstruktor egy lementet jatek betoltese eseten jelenik meg
    public Minesweeper (int x, int y, int pontok, int[][] ertekek, int[][] ertekekEredeti) {
        this.x=x;
        this.y=y;
        this.pontok=pontok;
        this.ertekek=ertekek;
        this.ertekekEredeti=ertekekEredeti;
        this.setTitle("Minesweeper");

        if(x==10){
            setBounds(500,50,360,360);
            n=10;
        }
        else if(x==18){
            setBounds(500,50,600,600);
            n=40;
        }
        else{
            setBounds(500,25,800,800);
            n=60;
        }

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(x,y));

        file = new JFileChooser(new File("C:\\Users\\sarko\\Desktop\\Java\\Projekt\\src"));

        menu = new JMenuBar();
        save = new JMenuItem("Save");
        menu.add(save);

        this.setJMenuBar(menu);

        save.addActionListener(this);

        cellak = new JButton[x][y];
        panelek = new JPanel[x][y];

        for (int i=0;i<x; i++){
            for (int j=0;j<y;j++){
                //Az ertekekEredeti tomb segitsegevel tudja melyik mer felfedezett cellahoz milzen ertek tartozik
                if(ertekek[i][j]!=-2) {
                    panelek[i][j] = new JPanel();
                    panelek[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                    if(ertekek[i][j]!=-1) {
                        panelek[i][j].setBackground(new Color(0x06C524));
                    }
                    else {
                        panelek[i][j].setBackground(new Color(0xC40404));
                    }
                    panelek[i][j].setLayout(new GridLayout(1, 0));
                    this.add(panelek[i][j]);

                    cellak[i][j] = new JButton("");
                    cellak[i][j].setBackground(new Color(0x1E6503));
                    panelek[i][j].add(cellak[i][j]);
                    cellak[i][j].addActionListener(this);
                }
                else{
                    panelek[i][j] = new JPanel();
                    panelek[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                    panelek[i][j].setBackground(new Color(0x06C524));
                    panelek[i][j].setLayout(new GridLayout(1, 0));
                    this.add(panelek[i][j]);

                    Label label;

                    if(ertekekEredeti[i][j]!=0)
                        label = new Label("" + String.valueOf(ertekekEredeti[i][j]));
                    else
                        label = new Label("");
                    panelek[i][j].add(label);
                }
            }
        }
        //Innen kezdodik a jatek idomerese
        start = Instant.now();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==save) {
            //A save gomb lenyomasa utan egy altalunk letrehozott file-ba lementi az x,y,pontok,ertekek,ertekekEredeti ertekeket
            int r = file.showSaveDialog(null);

            //File letrehozasa
            if (r == JFileChooser.APPROVE_OPTION) {
                File f = file.getSelectedFile();
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
                try {
                    //File feltoltese a szukseges adatokkal
                    FileWriter fileWriter = new FileWriter(f.getAbsolutePath());

                    fileWriter.write(String.valueOf(x) + " " + String.valueOf(y) + "\n");
                    fileWriter.write(String.valueOf(pontok) + "\n");

                    for(int i=0;i<x;i++){
                        for(int j=0;j<y;j++){
                            fileWriter.write(String.valueOf(ertekek[i][j]) + " ");
                        }
                        fileWriter.write("\n");
                    }

                    for(int i=0;i<x;i++){
                        for(int j=0;j<y;j++){
                            fileWriter.write(String.valueOf(ertekekEredeti[i][j]) + " ");
                        }
                        fileWriter.write("\n");
                    }

                    fileWriter.close();
                } catch (IOException ex) {
                    System.out.println("Error!");
                }
            }
        }
        else {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    if (e.getSource() == cellak[i][j]) {
                        //Ha egy olyan cellara lepunk mely akna kozeleben van akkor a gomb helyett az adott ertek jelenik meg
                        if (ertekek[i][j] > 0) {
                            panelek[i][j].remove(cellak[i][j]);
                            Label label = new Label("" + String.valueOf(ertekek[i][j]));
                            panelek[i][j].add(label);
                            panelek[i][j].revalidate();
                            panelek[i][j].repaint();
                            //Kicsereljuk a cella erteket -2-re hogy tudjuk hogy mar jartunk itt
                            ertekek[i][j] = -2;
                            //csokentjuk a pontokat
                            pontok--;
                        //Ha olyan cellara lepunk mely nincs akna kozeleben akkor a gomb csak eltunik
                        } else if (ertekek[i][j] == 0) {
                            //A felszabadit rekurziv fuggveny segitsegevel felszabadit minden olzan nulla erteku cellat mely nin izolalva
                            felszabadit(i, j);
                        //Ha aknara lepunk
                        } else {
                            //Megall az idomeres
                            finish = Instant.now();
                            timeElapsed = Duration.between(start, finish).toSeconds();
                            //Megjeleni minden erteket, az aknakat is
                            for (int k = 0; k < x; k++) {
                                for (int l = 0; l < y; l++) {
                                    if (ertekek[k][l] != -2) {
                                        panelek[k][l].remove(cellak[k][l]);
                                        Label label;
                                        if (ertekek[k][l] == -1) {
                                            label = new Label("Boom!");
                                        } else if (ertekek[k][l] == 0) {
                                            label = new Label("");
                                        } else {
                                            label = new Label("" + String.valueOf(ertekek[k][l]));
                                        }
                                        panelek[k][l].add(label);
                                        panelek[k][l].revalidate();
                                        panelek[k][l].repaint();
                                    }
                                }
                            }
                            //Megjelenik a lose frame mely kirja a jatekidot
                            LoseFrame lose = new LoseFrame(timeElapsed);
                            lose.setVisible(true);
                        }
                        break;
                    }
                }
            }
            //Ha a pontok valtozo erteke egyenlo lesz az aknak szamaval nyertunk
            if (pontok == n) {
                //Ugyanaz tortenik mint vesztes eseten
                finish = Instant.now();
                timeElapsed = Duration.between(start, finish).toSeconds();
                for (int k = 0; k < x; k++) {
                    for (int l = 0; l < y; l++) {
                        if (ertekek[k][l] != -2) {
                            panelek[k][l].remove(cellak[k][l]);
                            Label label;
                            if (ertekek[k][l] == -1) {
                                label = new Label("Mine!");
                            } else if (ertekek[k][l] == 0) {
                                label = new Label("");
                            } else {
                                label = new Label("" + String.valueOf(ertekek[k][l]));
                            }
                            panelek[k][l].add(label);
                            panelek[k][l].revalidate();
                            panelek[k][l].repaint();
                        }
                    }
                }
                //A win frame megjelenik mely kiirja a jatekidot
                WinFrame win = new WinFrame(timeElapsed);
                win.setVisible(true);
            }
        }
    }

    private void felszabadit(int i, int j){
        panelek[i][j].remove(cellak[i][j]);
        Label label = new Label("");
        panelek[i][j].add(label);
        panelek[i][j].revalidate();
        panelek[i][j].repaint();
        ertekek[i][j]=-2;
        pontok--;

        if(i > 0)
            if(ertekek[i-1][j]==0)
                felszabadit(i-1,j);
            else if(ertekek[i-1][j]>0){
                panelek[i-1][j].remove(cellak[i-1][j]);
                label = new Label("" + String.valueOf(ertekek[i-1][j]));
                panelek[i-1][j].add(label);
                panelek[i-1][j].revalidate();
                panelek[i-1][j].repaint();
                ertekek[i-1][j]=-2;
                pontok--;
            }
        if(i < x-1)
            if(ertekek[i+1][j]==0)
                felszabadit(i+1,j);
            else if(ertekek[i+1][j]>0){
                panelek[i+1][j].remove(cellak[i+1][j]);
                label = new Label("" + String.valueOf(ertekek[i+1][j]));
                panelek[i+1][j].add(label);
                panelek[i+1][j].revalidate();
                panelek[i+1][j].repaint();
                ertekek[i+1][j]=-2;
                pontok--;
            }
        if(j > 0)
            if(ertekek[i][j-1]==0)
                felszabadit(i,j-1);
            else if(ertekek[i][j-1]>0){
                panelek[i][j-1].remove(cellak[i][j-1]);
                label = new Label("" + String.valueOf(ertekek[i][j-1]));
                panelek[i][j-1].add(label);
                panelek[i][j-1].revalidate();
                panelek[i][j-1].repaint();
                ertekek[i][j-1]=-2;
                pontok--;
            }
        if(j < y-1)
            if(ertekek[i][j+1]==0)
                felszabadit(i,j+1);
            else if(ertekek[i][j+1]>0){
                panelek[i][j+1].remove(cellak[i][j+1]);
                label = new Label("" + String.valueOf(ertekek[i][j+1]));
                panelek[i][j+1].add(label);
                panelek[i][j+1].revalidate();
                panelek[i][j+1].repaint();
                ertekek[i][j+1]=-2;
                pontok--;
            }
    }
}
