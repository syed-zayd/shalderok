public class BasicBow extends Weapon {

    public BasicBow() {
        super("Basic Bow", 1, 0, 0, 0, "sprites/basic_bow.png");
    }

    @Override
    public int getAtk() {
        return passiveAtk;
    }

    @Override
    public int getDodge() {
        return passiveDodge;
    }

    @Override
    public int getSpd() {
        return passiveSpd;
    }

    @Override
    public void attack() {
        
    }

}
