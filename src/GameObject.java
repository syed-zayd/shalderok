import java.awt.Graphics2D;

abstract class GameObject {
    double x, y;
    int w, h;
    String state;
    int frameIndex;
    Sprite sprite;


    public GameObject(double x, double y, int w, int h, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.sprite = sprite;
        this.state = "idle";
        this.frameIndex = 0;
    }

    public int drawX() {
        return (int) Math.round(x);
    }
    public int drawY() {
        return (int) Math.round(y);
    }
    public int drawCenterX() {
        return (int) Math.round((2*x+w)/2.);
    }
    public int drawCenterY() {
        return (int) Math.round((2*y+h)/2.);
    }

    public int drawXFromCenter(double centerX) {
        return (int) Math.round(centerX - (w / 2.));
    }

    public int drawYFromCenter(double centerY) {
        return (int) Math.round(centerY - (h / 2.));
    }

    public void setSprite(Sprite sprite){
        this.sprite = sprite;
    }

    public void paint(Graphics2D g2d){
        if(sprite != null){
            g2d.drawImage(sprite.getSprite(state, frameIndex), drawX(), drawY(), null);
        }
    }

    public abstract void update();
    public abstract boolean isSolid();
    public abstract void interact();
}