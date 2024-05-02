// singleton class for the player; get the player using Player.getInstance()
public class Player implements Renderable {

    private int xp; // experience gained
    private int maxhp; // maximum health points
    private int hp; // health points
    private int eva; // evasiveness / dodge %
    private int atk; // attack / damage

    private Armor armor; // the equipped armor

    private static final Player instance = new Player();

    private Player() {
        this.xp = 0;
        this.maxhp = 5;
        this.hp = 5;
        this.eva = 0;
        this.atk = 1;

        this.armor = null;
    }

    public static Player getInstance() {
        if (instance == null) {
            return new Player();
        }
        return instance;
    }

    public int getxp() {
        return xp;
    }
    public int getmaxhp() {
        return maxhp;
    }
    public int gethp() {
        return hp;
    }
    public int geteva() {
        return eva;
    }
    public int getatk() {
        return atk;
    }

    @Override
    public void paint() {
        // paint something
    }

    public void equip(Armor armor) {
        this.armor = armor;
    }

    public void takeDmg(int dmg) {
        // first let the armor take some damage

        hp -= Math.max(dmg, 0);
        if (hp<=0) {
            hp=0;
            die();
        }
    }

    public void gainxp(int xp) {
        this.xp+=xp;

        // check level up
    }

    // restores hp and returns how much hp was restored
    public int restore(int a) {
        int prevhp = hp;
        hp = Math.min(hp+a, maxhp);
        return hp-prevhp;
    }

    public void die() {
        // die or something
    }
}
