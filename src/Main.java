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

        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {}

            @Override
            public void mouseMoved(MouseEvent e) {}
            
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
        JFrame frame = new JFrame("shalderok");
        Main panel = new Main();
        
        // set custom cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("sprites/cursor.png");
        Cursor c = toolkit.createCustomCursor(image, new Point(16,16), "gameplay");
        panel.setCursor (c);
        
        frame.add(panel);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // frame.setUndecorated(true);
        frame.setVisible(true);

        Util.updateFrameSize(frame.getContentPane().getSize());

        frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (true) {
            WorldManager.getInstance().update();
            panel.repaint();
            Thread.sleep(20);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate((int)(Util.panelWidth/2-p.getHitbox().getCenterX()), (int)(Util.panelHeight/2-p.getHitbox().getCenterY()));
        WorldManager.getInstance().paint(g2d);
    }

}