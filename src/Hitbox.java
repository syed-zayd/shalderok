public class Hitbox {
    private int x;
    private int y;
    private int w;
    private int h;

    public Hitbox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public static boolean isColliding(Hitbox a, Hitbox b) {
        // Calculate the right and bottom edges of each hitbox
        int aRight = a.x + a.w;
        int aBottom = a.y + a.h;
        int bRight = b.x + b.w;
        int bBottom = b.y + b.h;
    
        // Check for collision along the x-axis and y-axis
        boolean xOverlap = a.x < bRight && aRight > b.x;
        boolean yOverlap = a.y < bBottom && aBottom > b.y;
    
        // Return true if there is overlap along both axes
        return xOverlap && yOverlap;
    }
    
}
