import java.util.ArrayList;

public class Chest extends GameObject {
    
    private ArrayList<Item> items;

    public Chest(double x, double y){
        super(x, y, 0, 0, null);
        Sprite s = SpriteLoader.getSprite("chest");
        this.w = s.getWidth();
        this.h = s.getHeight();
        this.sprite = s;
        items = new ArrayList<Item>();
        generateRandomItems((int) (Math.random() * 5), (int) (Math.random() * 3));
    }

    public void generateRandomItems(int numItems, int rarityLevel){

        for(int i = 0; i < numItems; i++){
            switch(rarityLevel){
                case 0:
                    if(Math.random() < 0.01){
                        items.add(Item.getRandomWeapon());
                    }
                    else{
                        items.add(Item.getRandomPotion());
                    }
                    break;
                case 1:
                    if(Math.random() < 0.1){
                        items.add(Item.getRandomWeapon());
                    }
                    else{
                        items.add(Item.getRandomPotion());
                    }
                    break;
                case 2:
                    if(Math.random() < 0.5){
                        items.add(Item.getRandomWeapon());
                    }
                    else{
                        items.add(Item.getRandomPotion());
                    }
                    break;
            }
        }

    }

    public Item getItem(int index){
        if(index >= items.size()){
            return new Fists(null, 0, 0);
        }
        return items.get(index);
    }

    @Override
    public void update() {
        
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public void interact() {
        World.openChest(this);
    }

}
