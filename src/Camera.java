class Camera {
    double x, y;
    double zoom;
    Camera instance;
    
    public Camera() {
        zoom=1;
    }

    public void center(GameObject obj) {
        x = obj.drawCenterX() - Main.getScreenSize().getWidth()/2;
        y = obj.drawCenterY() - Main.getScreenSize().getHeight()/2;
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
}