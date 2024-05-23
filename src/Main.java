import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
  static JFrame frame = new JFrame("camera test");
  static World world = new World();

  public static void main(String[] args) throws InterruptedException {
    frame.setSize(800, 600);;
    frame.add(world);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setUndecorated(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set custom cursor
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage("sprites/cursor.png");
    Cursor c = toolkit.createCustomCursor(image, new Point(16,16), "gameplay");
    world.setCursor (c);

    while (true) {
      world.update();
      world.repaint();
      Thread.sleep(5);
    }
  }

  public static Dimension getScreenSize() {
    return frame.getContentPane().getSize();
  }
}