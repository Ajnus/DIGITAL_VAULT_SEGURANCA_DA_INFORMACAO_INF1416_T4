// -------------------------
// Jam Ajna Soares - 2211689 
// Olavo Lucas     - 1811181
// -------------------------

package model;

import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.Level;


public class Sistema {
    private static Sistema sistema;
    public final static Logger log = Logger.getLogger(Sistema.class.getName());
    private static boolean firstTime;

    private Sistema(){
        //verifica se é a primeira vez e salva no campo firstTime
    }

    public static Sistema getInstance(){
        if (sistema==null){
            Sistema.sistema = new Sistema();
        }
        return sistema;
    }
    public static void main(String[] arg){}
    public static void turnOn(){}
    public static void shutDown(){}
    public static Boolean FirstTime(){return firstTime;}
}
