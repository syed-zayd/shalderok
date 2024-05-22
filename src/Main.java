import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel{
    static JFrame frame = new JFrame("shalderok");
    static Main panel = new Main();
    static Player p = Player.getInstance();
    static boolean a = false;

    public Main() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.printf("%d, %d\n", e.getX(), e.getY());
                panel.setAlignmentX(0.5f);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // a = !(a);
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
            
        });

        addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                KeyHandler.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                KeyHandler.keyReleased(e);
            }
            
        });

        setFocusable(true);
    }

    public static void main(String[] args) throws InterruptedException {        
        // set custom cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("sprites/cursor.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(16, 16), "gameplay");
        panel.setCursor (c);
        
        frame.add(panel);
        frame.setSize(0, 0);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        frame.setUndecorated(false);
        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            WorldManager.getInstance().update();
            panel.repaint();
            Thread.sleep(10);
        }
    }

    public double panelWidth() {
        return frame.getContentPane().getSize().getWidth();
    }
    public double panelHeight() {
        return frame.getContentPane().getSize().getHeight();
    }
    public int centerX() {
        return (int)panelWidth()/2;
    }
    public int centerY() {
        return (int)panelHeight()/2;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setClip(new Rectangle(-100, -100, 1000, 1000));

        Util.xOffset = p.x;
        Util.yOffset = p.y;

        if (a) {
            g2d.scale(0.5, 0.5);
        } else {
            g2d.scale(1,1);
        }
        // g2d.translate(centerX()-((int)p.hitbox.x+(int)p.hitbox.w)/2, centerY()-((int)p.y+p.h)/2);
        WorldManager.getInstance().paint(g2d);
    }

}