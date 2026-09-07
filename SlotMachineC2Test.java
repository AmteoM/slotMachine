
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class slotMachineC2Test.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */

/**
 * Default constructor for test class slotMachineC2Test
 */
public class SlotMachineC2Test{
    private SlotMachine slotMachine;

    @BeforeEach
    public void setUp(){
        slotMachine = new SlotMachine();
    }

    @Test
    public void shouldNotChangeConfigurationWhenLocked(){
        slotMachine.addWheel(1);
        slotMachine.addSymbol(1, "red");
        slotMachine.addSymbol(1, "blue");
        slotMachine.placeSymbol(1, "blue");
        slotMachine.lock(1);
        slotMachine.spin(1);
        assertEquals("blue", slotMachine.configuration()[0]);
    }
    @Test
    public void shouldStayLockedOnLastWheel(){
        slotMachine.addWheel(1);
        slotMachine.addWheel(2);
        slotMachine.addSymbol(2, "red");
        slotMachine.addSymbol(2, "blue");
        slotMachine.placeSymbol(2, "blue");

        slotMachine.lock(999);
        slotMachine.spin(2);

        assertEquals("blue", slotMachine.configuration()[1]);
    }
}