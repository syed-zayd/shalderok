public class Sword extends Weapon {

        public Sword(Entity owner, double x, double y) {
            super(owner, x, y, (int) (Math.random() * 8) + 1, SpriteLoader.getSprite("sword"));
        }
    
        public void shoot() {
            if(canShoot()){
                queuedProjectiles.add(new MeleeProjectile(x, y, angle, calculateDamage(), 8));
                cooldownTimer = 200;
            }
        }
        
        @Override
        public void interact() {
    
        }
    
}
