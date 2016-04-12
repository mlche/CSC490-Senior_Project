import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Created by ken12_000 on 4/11/2016.
 */
public class TabCaret extends DefaultCaret {

    private Boolean overwriteMode;

    public TabCaret(){
        overwriteMode = false;
    }

    /**
     * Damages the area surrounding the caret to cause it to be repainted in a new location.
     */
    protected synchronized void damage(Rectangle r) {
        if(!overwriteMode){
            super.damage(r);
        }
        else {
            if (r == null)
                return;

            //Give values to x, y, width, height (inherited from java.awt.Rectangle)
            x = r.x;
            y = r.y;
            height = r.height;

            //May be set by paint (better value), set here if not
            if (width <= 0)
                width = getComponent().getWidth();

            //Calls getComponent().repaint(x, y, width, height)
            repaint();
        }
    }

    public void paint(Graphics g) {
        if(!overwriteMode){
            super.paint(g);
        }
        else {
            JTextComponent comp = getComponent();
            if (comp == null)
                return;

            //getDot() returns current location of the caret
            int dot = getDot();
            Rectangle r = null;
            char dotChar;

            try {
                r = comp.modelToView(dot);
                if (r == null)
                    return;
                dotChar = comp.getText(dot, 1).charAt(0);
            } catch (BadLocationException e) {
                return;
            }

            if ((x != r.x) || (y != r.y)) {
                // paint() has been called directly, without a previous call to
                // damage(), so do some cleanup. (This happens, for example, when the text component is resized.)

                repaint(); // erase previous location of caret
                x = r.x; // Update dimensions (width gets set later in this method)
                y = r.y;
                height = r.height;
            }

            g.setColor(comp.getCaretColor());
            g.setXORMode(comp.getBackground()); // do this to draw in XOR mode

        /*if (dotChar == '\n') {
            int diam = r.height;
            if (isVisible()) {
                g.fillArc(r.x - diam / 2, r.y, diam, diam, 270, 180); //Half circle
            }
            width = diam / 2 + 2;
            return;
        }*/

            width = g.getFontMetrics().charWidth(dotChar);

            if (dotChar == '\t' || dotChar == '\n') {
            /*try {
                Rectangle nextr = comp.modelToView(dot + 1);
                if ((r.y == nextr.y) && (r.x < nextr.x)) {
                    width = nextr.x - r.x;
                    if (isVisible())
                        g.fillRoundRect(r.x, r.y, width, r.height, 12, 12);
                    return;
                } else
                    dotChar = ' ';
            } catch (BadLocationException e) {
                dotChar = ' ';
            }*/
                width = g.getFontMetrics().charWidth(' ');
            }

            if (isVisible())
                g.fillRect(r.x, r.y, width, r.height);
        }
    }

    /**
     * Sets overwrite mode.
     */
    public void setOverwriteMode(Boolean mode){
        overwriteMode = mode;
    }
}
