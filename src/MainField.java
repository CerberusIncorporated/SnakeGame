import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;


public class MainField extends JPanel implements ActionListener {
    private final int SIZE = 640;
    private final int DOT_SIZE = 64; //размер картинок
    private final int ALL_DOTS = 660;
    private Image dot;
    private Image frog;
    private int frogX;
    private int frogY;
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];
    private int dots;
    private Timer timer;
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public MainField(){
        setBackground(Color.black);
        loadImages();
        initGame();
        addKeyListener(new FieldKeyListener());
        setFocusable(true);
    }

    public void initGame(){
        dots = 3;
        for (int i = 0; i < dots; i++) {
            x[i] = 192 - i*DOT_SIZE;
            y[i] = 192;
        }
        timer = new Timer(250,this);
        timer.start();
        createFrog();
    }
    public void createFrog(){
        frogX = new Random().nextInt(10)*DOT_SIZE;
        frogY = new Random().nextInt(10)*DOT_SIZE;
    }

    public void loadImages(){
        ImageIcon loadFrog = new ImageIcon(getClass().getResource("/image/frog.png"));
        frog = loadFrog.getImage();
        ImageIcon loadDot = new ImageIcon(getClass().getResource("/image/sun.png"));
        dot = loadDot.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(inGame){
            g.drawImage(frog,frogX,frogY,this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot,x[i],y[i],this);
            }
        }else{
            String str = "Game over";

            g.setColor(Color.white);

            g.drawString(str,250,SIZE/2);
        }
    }

    public void move(){
        for (int i = dots; i > 0; i--) {
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        if(left){
            x[0] -= DOT_SIZE;
        }
        if(right){
            x[0] += DOT_SIZE;
        } if(up){
            y[0] -= DOT_SIZE;
        } if(down){
            y[0] += DOT_SIZE;
        }
    }

    public void checkFrog(){
        if(x[0] == frogX && y[0] == frogY){
            dots++;
            createFrog();
        }
    }

    public void checkCollisions(){
        for (int i = dots; i >0 ; i--) {
            if(i>4 && x[0] == x[i] && y[0] == y[i]){
                inGame = false;
            }
        }

        if(x[0]>SIZE){
            inGame = false;
        }
        if(x[0]<0){
            inGame = false;
        }
        if(y[0]>SIZE){
            inGame = false;
        }
        if(y[0]<0){
            inGame = false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(inGame){
            checkFrog();
            checkCollisions();
            move();

        }
        repaint();
    }
    class FieldKeyListener extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_LEFT && ! right){
                left = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_RIGHT && ! left){
                right = true;
                up = false;
                down = false;
            }
            if(key == KeyEvent.VK_UP && ! down){
                up = true;
                right = false;
                left = false;
            }
            if(key == KeyEvent.VK_DOWN && ! up){
                down = true;
                right = false;
                left = false;
            }
        }
    }
}
