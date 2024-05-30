public class Coordinate {
    
    private double x;
    private double y;

    public Coordinate(double x, double y){
        this.x = x;
        this.y = y;
    }

    public void reflect(){
        y = -y;
    }

    public void rotate(double angle){
        double transformX = x * Math.cos(angle) - y * Math.sin(angle);
        double transformY = x * Math.sin(angle) + y * Math.cos(angle);
        x = transformX;
        y = transformY;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public void setX(double x){
        this.x = x;
    }

    public void setY(double y){
        this.y = y;
    }

}
