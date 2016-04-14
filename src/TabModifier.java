import javax.swing.*;
import javax.swing.text.html.HTMLDocument;

/**
 * Class to handle modifications to tablature.
 * This will include length changes and tuning changes.
 */
public class TabModifier {

    private TabSheet tabSheet;
    private String tuningTo;
    private String tuningFrom;
    private boolean fourString;
    private int stringNum;
    private int c;

    public TabModifier(TabSheet ts){
        tabSheet = ts;
        tuningTo = "";
        fourString = false;
        stringNum = 0;
        c = 0;
    }

    /**
     * Modify the tablature from the current tuning to the new tuning.
     * Return the new tab as a String.
     */
    public void modifyTab(String tun1, String tun2){
        int i = 0;
        String altTab = "";

        int steps = getSteps(tun1,tun2);

        if(steps != 0){
            int marker = 0;
            for(String line : tabSheet.getString().split("\\n")){
                if(line.charAt(1) == '|' || line.charAt(2) == '|'){

                    String s = "";
                    char t = line.charAt(c);
                    int sl = line.length();

                    if(marker == 0 && t == 'G'){
                        stringNum += 2;
                        marker += 1;
                    }
                    else if(marker == 0 && stringNum > 0) marker = 1;

                    s += newStrTune();
                    c++;
                    t = line.charAt(c);

                    if(t != '|'){
                        c++;
                    }


                    /*while(sl > c){
                        t = line.charAt(c);

                        int k = Math.abs(steps);

                        /*if(k == 2 && stringNum < 5) {s += Character.toString(t);}
                        else if(k == 2 && stringNum == 5) {
                            s += dropD(t,line.charAt(c+1),steps);
                        }
                        else if(k == 1 && tuningTo.equalsIgnoreCase("D") && stringNum < 5) {
                            s += dropD(t,line.charAt(c+1),1);
                        }
                        else if(k == 1 && tuningTo.equalsIgnoreCase("D") && stringNum == 5){
                            s += dropD(t,line.charAt(c+1),1);
                        }
                        else if(k == 1 && tuningTo.equalsIgnoreCase("Eb") && stringNum < 5){
                            s += dropD(t,line.charAt(c+1),1);
                        }
                        else if(k == 1 && tuningTo.equalsIgnoreCase("Eb") && tuningFrom.equalsIgnoreCase("D")
                                && stringNum == 5){
                            s += dropD(t,line.charAt(c+1),-1);
                        }
                        else{
                            s += dropD(t,line.charAt(c+1),steps);
                        }

                        //s += dropD(t, line.charAt(c+1),steps);
                        c++;
                    }*/

                    altTab += s + line.substring(c,sl)+"\n";

                    //altTab += s + "\n";
                    s = "";

                    if(stringNum < 6) stringNum++;
                    else {
                        stringNum = 0;
                        marker = 0;
                    }
                }
                else{
                    altTab += line + "\n";
                }
                c = 0;
            }
            tabSheet.setText(altTab);
        }
    }


    private String dropD(char t, char p, int steps){
        String num = "";
        String str = "";

        if(t != '-' && 9 >= Character.getNumericValue(t) && Character.getNumericValue(t) >= 0){
            num += Character.toString(t);

            if (p != '-' && 9 >= Character.getNumericValue(p) && Character.getNumericValue(p) >= 0) {
                num += Character.toString(p);
                c++;
            }

            int q = Integer.parseInt(num);
            int m = q + steps;
            str += Integer.toString(m);
        }
        else{
            return Character.toString(t);
        }
        return str;
    }


    private int getSteps(String tun1, String tun2){

        char k1 = ' ';
        char k11 = ' ';
        char k2 = ' ';
        char k21 = ' ';
        int steps = 0;

        //  determine the user input of current instrument tuning
        if(tun1.equalsIgnoreCase("E Standard")){
            k1 = 'E';
            tuningFrom = "E";
        }
        else if(tun1.equalsIgnoreCase("E Flat")){
            k1 = 'E';
            k11 = 'b';
            tuningFrom = "Eb";
        }
        else{
            k1 = 'D';
            tuningFrom = "D";
        }

        //  determine the user's desired instrument tuning
        if(tun2.equalsIgnoreCase("E Standard")){
            k2 = 'E';
            tuningTo = "E";
        }
        else if(tun2.equalsIgnoreCase("E Flat")){
            k2 = 'E';
            k21 = 'b';
            tuningTo = "Eb";
        }
        else{
            k2 = 'D';
            tuningTo = "D";
        }

        if(k1 == k2 && k11 == k21) steps = 0;
        else if(k1 == k2 && k11 == 'b') steps = -1;
        else if(k1 == k2 && k21 == 'b') steps = 1;
        else if(k1 == 'E' && k11 == 'b' && k2 == 'D') steps = 1;
        else if(k1 == 'D' && k2 == 'E' && k21 == 'b') steps = -1;
        else if(k1 == 'E' && k2 == 'D') steps = 2;
        else if(k1 == 'D' && k2 == 'E') steps = -2;

        return steps;
    }

    private String newStrTune(){
        if(tuningTo.equalsIgnoreCase("E") && stringNum == 5) return "E";
        else if(tuningTo.equalsIgnoreCase("D") && stringNum == 5) return "D";
        else if((tuningTo.equalsIgnoreCase("E") || tuningTo.equalsIgnoreCase("D")) && stringNum == 0) return "e";
        else if((tuningTo.equalsIgnoreCase("E") || tuningTo.equalsIgnoreCase("D")) && stringNum == 1) return "B";
        else if((tuningTo.equalsIgnoreCase("E") || tuningTo.equalsIgnoreCase("D")) && stringNum == 2) return "G";
        else if((tuningTo.equalsIgnoreCase("E") || tuningTo.equalsIgnoreCase("D")) && stringNum == 3) return "D";
        else if((tuningTo.equalsIgnoreCase("E") || tuningTo.equalsIgnoreCase("D")) && stringNum == 4) return "A";
        else if(tuningTo.equalsIgnoreCase("Eb") && stringNum == 0) return "eb";
        else if(tuningTo.equalsIgnoreCase("Eb") && stringNum == 1) return "Bb";
        else if(tuningTo.equalsIgnoreCase("Eb") && stringNum == 2) return "Gb";
        else if(tuningTo.equalsIgnoreCase("Eb") && stringNum == 3) return "Db";
        else if(tuningTo.equalsIgnoreCase("Eb") && stringNum == 4) return "Ab";
        else if(tuningTo.equalsIgnoreCase("Eb") && stringNum == 5) return "Eb";
        else return "";
    }
}
