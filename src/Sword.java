public class Sword extends Weapon {

        public Sword(Entity owner, double x, double y) {
            super(owner, x, y, SpriteLoader.getSprite("sword"));
        }
    
        public void shoot() {
            if(canShoot()){
                queuedProjectiles.add(new MeleeProjectile(x, y, angle, 10, 8));
                cooldownTimer = 25;
            }
        }
        
        @Override
        public void interact() {
    
        }
    
}
