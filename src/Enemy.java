import java.awt.Point;
import java.util.ArrayList;

abstract class Enemy extends Entity {
    
    boolean activated;

    ArrayList<GameObject> pathfindingPath; // pathfinding
    Point pathfindingCurrentIndex;

    public Enemy(double x, double y, int hp, Sprite s) {
        super(x, y, hp, s);
        this.hp = hp;
        activated = false;
        pathfindingPath = new ArrayList<GameObject>();
        pathfindingCurrentIndex = new Point(-1,-1);
    }

    public void update(){
        if(hp <= 0){
            activated = false;
        }
    }

}
