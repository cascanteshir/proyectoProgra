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

    public void matricesDisponibles(JButton[][] botonesGrandes, JButton[][][][] botonesPeq, int fila, int columna, JFrame frame) {
        // Ciclo para ir verificando si alguien ganó cada vez que se toca un botón
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
    
                // Variable con un booleano para saber si se ganó en un tablero específico
                boolean miniTableroGanado = verificarGanador(botonesPeq[m][n]);
    
                if (miniTableroGanado) {
                    // Verificar si el botón grande ya tiene un ganador (si tiene "X" o "O", no se debe modificar)
                    if (botonesGrandes[m][n].getText().equals("X") || botonesGrandes[m][n].getText().equals("O")) {
                        continue; // Si ya tiene un ganador, no modificar el botón grande
                    }
    
                    String textoGrande = turno; // Variable donde se guarda el texto que va en el botón grande ("X" o "O")
    
                    // Ocultar los botones pequeños de la submatriz ganada
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            botonesPeq[m][n][o][p].setVisible(false);  // Ocultar todos los botones pequeños de la submatriz
                        }
                    }
    
                    // Actualizar el botón grande correspondiente al mini-tablero ganado
                    botonesGrandes[m][n].setText(textoGrande);  // Usamos m y n, que representan la submatriz ganada
                    botonesGrandes[m][n].setFont(botonesGrandes[m][n].getFont().deriveFont(130f));
                    botonesGrandes[m][n].setEnabled(true);
    
                    // Deshabilitar los botones de la submatriz donde se ganó
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            botonesPeq[m][n][o][p].setEnabled(false);  // Deshabilitar todos los botones pequeños de la submatriz ganada
                        }
                    }
    
                } else {
                    // Habilitar o deshabilitar los botones dependiendo de en qué mini-tablero se debe jugar
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            if (m == fila && n == columna) {
                                botonesPeq[m][n][o][p].setEnabled(true);  // Habilita los botones del mini-tablero donde se debe jugar
                            } else {
                                botonesPeq[m][n][o][p].setEnabled(false);  // Deshabilita los botones de otros mini-tableros
                            }
                        }
                    }
                }
            }
        }
    }
}
