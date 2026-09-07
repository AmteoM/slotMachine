/**
 * A wheel that can be manipulated and that draws itself on a canvas.
 * 
 * @author Juan Cortazar y Mateo Martinez 
 * @version 23/08/2026
 */
import java.util.List;
import java.util.ArrayList;
public class Wheel {
    private List<Circle> symbols;
    private boolean isVisible;
    private int visibleIn;
    private int xPosition; 
    private int yPosition;
    private Circle border;
    private int borderX;
    private int borderY;
    private boolean isLocked;
    private static final int BORDERMARGIN = 6;
    private static final int BASEX = 20;
    private static final int BASEY = 50;
    private static final int SEPARATIONX = 80;
    private static final int SEPARATIONY = 40;
    private static final int POSCIRCXCANV = 20;
    private static final int POSCIRCYCANV = 15;

    /**
     * Constructor for wheel 
     * @param xpos horizontal position of the wheel 
     */
    public Wheel(int xpos) {
        symbols = new ArrayList<Circle>();
        isVisible = false;
        isLocked = false;
        visibleIn = -1;
        yPosition = BASEY;
        xPosition = BASEX + (xpos - 1) * SEPARATIONX;

        border = new Circle();
        border.changeColor("black");
        border.changeSize(30 + BORDERMARGIN);
        borderX = POSCIRCXCANV;
        borderY = POSCIRCYCANV;
    }

    /**
     * Add a symbol in a wheel 
     * @param color is the color of the symbol that will be added
     */
    public void addSymbol(int pos, String color){
        if (pos < 1) {
            pos = 1; 
        }
        if (pos > symbols.size() + 1){
            pos = symbols.size() + 1;
        }
        int index = pos - 1;
        for (int i = index; i < symbols.size(); i++){
            symbols.get(i).moveVertical(SEPARATIONY);
        }
        Circle circle = new Circle();
        circle.changeColor(color);
        int cxPos = xPosition - POSCIRCXCANV;
        int cyPos = yPosition - POSCIRCYCANV + index * SEPARATIONY;
        circle.moveVertical(cyPos);
        circle.moveHorizontal(cxPos);
        symbols.add(index, circle);
        if (isVisible){
            circle.makeVisible();
        }
        visibleIn = -1;
        if (isVisible){
            updateBorder(currentIndex());
        }
    }

    /**
     * Delete a symbol in a wheel
     * @param symbol is the color of the symbol that will be deleted
     */
    public void delSymbol(String symbol){
        int foundIn = -1;
        for (int i = 0; i < symbols.size(); i++){
            if (symbols.get(i).getColor().equals(symbol)){
                foundIn = i;
                break;
            }
        }
        if (foundIn != -1){
            Circle delete = symbols.get(foundIn);
            if (isVisible){
                delete.makeInvisible();
            }
            symbols.remove(foundIn);
            for (int i = foundIn; i < symbols.size(); i++){
                symbols.get(i).moveVertical(-SEPARATIONY);
            }
            
            visibleIn = -1;
            if (isVisible && symbols.size() > 0){
                updateBorder(currentIndex());
            }
        }
    }

    /**
     * Spin and get a random symbol of the wheel
     */
    public void spin(){
        if (isLocked){
            return;
        }
        if (symbols.size() > 0){
            int randomIn = (int)(Math.random() * symbols.size());
            visibleIn = randomIn;
            updateBorder(visibleIn);
        }
    }

    /**
     * Make all symbols of this wheel visible on the canvas,
     * and show the border around the current winning symbol.
     */
    public void makeVisible(){
        isVisible = true;
        for (int i = 0; i < symbols.size(); i++){
            symbols.get(i).makeVisible();
        }
        if (symbols.size() > 0) {
            updateBorder(currentIndex());
        }
    }

    /**
     * Make all symbols of this wheel invisible on the canvas,
     * including the border.
     */
    public void makeInvisible(){
        isVisible = false;
        for (int i = 0; i < symbols.size(); i++){
            symbols.get(i).makeInvisible();
        }
        border.makeInvisible();
    }

