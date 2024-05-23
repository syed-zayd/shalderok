import java.awt.Dimension;
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