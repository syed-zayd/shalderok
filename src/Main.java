import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Main {
  public static JFrame frame = new JFrame("camera test");
  public static World world;
  static double fps;

  public static void main(String[] args) throws InterruptedException {
    
    System.out.println("Loading sprites...");
    SpriteLoader.loadSprites();

    world = new World();
    
    frame.setSize(800, 600);
    frame.add(world);
    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
    frame.setUndecorated(false);
    frame.setVisible(true);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // set custom cursor
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    Image image = toolkit.getImage("graphics/cursor.png");
    Cursor c = toolkit.createCustomCursor(image, new Point(16,16), "gameplay");
    world.setCursor(c);

    while (true) {
      world.update();
      world.repaint();
      Thread.sleep(10);
    }
  }

  public static Dimension getScreenSize() {
    return frame.getContentPane().getSize();
  }
}