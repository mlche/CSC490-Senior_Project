import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


/**
 * Class to handle insertion of blank tablature into the textEditorGUI.
 */
public class BlankTabUtility {
    //Text area to write blank tab to
    private JTextArea textArea;

    //Tab file to insert
    private Path tabPath;
    
    //String array for each line to be printed
    private String[] instrStrings;

    /**
     * Constructor.
     */
    public BlankTabUtility(JTextArea area){
        textArea = area;
    }

    /**
     * Get corresponding tab file and write it to the text area.
     */
    public void insertTab(String tng, int strings, int sections){
        //Get the tab file for associated specifications
        tabPath = getTabPath(tng, strings);
        
        instrStrings = new String[strings];

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(tabPath, charset)) {
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null){
                instrStrings[i] = line;
                i++;
            }
            for(i = 0; i < sections; i++){
                int s = 0;
                while (s<instrStrings.length) {
                    textArea.append(instrStrings[s] + "\n");
                    s++;
                }
                textArea.append("\n");
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    /**
     * Get the appropriate tab file given specifications.
     * Tuning, number of strings and sections.
     */
    private Path getTabPath(String tuning, int numStrings){
        Path path;

        String pathStr = tuning + "_" + numStrings + ".txt";

        path = Paths.get("src", "resources", "blank_tabs", pathStr);
                
        return path;
        
    }

}
