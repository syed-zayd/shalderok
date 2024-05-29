import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

class World extends JPanel {
    static final int TILE_SIZE = 32;
    static Camera camera;
    static Player p;
    Floor f;
    
    static Point mouse = new Point (0, 0);
    
    public World() {
        p = new Player(50, 100);
        camera = new Camera();
        camera.centerObj = p;

        try {
            f = new Floor();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                p.useWeapon();
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub
            }

            

        });


        addKeyListener(new KeyHandler());
        addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {}

            @Override
            public void mouseMoved(MouseEvent e) {
                mouse = e.getPoint();
            }
            
        });

        addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    camera.zoomIn();
                } else {
                    camera.zoomOut();
                }
            }
        });
        setFocusable(true);
    }

    private static double collisionX(GameObject a, GameObject b) {
        double pushLeft = b.x-(a.x+a.w);
        if (pushLeft>=0) {
            return 0;
        }
        double pushRight = (b.x+b.w) - a.x;
        if (pushRight<=0) {
            return 0;
        }

        if (Math.abs(pushLeft) <= Math.abs(pushRight)) {
            return pushLeft;
        } else {
            return pushRight;
        }
    }
    
    private static double collisionY(GameObject a, GameObject b) {
        double pushUp = b.y-(a.y+a.h);
        if (pushUp>=0) {
            return 0;
        }
        double pushDown = (b.y+b.h) - a.y;
        if (pushDown<=0) {
            return 0;
        }

        if (Math.abs(pushUp) <= Math.abs(pushDown)) {
            return pushUp;
        } else {
            return pushDown;
        }
    }

    private void handleCollisions() {
        for (Room r: f.getRooms()) {
            Iterator<GameObject> it = r.getObjIterator();
            while (it.hasNext()) {
                GameObject obj = it.next();
                if (! (obj instanceof Wall)) {
                    continue;
                }

                double cx = collisionX(p, obj);
                double cy = collisionY(p, obj);
                if (cx != 0 && cy != 0) {
                    if (Math.abs(cx) < Math.abs(cy)) {
                        p.x += cx;
                        p.vx = 0;
                    } else {
                        p.y += cy;
                        p.vy = 0;
                    }
                }
            }
        }
    }

    public void update() {
        // update every object
        p.update();

        for (Room r: f.getRooms()) {
            Iterator<GameObject> it = r.getObjIterator();
            while (it.hasNext()) {
                it.next().update();
            }
        }

        // manage collisions
        handleCollisions();

        // update camera
        camera.center();
    }
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        try {
            g2d.setFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/MysteryQuest-Regular.ttf")).deriveFont(18f));
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        g2d.translate(-camera.x, -camera.y);
        g2d.transform(new AffineTransform(camera.zoom, 0, 0, camera.zoom, camera.getCenterX()*(1-camera.zoom), camera.getCenterY()*(1-camera.zoom)));

        p.paint(g2d);
        
        for (Room r: f.getRooms()) {
            Iterator<GameObject> it = r.getObjIterator();
            while (it.hasNext()) {
                it.next().paint(g2d);
            }
        }
    }
    
}