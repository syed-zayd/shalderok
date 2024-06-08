public abstract class Item extends GameObject implements Cloneable {

    int numUses;
    static Weapon[] weapons = {new Bow(null, 0, 0), new Wand(null, 0, 0), new Sword(null, 0, 0)};
    static Item[] potions = {new DamagePotion(0, 0, 0), new HealingPotion(0, 0, 0), new SpeedPotion(0, 0, 0)};

    public Item(double x, double y, int w, int h, Sprite s) {
        super(x, y, w, h, s);
        this.numUses = 1;
    }
    
    public abstract void use();

    public static Weapon getRandomWeapon(){
        try {
            Weapon randomWeapon = weapons[(int) (Math.random() * weapons.length)];
            return (Weapon) randomWeapon.clone();
        } catch(CloneNotSupportedException e){
            
        }
        return null;
    }

    public static Item getRandomPotion(){
        try {
            Item randomPotion = potions[(int) (Math.random() * potions.length)];
            return (Item) randomPotion.clone();
        } catch(CloneNotSupportedException e){
            
        }
        return null;
    }

}