    /**
     * Return the colors of the symbols mounted on this wheel, in order.
     */
    public List<String> getSymbols(){
        List<String> colors = new ArrayList<String>();
        for (int i = 0; i < symbols.size(); i++){
            colors.add(symbols.get(i).getColor());
        }
        return colors;
    }

    /**
     * Return the color of the symbol currently marked as visible
     * (the result of the last spin, or the first symbol by default
     * if the wheel has never spun). Returns an empty string if the
     * wheel has no symbols mounted.
     */
    public String getVisibleSymbol(){
        if (symbols.size() == 0) {
            return "";
        }
        return symbols.get(currentIndex()).getColor();
    }

    /**
     * Return the index that should be treated as "the current winning
     * symbol" - index 0 by default if the wheel has never spun.
     */
    private int currentIndex(){
        if (visibleIn == -1) {
            return 0;
        }
        return visibleIn;
    }

    /**
     * Move the border circle so it sits centered behind the symbol
     * at index i, and bring that symbol back to the front so the
     * border does not cover its color.
     * @param i the index of the symbol that should be highlighted
     */
    private void updateBorder(int i){
        int targetX = xPosition - BORDERMARGIN / 2;
        int targetY = yPosition + i * SEPARATIONY - BORDERMARGIN / 2;

        int deltaX = targetX - borderX;
        int deltaY = targetY - borderY;

        border.moveHorizontal(deltaX);
        border.moveVertical(deltaY);

        borderX = targetX;
        borderY = targetY;

        if (isVisible) {
            border.makeVisible();
            symbols.get(i).moveHorizontal(0);
        }
    }
    
    public void placeSymbol(String symbol){
        if (isLocked){
            return;
        }
        int foundIn = -1;
        for (int i = 0; i < symbols.size(); i++){
            if (symbols.get(i).getColor().equals(symbol)){
                foundIn = i;
                break;
            }
        }
        if (foundIn != -1){
            visibleIn = foundIn;
            if(isVisible){
                updateBorder(visibleIn);
            }
        }
    }
    
    /**
     * Lock this wheel so that it cannot be spun or placed.
     */
    public void lock(){
        isLocked = true;
    }

    /**
     * Unlock this wheel so that it can be spun again.
     */
    public void unlock(){
        isLocked = false;
    }

    /**
     * Change the color of the border that marks the current symbol.
     * @param color the CSS color name for the border
     */
    public void setBorderColor(String color){
        border.changeColor(color);
        if (isVisible && symbols.size() > 0) {
            updateBorder(currentIndex());
        }
    }
    
    /**
     * Rotate this wheel a given number of steps. Positive steps move
     * foward, negative steps move backward
     * @param steps the number of positions to rotate
     */
    public void spin(int steps){
        if (isLocked || symbols.size() == 0 || steps == 0){
            return;
        }
        visibleIn = currentIndex();
        int bucle = Math.abs(steps);
        for (int i = 0; i < bucle; i++){
            if (steps > 0){
                visibleIn += 1;
                if (visibleIn >= symbols.size()){
                    visibleIn = 0;
                }
            } else {
                visibleIn -= 1;
                if (visibleIn < 0){
                    visibleIn = symbols.size() - 1;
                }
            }
            updateBorder(visibleIn);
            if (isVisible){
                Canvas.getCanvas().wait(200);
            }
        }
    }
    
    /**
     * Move this wheel to a new horizontal position on the canvas
     * @param xpos the new position of the wheel, starting at 1
     */
    public void relocate(int xpos){
        int nuevoX = BASEX + (xpos - 1) * SEPARATIONX;
        int delta = nuevoX - xPosition;
        for(int i = 0; i < symbols.size(); i++){
            symbols.get(i).moveHorizontal(delta);
        }
        border.moveHorizontal(delta);
        xPosition = nuevoX;
        borderX = borderX + delta;
        
    }
    public boolean isLocked(){
        return isLocked;
    }
}