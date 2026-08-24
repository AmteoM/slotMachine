
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
    }

    /**
     * Add a symbol of the given color to the wheel at the given position.
     * @param pos the position of the wheel that will receive the symbol
     * @param color the CSS color name of the symbol that will be added
     */
    public void addSymbol(int pos, String color){
        if(pos < 1){
            pos = 1; 
        }
        if(pos > wheels.size()){
            pos = wheels.size();
        }
        wheels.get(pos-1).addSymbol(color);
    }

    /**
     * Delete the symbol of the given color from every wheel of the machine.
     * @param symbol the CSS color name of the symbol that will be deleted
     */
    public void delSymbol(String symbol){
        for (Wheel w : wheels) {
            w.delSymbol(symbol);
        }        
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
    }

    /**
     * Spin every wheel of the machine.
     */
    public void spin(){
        for (Wheel r : wheels){
            r.spin();
        }
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
}