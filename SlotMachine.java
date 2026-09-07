
/**
 * A slot machine simulator that manages a set of wheels with colored symbols.
 * 
 * @author Juan Cortazar y Mateo Martinez
 * @version 23/08/2026
 */
import java.util.*;

public class SlotMachine{
    private List<Wheel> wheels;
    private boolean ok;

    /**
     * Create a slot machine with no wheels.
     */
    public SlotMachine(){
        wheels = new ArrayList<Wheel>();
        ok = true;
    }

    /**
     * Add a new wheel at the given position. Positions start at 1; values out of
     * range are adjusted to the nearest valid position.
     * @param pos the position where the new wheel will be inserted
     */
    public void addWheel( int pos){
        if(pos < 1){
            pos = 1; 
        }
        if(pos > wheels.size()+1){
            pos = wheels.size() + 1;
        }
        wheels.add(pos -1, new Wheel(pos));
        refreshJackpot();
    }

    /**
     * Delete the wheel located at the given position. Positions start at 1; values
     * out of range are adjusted to the nearest valid position.
     * @param pos the position of the wheel that will be deleted
     */
    public void delWheel(int pos){
        if(pos < 1){
            pos = 1; 
        }
        if(pos > wheels.size()){
            pos = wheels.size();
        }
        wheels.remove(pos-1);
        refreshJackpot();
    }

    /**
     * Add a symbol of the given color to the wheel at the given position.
     * @param pos the position of the wheel that will receive the symbol
     * @param color the CSS color name of the symbol that will be added
     */
    public void addSymbol(int pos, String color){
        for (Wheel w : wheels) {
        w.addSymbol(pos, color);
        }
        refreshJackpot();
    }

    /**
     * Delete the symbol of the given color from every wheel of the machine.
     * @param symbol the CSS color name of the symbol that will be deleted
     */
    public void delSymbol(String symbol){
        for (Wheel w : wheels) {
            w.delSymbol(symbol);
        }
        refreshJackpot();
    }

    /**
     * Spin a single wheel of the machine. Positions start at 1; values out of
     * range are adjusted to the nearest valid position.
     * @param wheel the position of the wheel that will be spun
     */
    public void spin(int wheel){
        if (wheel < 1) {
            wheel = 1;
        }
        if (wheel > wheels.size()) {
            wheel = wheels.size();
        }
        wheels.get(wheel - 1).spin();
        refreshJackpot();
    }

    /**
     * Spin every wheel of the machine.
     */
    public void spin(){
        for (Wheel r : wheels){
            r.spin();
        }
        refreshJackpot();
    }

    /**
     * Return the colors of all the symbols mounted on the machine, wheel by wheel,
     * each wheel starting at its first symbol.
     * @return the colors of every symbol mounted on the machine
     */
    public String[] symbols(){
        List<String> colores = new ArrayList<String>();
        for ( Wheel o : wheels){
            colores.addAll(o.getSymbols());
        }
        String[] resultado = colores.toArray(new String[0]);
        return resultado;
    }

    /**
     * Make the machine and all of its symbols visible on the canvas.
     */
    public void makeVisible(){
        for (Wheel w : wheels){
            w.makeVisible();
        }
    }

    /**
     * Make the machine and all of its symbols invisible on the canvas.
     */
    public void makeInvisible(){
        for (Wheel w : wheels){
            w.makeInvisible();
        }
    }

    /**
     * Return how many different colors are mounted on the machine.
     * @return the number of distinct symbol colors
     */
    public int distinctSymbols(){
        Set<String> distintos = new HashSet<String>();
        distintos.addAll(Arrays.asList(symbols()));
        return distintos.size();
    }

    /**
     * Return the colors of the visible symbols of every wheel, ordered from left
     * to right.
     * @return the color of the visible symbol of each wheel
     */public String[] configuration(){
        List<String> colores = new ArrayList<String>();
        for (Wheel m : wheels) {
            colores.add(m.getVisibleSymbol());
        }
        String[] resultado = colores.toArray(new String[0]);
        return resultado;
    }

