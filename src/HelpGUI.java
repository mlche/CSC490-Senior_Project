import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.awt.Font.DIALOG;

/**
 * The help screen GUI.
 */
public class HelpGUI {

    //Frame to hold panels
    private JFrame frame;

    //Title for frame
    private String TITLE = "Help";

    //JPanels
    private JPanel mainPanel;

    //Text pane to display help information
    private JTextPane textPane;
    StyledDocument doc;

    //Strings for files
    private final String TAB_SYMBOLS = "tab symbols";

    //Menu buttons
    JButton howToButton;
    JButton tabSymbolsButton;
    JButton tuningsButton;

    //Menu button fonts
    private final Font PRESSED_FONT = new Font(Font.DIALOG,Font.BOLD, 16);
    private final Font UNPRESSED_FONT = new Font(Font.DIALOG, Font.PLAIN, 16);

    /**
     * Constructor.
     * Set up the JFrame.
     */
    public HelpGUI(){
        frame = new JFrame();

        //Get frame ready
        frame.setTitle(TITLE);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create the menu bar
        JMenuBar menuBar = createMenuBar();

        //Build the panel and add it to the frame
        buildPanel();

        Container contentPane = frame.getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.add(menuBar, BorderLayout.LINE_START);
        contentPane.add(mainPanel);

        //Display the window
        frame.pack();
        //frame.setSize(new Dimension(800, 500));
        frame.setLocationRelativeTo(null); // Center frame on screen
        frame.setVisible(true);
    }

    public JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        menuBar.setLayout(new BoxLayout(menuBar, BoxLayout.PAGE_AXIS));

        //Create the menu buttons
        howToButton = createMenuButton("How to Read Tabs");
        tabSymbolsButton = createMenuButton("Tab Symbols");
        tuningsButton = createMenuButton("Guitar Tunings");

        //Add the appropriate action listener to each button
        howToButton.addActionListener(new HowToButtonListener());
        tabSymbolsButton.addActionListener(new TabSymbolButtonListener());
        tuningsButton.addActionListener(new TuningsButtonListener());

        //Add the menu buttons to the menu with spacing in between
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));
        menuBar.add(howToButton);
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));
        menuBar.add(tabSymbolsButton);
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));
        menuBar.add(tuningsButton);
        menuBar.add(Box.createRigidArea(new Dimension(0, 100)));

        //Set a border on the menu
        menuBar.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

        return menuBar;
    }

    /**
     * Creates a menu button.
     * The button will be displayed as just text.
     */
    public JButton createMenuButton(String label) {
        JButton button = new JButton(label);

        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFont(UNPRESSED_FONT);

        Dimension dim = new Dimension(button.getPreferredSize().width + 15, button.getPreferredSize().height);
        button.setPreferredSize(dim);

        return button;
    }

    /**
     * Build the main panel to hold other panels.
     */
    private void buildPanel(){
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);

        textPane = new JTextPane();
        textPane.setPreferredSize(new Dimension(800, 500));
        doc = textPane.getStyledDocument();
        textPane.setEditable(false);

        mainPanel.add(textPane);
    }

    /**
     * Retrieve and display tab symbols in the text pane.
     */
    private void displayTabSymbols(){
        //Get tab symbol text
        Path path = getFilePath(TAB_SYMBOLS);

        //Array to hold text
        String[] textArray = new String[1];
        textArray[0] = "";

        Charset charset = Charset.forName("US-ASCII");
        try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
            String line = "";
            int i = 0;
            while ((line = reader.readLine()) != null){
                textArray[0] += line + "\n";
                i++;
            }

            doc.insertString(doc.getLength(), textArray[0], null);

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the file path
     */
    private Path getFilePath(String type){
        Path pth;
        //Get tab symbols file path
        if(type.equals(TAB_SYMBOLS)){
            pth = Paths.get("src", "resources", "help_text", "tab_symbols.txt");
        }else{
            pth = null;
        }

        return pth;
    }


    /**
     * Listener class for tab symbol button click
     */
    private class HowToButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Make how to button bold and others plain
            howToButton.setFont(PRESSED_FONT);
            tabSymbolsButton.setFont(UNPRESSED_FONT);
            tuningsButton.setFont(UNPRESSED_FONT);

            //Empty text pane
            textPane.setText("");
        }
    }

    /**
     * Listener class for tab symbol button click
     */
    private class TabSymbolButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Make tab symbols button bold and others plain
            tabSymbolsButton.setFont(PRESSED_FONT);
            howToButton.setFont(UNPRESSED_FONT);
            tuningsButton.setFont(UNPRESSED_FONT);

            //Empty text pane
            textPane.setText("");

            //Show tab symbol chart
            displayTabSymbols();
        }
    }

    /**
     * Listener class for tab symbol button click
     */
    private class TuningsButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //Make tunings button bold and others plain
            tuningsButton.setFont(PRESSED_FONT);
            tabSymbolsButton.setFont(UNPRESSED_FONT);
            howToButton.setFont(UNPRESSED_FONT);

            //Empty text pane
            textPane.setText("");
        }
    }

}
