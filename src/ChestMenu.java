import java.awt.Graphics2D;

public class ChestMenu {
    
    private final int CELL_SIZE = 36;
    private final int OFFSET = 4;
    Chest chest;
    int numRows;
    int numCols;

    public ChestMenu(Chest chest){
        this.chest = chest;
        numRows = 2;
        numCols = 5;
    }

    public void paint(Graphics2D g2d){
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numCols; c++){
                Item item = chest.getItem(r * numRows + c);
                g2d.drawRect(menuX() + c * (CELL_SIZE + OFFSET), menuY() + r * (CELL_SIZE + OFFSET), CELL_SIZE + OFFSET, CELL_SIZE + OFFSET);
                g2d.drawImage(item.sprite.getSprite(item.state, item.frameIndex), menuX() + c * (CELL_SIZE + OFFSET) + OFFSET, menuY() + r * (CELL_SIZE + OFFSET) + OFFSET, null);
            }
        }
    }

    public int menuX(){
        return (int) ((Main.getScreenSize().getWidth() - (numCols * (CELL_SIZE + OFFSET))) / 2.);
    }

    public int menuY(){
        return (int) ((Main.getScreenSize().getHeight() - (numRows * (CELL_SIZE + OFFSET))) / 2.);
    }

}
