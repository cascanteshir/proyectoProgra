package Supergato;

import javax.swing.*;
import java.awt.event.*;

public class Logica {
    String turno = "X"; //Registro de turno

    //Método para cambiar el turno
    public void cambiarTurno(){
        if(turno == "O"){
            turno = "X";
        }else{
            turno = "O";
        }
    }

    public boolean verificarGanador(JButton[][] matriz) { // Método para verificar si se ganó en un tablero
        boolean gane = false;

        for (int i = 0; i < 3; i++) {
            //Verifica si en una misma fila los botones tienen el mismo texto (si no está vacío)
            if (matriz[i][0].getText() != " " && matriz[i][0].getText().equals(matriz[i][1].getText()) && matriz[i][1].getText().equals(matriz[i][2].getText())) {
                gane = true; 
            }
            //Verifica si en una misma columna los botones tienen el mismo texto (si no está vacío)
            if (matriz[0][i].getText() != " " && matriz[0][i].getText().equals(matriz[1][i].getText()) && matriz[1][i].getText().equals(matriz[2][i].getText())) {
                gane = true;
            }
            // Verifica si en la diagonal principal los botones tienen el mismo texto (si no está vacío)
            if (matriz[0][0].getText() != " " && matriz[0][0].getText().equals(matriz[1][1].getText()) && matriz[1][1].getText().equals(matriz[2][2].getText())) {
                gane = true;
            }
            // Verifica si en la otra diagonal los botones tienen el mismo texto (si no está vacío)
            if (matriz[2][0].getText() != " " && matriz[2][0].getText().equals(matriz[1][1].getText()) && matriz[1][1].getText().equals(matriz[0][2].getText())) {
                gane = true;
            }
        }
        return gane;
    }

    public void matricesDisponibles(JButton[][] botonesGrandes, JButton[][][][] botonesPeq, int fila, int columna, JFrame frame){
        // Ciclo para ir verificando si alguien ganó cada vez que se toca un botón
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {

                // Variable con un booleano para saber si se gano en un tablero específico
                // (El método se explica más abajo)
                boolean miniTableroGanado = verificarGanador(botonesPeq[m][n]);
        
                if (miniTableroGanado) {
                    String textoGrande = ""; // Variable donde se guarda el texto que va en el botón grande
                    
                    // Se obtiene el texto del último botón que se presionó si el botón no está vacío
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            if (botonesPeq[m][n][o][p].getText() != " ") {
                                botonesPeq[m][n][o][p].setVisible(false);
                                textoGrande = botonesPeq[m][n][o][p].getText();
                                break; 
                            }

                        }
                    }
                    // Se pone el texto del ganador (que se guardó antes en la variable "textoGrande") en el botón grande ("X" u "O")
                    botonesGrandes[m][n].setText(textoGrande);
                    botonesGrandes[m][n].setFont(botonesGrandes[m][n].getFont().deriveFont(130f));

                    // Se añade el botón grande en la posición del tablero que se ganó al frame
                    frame.add(botonesGrandes[m][n]); 

                    // Deshabilitar los botones de la submatriz en que se ganó
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            botonesPeq[m][n][o][p].setEnabled(false);
                        }
                    }
                    
                } else {
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            // Si m y n corresponden al mini-tablero donde debe jugar el siguiente turno, se habilitan los botones
                            if (m == fila && n == columna) {
                                botonesPeq[m][n][o][p].setEnabled(true); // Habilita los botones del mini-tablero donde se debe jugar
                            } else {
                                botonesPeq[m][n][o][p].setEnabled(false); // Deshabilita los botones de otros mini-tableros
                            }
                        }
                    }
                }
            }
        }
    }
}
