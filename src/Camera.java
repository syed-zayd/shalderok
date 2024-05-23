class Camera {
    double x, y;
    double zoom;
    GameObject centerObj;
    
    public Camera() {
        zoom=1;
    }

    public void center() {
        x = centerObj.drawCenterX() - Main.getScreenSize().getWidth()/2;
        y = centerObj.drawCenterY() - Main.getScreenSize().getHeight()/2;
    }

    public void zoomOut() {
        zoom = Math.max(0.5, zoom-0.05);
    }
    public void zoomIn() {
        zoom = Math.min(1.5, zoom+0.05);
    }
    public void resetZoom() {
        zoom = 1;
    }

    public double getCenterX() {
        return x+Main.getScreenSize().getWidth()/2.;
    }
    public double getCenterY() {
        return y+Main.getScreenSize().getHeight()/2.;
    }
}