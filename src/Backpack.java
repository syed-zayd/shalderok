import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

public class Backpack {
    
    private int numSlots;
    private int activeSlot;
    ArrayList<Item> items;

    public Backpack(int numSlots){
        this.numSlots = numSlots;
        items = new ArrayList<Item>();
        activeSlot = 0;
    }

    public void addItem(Item item){
        items.add(item);
    }

    public void removeItem(Item item){
        items.remove(item);
    }

    public Item getActiveItem(){
        return items.get(activeSlot);
    }

    public void setActiveSlot(int slot){
        activeSlot = slot;
    }

    public int size(){
        return items.size();
    }

    public void update(){
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            if(item.numUses <= 0){
                items.set(i, new Fists(World.p, World.p.x, World.p.y));
            }
        }
    }

    public Item swapItem(int x, int y, Item selectedItem){
        for(int i = 0; i < numSlots; i++){
            if(x >= hotbarX() + (40 * i) && x <= hotbarX() + (40 * i) + 40 && y >= hotbarY() && y <= hotbarY() + 40){
                Item swapItem = items.get(i);
                items.set(i, selectedItem);
                return swapItem;
            }
        }
        return null;
    }

    public void paint(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        for(int i = 0; i < numSlots; i++){
            if(i != activeSlot){
                g2d.drawRect(hotbarX() + (40 * i), hotbarY(), 40, 40);
            }
        }
        g2d.setColor(Color.RED);
        g2d.drawRect(hotbarX() + (40 * activeSlot), hotbarY(), 40, 40);
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            if(item.sprite != null){
                g2d.drawImage(item.sprite.getSprite(item.state, item.frameIndex), hotbarX() + (40 * i) + 4, hotbarY() + 4, null);
            }
        }
    }

    private int hotbarX(){
        return (int) ((Main.getScreenSize().getWidth() - (40 * numSlots)) / 2.);
    }

    private int hotbarY(){
        return (int) ((Main.getScreenSize().getHeight()) - 100);
    }

}
