/**
 * Demo class to create a textEditorGUI object.
 */
public class TabRunner {

    public static void main(String[] args) {
        //Type of tab sheet to open up
        final String UNLIMITED = "unlimited";
        final String LIMITED = "limited";

        //Choost a type from above to pass to GUI
        //new textEditorGUI(UNLIMITED);

        new textEditorGUI(LIMITED);
    }
}
