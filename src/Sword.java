public class Sword extends Weapon {

        public Sword(double x, double y) {
            super(x, y, SpriteLoader.getSprite("sword"));
        }
    
        @Override
        void shoot() {
            queuedProjectiles.add(new MeleeProjectile(x, y, angle));
        }
        
        @Override
        public void interact() {
    
        }
    
}
