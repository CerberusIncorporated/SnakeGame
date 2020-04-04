import javax.swing.*;

public class MainWindow extends JFrame {
    public MainWindow(){
        setTitle("SnakeFrog");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(650,650);
        setLocation(200,50);
        add(new MainField());
        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow mw = new MainWindow();
    }
}
