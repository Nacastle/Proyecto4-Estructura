
package javaapplication1;

import java.util.ArrayList;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;


public class Grupo {
    public ArrayList<Linea> integrantes=new ArrayList();
    public Linea lider=null;
    //Graph graph = new DefaultGraph("Grupos");
    public void imprimirIntegrantes(){
        System.out.println("Lider: "+lider.cadena);
        for (int i = 0; i < integrantes.size(); i++) {
            
            System.out.println(integrantes.get(i).cadena);
            //return integrantes.get(i).cadena;
        }
        System.out.println("");
    }
    
    public int noIntegrantes(){
        int respuesta=0;
        for (int i = 0; i < integrantes.size(); i++) {
            respuesta=respuesta+integrantes.get(i).noPersonas;
        }
        return respuesta;
    }
    
}
