import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Util {

    public static BufferedImage rotateImage(BufferedImage img, double angle) {
        double sin = Math.abs(Math.sin(angle)), cos = Math.abs(Math.cos(angle));
        int w = img.getWidth();
        int h = img.getHeight();
        int newWidth = (int) Math.floor(w * cos + h * sin);
        int newHeight = (int) Math.floor(h * cos + w * sin);
    
        BufferedImage rotated = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = rotated.createGraphics();
        AffineTransform at = new AffineTransform();
        at.translate((newWidth - w) / 2, (newHeight - h) / 2);
    
        int x = w / 2;
        int y = h / 2;
    
        at.rotate(angle, x, y);
        g2d.setTransform(at);
        g2d.dispose();
    
        return rotated;
    }

    public static void drawCenteredString(Graphics2D g2d, String text, int centerX, int y) {
        // Get font metrics for the current font
        FontMetrics metrics = g2d.getFontMetrics(g2d.getFont());

        // Determine the X coordinate for the text such that it's centered at centerX
        int x = centerX - (metrics.stringWidth(text) / 2);

        // Draw the string
        g2d.drawString(text, x, y);
    }

    public static int randInt(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

    public static String randDirection(boolean up, boolean down, boolean left, boolean right) {
        int options = (up?1:0) + (down?1:0) + (left?1:0) + (right?1:0);
        int r = Util.randInt(1, options);
        int i=1;
        if (up && r==i++) {
            return "up";
        }
        if (down && r==i++) {
            return "down";
        }
        if (left && r==i++) {
            return "left";
        }
        if (right && r==i++) {
            return "right";
        }
        return "none";
    }

    public static void centerPosition(GameObject obj1, GameObject obj2) {
        obj1.x = obj2.x + (obj2.w/2.) - obj1.w/2.;
        obj1.y = obj2.y + (obj2.h/2.) - obj1.h/2.;
    }

    public static String capitalize(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}