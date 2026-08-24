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
    public void addSymbol(String color){
        Circle circle = new Circle();
        circle.changeColor(color);
        int cxPos = xPosition - POSCIRCXCANV;
        int cyPos = yPosition - POSCIRCYCANV + symbols.size() * SEPARATIONY;
        circle.moveVertical(cyPos);
        circle.moveHorizontal(cxPos);
        symbols.add(circle);
        if (isVisible) {
            circle.makeVisible();
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
        }
    }

    /**
     * Spin and get a random symbol of the wheel
     */
    public void spin(){
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
}