public abstract class Item extends GameObject {

    public Item(double x, double y, int w, int h, Sprite s) {
        super(x, y, w, h, s);   
    }
    
    public abstract void use();

}
