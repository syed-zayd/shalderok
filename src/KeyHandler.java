import java.awt.event.KeyEvent;

public class KeyHandler {
    private static final boolean[] KEYS_HELD = new boolean[0x10000];

    public static void keyPressed(KeyEvent e) {
        KEYS_HELD[e.getKeyCode()] = true;

        Player.getInstance().keyPressed(e);
    }

    public static void keyReleased(KeyEvent e) {
        KEYS_HELD[e.getKeyCode()] = false;

        Player.getInstance().keyReleased(e);
    }

    public static boolean isHeld(int key) {
        return KEYS_HELD[key];
    }

}
