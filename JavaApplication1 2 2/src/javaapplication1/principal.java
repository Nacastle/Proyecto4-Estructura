package javaapplication1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.MultiGraph;

public class principal {

    public ArrayList<Linea> lineas = new ArrayList();
    public ArrayList<Grupo> set = new ArrayList();
    public int noGrupos;
    public int noPersonasPorGrupo;
    public int noDescansosLider;
    public int totalPersonas;
    public int personasFaltantes;
    //variables para que se conoscan
    int numeroSetNecesarios;
    Graph graph = new MultiGraph("Grupos");

    principal() throws IOException {

        muestraContenido("./hola.txt");
        Scanner entrada = new Scanner(System.in);
        noPersonasPorGrupo = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduzca el No de personas por grupo."));
        noDescansosLider = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduzca el No de descansos para lideres"));
//        System.out.println("Introdusca el No de personas por grupo:");
//        noPersonasPorGrupo = entrada.nextInt();
//        System.out.println("Introdusca el No de descansos para lideres:");
//        noDescansosLider = entrada.nextInt();

        for (int i = 0; i < lineas.size(); i++) {
            totalPersonas = totalPersonas + lineas.get(i).noPersonas;
        }
        noGrupos = totalPersonas / noPersonasPorGrupo;
        personasFaltantes = totalPersonas % noPersonasPorGrupo;
        System.out.println("Total de personas : " + totalPersonas);
        System.out.println("No de Grupos : " + noGrupos);
        System.out.println("Personas Resagadas: " + personasFaltantes);
        llenarPrimerSet();
        for (int i = 0; i < lineas.size(); i++) {
            graph.addNode(lineas.get(i).cadena);
        }
        hacerAmigos();
        imprimirSet();
        if (personasFaltantes > 0) {
            numeroSetNecesarios = (noGrupos + 1) * mayorGrupoExistente();
        } else {
            numeroSetNecesarios = noGrupos * mayorGrupoExistente();
        }
        /*numeroSetNecesarios=noGrupos*mayorGrupoExistente();
        /*por tiempo se permitira formar en los futuros set grupos menores que el estandar*/
        int contador = 0;
        int elec = 0;
        while (elec == 0) {
            elec = Integer.parseInt(JOptionPane.showInputDialog("Presiona cero para ver otro set o cualquier otro numero para salir SIN TERMINAR"));
            //System.out.println("Presiona cero para ver otro set o cualquier otro numero para salir SIN TERMINAR");
            //elec = entrada.nextInt();
            hacerAmigos();
            if (elec == 0) {
                cambiarSet();
                asignacionLideres();
                imprimirSet();
                for (Node n : graph) {
                    n.addAttribute("label", n.getId());
                }
                graph.display(true);
                contador++;
            }
            if (contador == numeroSetNecesarios) {
                JOptionPane.showMessageDialog(null, "Felicidades todos han sido lideres y todos se conocen");
                //System.out.println("Felicidades todos son lideres y amigos");
                elec = 2;
            }
        }

    }

    public void muestraContenido(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        while ((cadena = b.readLine()) != null) {
            //System.out.println(cadena); 
            lineas.add(new Linea(cadena));
        }
        b.close();
    }

    public void llenarPrimerSet() {
        Grupo grupoRef;
        for (int i = 0; i < noGrupos; i++) {
            grupoRef = new Grupo();
            for (int j = 0; j < lineas.size(); j++) {
                if (grupoRef.noIntegrantes() < noPersonasPorGrupo) {
                    if ((lineas.get(j).noPersonas <= (noPersonasPorGrupo - grupoRef.noIntegrantes())) && (lineas.get(j).tieneGrupo == false)) {
                        grupoRef.integrantes.add(lineas.get(j));
                        lineas.get(j).tieneGrupo = true;
                    }
                }
            }
            set.add(grupoRef);
        }
        llenarResagados();
        asignacionLideres();
    }

    public void asignacionLideres() {
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i).integrantes.size() > 0) {
                set.get(i).lider = set.get(i).integrantes.get(0);
            }

            //graph.addNode(set.get(i).lider = set.get(i).integrantes.get(0));
        }
    }

    public void llenarResagados() {
        /*int m=0;
        for (int i = 0; i < personasFaltantes; i++) {
            if (m==set.size()) {
                m=0;
            }
            for (int j = 0; j < lineas.size(); j++) {
                if (lineas.get(j).tieneGrupo==false) {
                    set.get(m).integrantes.add(lineas.get(j));
                    lineas.get(j).tieneGrupo = true;
                    m++;
                    j=lineas.size();
                }
            }
        }
         */
        if (personasFaltantes > 0) {
            Grupo grupo = new Grupo();
            for (int i = 0; i < personasFaltantes; i++) {
                for (int j = 0; j < lineas.size(); j++) {
                    if (lineas.get(j).tieneGrupo == false) {
                        grupo.integrantes.add(lineas.get(j));
                        lineas.get(j).tieneGrupo = true;
                        j = lineas.size();
                    }
                }
            }
            set.add(grupo);
        }

    }

    public void imprimirSet() {
        for (int i = 0; i < set.size(); i++) {
            set.get(i).imprimirIntegrantes();
        }
    }

    public int mayorGrupoExistente() {
        int respuesta = 0;
        for (int i = 0; i < set.size(); i++) {
            if (set.get(i).noIntegrantes() > respuesta) {
                respuesta = set.get(i).noIntegrantes();
            }
        }
        return respuesta;
    }

    /*es una opcion*/
    public void cambiarSet() {
        for (int i = 0; i < set.size(); i++) {
            if (i != (set.size() - 1)) {
                set.get(i + 1).integrantes.add(set.get(i).integrantes.get(0));
                set.get(i).integrantes.remove(0);
            } else {
                set.get(0).integrantes.add(set.get(i).integrantes.get(0));
                //graph.addNode(set.get(0).integrantes.add(set.get(i).integrantes.get(0)));
                set.get(i).integrantes.remove(0);
            }
        }
    }

    public void hacerAmigos() {
        Linea ref1;
        Linea ref2;
        for (int i = 0; i < set.size(); i++) {
            for (int j = 1; j < set.get(i).integrantes.size(); j++) {
                ref1 = set.get(i).integrantes.get(j);
                ref2 = set.get(i).integrantes.get(0);
                if (graph.getEdge(set.get(i).lider.cadena + ref1.cadena + ref2.cadena) == null) {
                    graph.addEdge(set.get(i).lider.cadena + ref1.cadena + ref2.cadena, ref1.cadena, ref2.cadena, true);
                }
            }
        }
    }

}
