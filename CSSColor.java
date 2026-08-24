
import java.awt.Color;

/**
 * Translates CSS standard color names into Color objects.
 * @author (sus nombres)
 * @version (fecha)
 */
public class CSSColor {
    private static String[] names = {
        "black", "white", "red", "blue", "yellow",
        "cyan", "magenta", "gray", "maroon", "green", "purple",
        "orange", "pink", "gold", "brown",
        "salmon", "turquoise", "violet"
    };

    private static Color[] colors = {
        new Color(0, 0, 0),       new Color(255, 255, 255),
        new Color(255, 0, 0),     new Color(0, 0, 255),
        new Color(255, 255, 0),   new Color(0, 255, 255),
        new Color(255, 0, 255),   new Color(128, 128, 128),
        new Color(128, 0, 0),     new Color(0, 128, 0),
        new Color(128, 0, 128),   new Color(255, 165, 0),
        new Color(255, 192, 203), new Color(255, 215, 0),
        new Color(165, 42, 42),   new Color(250, 128, 114),
        new Color(64, 224, 208),  new Color(238, 130, 238)
    };

    /**
     * Return the Color matching a CSS color name.
     * @param name the CSS color name
     * @return the matching Color, or null if the name is not supported
     */
    public static Color get(String name) {
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(name)) {
                return colors[i];
            }
        }
        return null;
    }

    /**
     * Check whether a name matches a supported CSS color.
     * @param name the CSS color name
     * @return true if the color is supported
     */
    public static boolean isValid(String name) {
        return get(name) != null;
    }

    /**
     * Return every supported color name.
     * @return the supported CSS color names
     */
    public static String[] availableColors() {
        return names;
    }
}