public class WebShooter extends Weapon {
    
    public WebShooter(double x, double y){
        super(x, y, SpriteLoader.getSprite("web"));
    }

    public void shoot(){
        queuedProjectiles.add(new Web(x, y, 5, angle));
    }

    @Override
    public void interact() {
        
    }

}
