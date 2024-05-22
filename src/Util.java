import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Util {
    static Point lastMouseMove = new Point(0,0);

    static int panelWidth = 0;
    static int panelHeight = 0;

    public static void updateFrameSize(Dimension size) {
        panelWidth = size.width;
        panelHeight = size.height;
    }

    // public static void updateLastMouseMove() {
    //     Player p = Player.getInstance();
    //     Util.lastMouseMove = MouseInfo.getPointerInfo().getLocation();
    //     Util.lastMouseMove.translate(-(int) (panelWidth / 2 - p.getHitbox().getCenterX()), -(int) (panelHeight / 2 - p.getHitbox().getCenterY()));
    // }

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
}