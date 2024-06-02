public class Sword extends Weapon {

        public Sword(double x, double y) {
            super(x, y, SpriteLoader.getSprite("sword"));
        }
    
        public void shoot() {
            if(canShoot()){
                queuedProjectiles.add(new MeleeProjectile(x, y, angle));
                cooldownTimer = 25;
            }
        }
        
        @Override
        public void interact() {
    
        }
    
}
