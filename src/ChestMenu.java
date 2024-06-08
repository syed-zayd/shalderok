import java.awt.Color;
import java.awt.Graphics2D;

public class ChestMenu {
    
    private final int CELL_SIZE = 36;
    private final int OFFSET = 4;
    Chest chest;
    int numRows;
    int numCols;
    Item selectedItem = null;

    public ChestMenu(Chest chest){
        this.chest = chest;
        numRows = 2;
        numCols = 5;
    }

    public void paint(Graphics2D g2d){
        g2d.setColor(Color.GRAY);
        g2d.fillRoundRect(menuX(), menuY(), 400, 200, 20, 20);
        g2d.setColor(Color.BLACK);
        int centerX = menuX() + 199;
        int centerY = menuY() + 99;

        int startX = centerX - ((numCols * CELL_SIZE) / 2);
        int startY = centerY - ((numRows * CELL_SIZE) / 2);
        
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numCols; c++){
                Item item = chest.getItem(r * numCols + c);
                g2d.drawRect(startX + c * (CELL_SIZE + OFFSET), startY + r * (CELL_SIZE + OFFSET), CELL_SIZE + OFFSET, CELL_SIZE + OFFSET);
                if(item == selectedItem){
                    selectedItem.paint(g2d);
                }
                else{
                    g2d.drawImage(item.sprite.getSprite(item.state, item.frameIndex), startX + c * (CELL_SIZE + OFFSET) + OFFSET, startY + r * (CELL_SIZE + OFFSET) + OFFSET, null);
                }
            }
        }
    }

    public void selectItem(int x, int y){
        int centerX = menuX() + 199;
        int centerY = menuY() + 99;

        int startX = centerX - ((numCols * CELL_SIZE) / 2);
        int startY = centerY - ((numRows * CELL_SIZE) / 2);
        
        for(int r = 0; r < numRows; r++){
            for(int c = 0; c < numCols; c++){
                if(x >= startX + c * (CELL_SIZE + OFFSET) && x <= startX + c * (CELL_SIZE + OFFSET) + CELL_SIZE && y >= startY + r * (CELL_SIZE + OFFSET) && y <= startY + r * (CELL_SIZE + OFFSET) + CELL_SIZE){
                    selectedItem = chest.getItem(r * numCols + c);
                    selectedItem.x = x;
                    selectedItem.y = y;
                    return;
                }
            }
        }
    }

    public void moveItem(int x, int y){
        selectedItem.x = x;
        selectedItem.y = y;
    }

    public boolean itemSelected(){
        return selectedItem != null;
    }

    public void swapItem(Item item){
        if(item != null){
            for(int i = 0; i < numCols * numRows; i++){
                if(chest.getItem(i) == selectedItem){
                    chest.setItem(i, item);
                    return;
                }
            }
        }
        selectedItem = null;
    }

    public int menuX(){
        return (int) ((Main.getScreenSize().getWidth() - (numCols * (CELL_SIZE + OFFSET))) / 2.);
    }

    public int menuY(){
        return (int) ((Main.getScreenSize().getHeight() - (numRows * (CELL_SIZE + OFFSET))) / 2.);
    }

}
