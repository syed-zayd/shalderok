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
    
    frame.setSize(800, 600);;
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


    final double UPDATE_INTERVAL = 1.0 / 100.0; // Update 100 times per second
    double deltaTime = 0;
    long lastTime = System.nanoTime();

    while (true) {
      long now = System.nanoTime();
      deltaTime += (now - lastTime) / 1e9;
      lastTime = now;

      while (deltaTime >= UPDATE_INTERVAL) {
        world.update();
        deltaTime -= UPDATE_INTERVAL;
      }
      world.repaint();
    }
  }

  public static Dimension getScreenSize() {
    return frame.getContentPane().getSize();
  }
}