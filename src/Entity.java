public abstract class Entity extends GameObject {
    
    public double vx, vy;
    public double knockbackX, knockbackY;
    public int hp;
    public int maxHp;
    public Weapon weapon;

    public Entity(double x, double y, int hp, Sprite s){
        super(x, y, s.getWidth(), s.getHeight(), s);
        this.vx = 0;
        this.vy = 0;
        this.hp = hp;
        this.maxHp = hp;
    }

    public void takeDamage(int damage){
        this.hp -= damage;
    }

    public void equip(Weapon weapon){
        this.weapon = weapon;
    }

    public void useWeapon(){
        if(weapon != null){
            weapon.shoot();
        }
    }

    @Override
    public boolean isSolid(){
        return true;
    }

}