    /**
     * Check whether the current configuration is a winning one, that is, whether
     * every visible symbol has the same color.
     * @return true if all the visible symbols share the same color
     */
    public boolean isJackpot(){
        Set<String> distintos = new HashSet<String>();     
        distintos.addAll(Arrays.asList(configuration()));
        if (distintos.contains("")){
            return false;
        }
        return distintos.size() == 1;
    }

    /**
     * Terminate the simulator, hiding the machine from the canvas.
     */
    public void exit(){
        makeInvisible();
    }

    /**
     * Indicate whether the last operation was completed successfully.
     * @return true if the last operation succeeded
     */
    public boolean ok(){
        return ok;
    }
    
    public void placeSymbol(int wheel, String symbol){
        if (wheel < 1) {
            wheel = 1;
        }
        if (wheel > wheels.size()) {
            wheel = wheels.size();
        }
        wheels.get(wheel - 1).placeSymbol(symbol);
        refreshJackpot();
    }
    
    /**
     * Lock a wheel so that it cannot be spun.
     * @param wheel the position of the wheel that will be locked
     */
    public void lock(int wheel){
        if (wheel < 1) {
            wheel = 1;
        }
        if (wheel > wheels.size()) {
            wheel = wheels.size();
        }
        wheels.get(wheel - 1).lock();
    }

    /**
     * Unlock a wheel so that it can be spun again.
     * @param wheel the position of the wheel that will be unlocked
     */
    public void unlock(int wheel){
        if (wheel < 1) {
            wheel = 1;
        }
        if (wheel > wheels.size()) {
            wheel = wheels.size();
        }
        wheels.get(wheel - 1).unlock();
    }
    
    /**
     * Update the border color of every wheel so the machine looks
     * different when the current configuration is a winning one
     */
    private void refreshJackpot(){
        String color;
        if (isJackpot()){
            color = "gold";
        } else {
            color = "black";
        }
        for (Wheel w : wheels){
            w.setBorderColor(color);
        }
    }
    
    /**
     * Leave the machien in the given configuration 
     * @param setSymbols the color each wheel should show, from left to right
     */
    public void spin(String[] setSymbols){
        for (int i = 0; i < setSymbols.length && i < wheels.size(); i++){
            placeSymbol(i+1, setSymbols[i]);   
        }
    }

    /**
     * Rotate a single wheel a given number of steps
     * @param wheel the position of the wheel that will be rotated
     * @param steps the number of positions to rotate
     */
    public void spin(int wheel, int steps){
        if (wheel > wheels.size()){
            wheel = wheels.size();
        } 
        if (wheel < 1){
            wheel = 1;
        }
        wheels.get(wheel-1).spin(steps);
        refreshJackpot();
    }
    
    /**
     * Swap the positions of two wheels of the machine, moving them on the
     * canvas as well. Positions start at 1; values out of range are adjusted
     * to the nearest valid position.
     * @param wheel1 the position of the first wheel that will be swapped
     * @param wheel2 the position of the second wheel that will be swapped
     */
    public void swap(int wheel1, int wheel2){
        if (wheel1 < 1){
            wheel1 = 1;
        } 
        if (wheel1 > wheels.size()){
            wheel1 = wheels.size();
        }
        if (wheel2 < 1){
            wheel2 = 1;
        } 
        if (wheel2 > wheels.size()){
            wheel2 = wheels.size();
        }
        
        if (wheels.get(wheel1 - 1).isLocked() ||
        wheels.get(wheel2 - 1).isLocked()){
        ok = false;
        return;
        }
        Wheel temp = wheels.get(wheel1 - 1);
        wheels.set(wheel1 - 1, wheels.get(wheel2 - 1));
        wheels.set(wheel2 - 1, temp);
        
        wheels.get(wheel1 - 1).relocate(wheel1);
        wheels.get(wheel2 - 1).relocate(wheel2);
        refreshJackpot();
    }
}