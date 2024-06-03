import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Backpack {
    
    private int numSlots;
    private int activeSlot;
    ArrayList<Item> items;

    public Backpack(int numSlots){
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

    public void paint(Graphics2D g2d){
        for(int i = 0; i < numSlots; i++){
            g2d.setColor(i == activeSlot ? Color.RED : Color.BLACK);
            g2d.drawRect(hotbarX() + (40 * i), 1000, 40, 40);
        }
        for(int i = 0; i < items.size(); i++){
            Item item = items.get(i);
            if(item.sprite != null){
                g2d.drawImage(item.sprite.getSprite(item.state, item.frameIndex), hotbarX() + (40 * i) + 4, 1004, null);
            }
        }
    }

    private int hotbarX(){
        return (int) ((Main.getScreenSize().getWidth() - (40 * numSlots)) / 2.);
    }

}
