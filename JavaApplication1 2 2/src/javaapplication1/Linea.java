
package javaapplication1;

import java.util.ArrayList;


public class Linea {
    public int noPersonas=0;
    public String cadena;
    public boolean lider=false;
    public int descanso=0;
    public boolean tieneGrupo=false;
    public ArrayList<Linea> amigos = new ArrayList();
    
    Linea(String nom){
        cadena=nom;
        int posicion=cadena.indexOf("&");
        if (posicion!=-1) {
            noPersonas=2;
        }else{
            noPersonas=1;
        }
    }
}
