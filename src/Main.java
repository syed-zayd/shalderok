import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel{

    static Player p = Player.getInstance();

    public Main() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf("%d, %d\n", e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("shalderok");
        Main panel = new Main();
        frame.add(panel);
        frame.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);

        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Player.getInstance().paint(g2d);
    }
}