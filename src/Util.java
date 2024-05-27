import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Util {

    public static BufferedImage rotateImage(BufferedImage buffImage, double angle) {
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
    
        int width = buffImage.getWidth();
        int height = buffImage.getHeight();
    
        int nWidth = (int) Math.floor((double) width * cos + (double) height * sin);
        int nHeight = (int) Math.floor((double) height * cos + (double) width * sin);
    
        BufferedImage rotatedImage = new BufferedImage(
                nWidth, nHeight, BufferedImage.TYPE_INT_ARGB);
    
        Graphics2D graphics = rotatedImage.createGraphics();
    
        graphics.setRenderingHint(
                RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    
        graphics.translate((nWidth - width) / 2, (nHeight - height) / 2);
        // rotation around the center point
        graphics.rotate(angle, (double) (width / 2), (double) (height / 2));
        graphics.drawImage(buffImage, 0, 0, null);
        graphics.dispose();
    
        return rotatedImage;
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
}