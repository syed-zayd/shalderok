import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    private static final boolean[] KEYS_HELD = new boolean[0x10000];

    public void keyPressed(KeyEvent e) {
        KEYS_HELD[e.getKeyCode()] = true;

        if (e.getKeyCode() == KeyEvent.VK_R) {
            World.camera.resetZoom();
        } else if (e.getKeyCode() == KeyEvent.VK_F11) {
            toggleFullscreen();
        }
    }

    public void keyReleased(KeyEvent e) {
        KEYS_HELD[e.getKeyCode()] = false;
    }

    public static boolean isHeld(int key) {
        return KEYS_HELD[key];
    }

	public void keyTyped(KeyEvent e) {}


    private void toggleFullscreen() {
        Main.frame.dispose();

        if (Main.frame.isUndecorated()) {
          Main.frame.setUndecorated(false);
        } else {
          Main.frame.setUndecorated(true);
        }
    
        Main.frame.setVisible(true);
    }
}