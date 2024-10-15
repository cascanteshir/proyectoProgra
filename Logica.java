package Supergato;

import javax.swing.*;
import java.awt.event.*;

public class Logica {
    String turno = "X"; //Registro de turno
    String ganador = "";

    //Método para cambiar el turno
    public void cambiarTurno(){
        if(turno == "O"){
            turno = "X";
        }else{
            turno = "O";
        }
    }

    /* Verifica si hay un ganador en una submatriz
     * matriz: tablero a verificar
     * 
     * retorna true si hay ganador, de lo contrario retorna false
     */
    public boolean verificarGanador(JButton[][] matriz) { // Método para verificar si se ganó en un tablero
        boolean gane = false;

        for (int i = 0; i < 3; i++) {
            //Verifica si en una misma fila los botones tienen el mismo texto (si no está vacío)
            if (matriz[i][0].getText() != " " && 
            matriz[i][0].getText().equals(matriz[i][1].getText()) && 
            matriz[i][1].getText().equals(matriz[i][2].getText())) {
                gane = true; 
            }
            //Verifica si en una misma columna los botones tienen el mismo texto (si no está vacío)
            if (matriz[0][i].getText() != " " && 
            matriz[0][i].getText().equals(matriz[1][i].getText()) && 
            matriz[1][i].getText().equals(matriz[2][i].getText())) {
                gane = true;
            }
            // Verifica si en la diagonal principal los botones tienen el mismo texto (si no está vacío)
            if (matriz[0][0].getText() != " " && 
            matriz[0][0].getText().equals(matriz[1][1].getText()) && 
            matriz[1][1].getText().equals(matriz[2][2].getText())) {
                gane = true;
            }
            // Verifica si en la otra diagonal los botones tienen el mismo texto (si no está vacío)
            if (matriz[2][0].getText() != " " && 
            matriz[2][0].getText().equals(matriz[1][1].getText()) && 
            matriz[1][1].getText().equals(matriz[0][2].getText())) {
                gane = true;
            }
        }
        return gane;
    }

    /**
     * Habilita o deshabilita los mini-tableros dependiendo del estado actual del juego.
     * 
     * botonesGrandes: Un arreglo 2D de JButton que representa los botones grandes del tablero.
     * botonesPeq: Un arreglo 4D de JButton que representa los botones pequeños (mini-tableros).
     * fila: La fila del tablero grande donde se acaba de jugar.
     * columna: La columna del tablero grande donde se acaba de jugar.
     * frame: El JFrame principal del juego.
     */
    public void matricesDisponibles(JButton[][] botonesGrandes, JButton[][][][] botonesPeq, int fila, int columna, JFrame frame) {
        
        boolean miniTableroGanado;  //Variable para saber si se ha ganado en algún tablero
    
        //Ciclo para verificar el estado de cada mini-tablero
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {

                miniTableroGanado = verificarGanador(botonesPeq[m][n]);
    
                if (miniTableroGanado) {

                    //Si el tablero ya tiene un ganador no modificar el botón grande
                    if (botonesGrandes[m][n].getText().equals("X") || botonesGrandes[m][n].getText().equals("O")) {
                        continue;  //Si el botón grande ya tiene ganador, pasa a la siguiente iteración
                    }
    
                    String textoGrande = turno;  //Asigna el texto del ganador del mini-tablero
    
                    // Oculta los botones pequeños en la submatriz ganada
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {
                            botonesPeq[m][n][o][p].setVisible(false);  // Oculta los botones pequeños
                            botonesPeq[m][n][o][p].setEnabled(false);  // Deshabilita los botones pequeños
                        }
                    }
    
                    // Actualiza el botón grande correspondiente
                    botonesGrandes[m][n].setText(textoGrande);
                    botonesGrandes[m][n].setFont(botonesGrandes[m][n].getFont().deriveFont(130f));
                    botonesGrandes[m][n].setEnabled(true);
    
                } else {
                    //Si aún no se ha ganado, se habilita o deshabilita dependiendo de la jugada
                    for (int o = 0; o < 3; o++) {
                        for (int p = 0; p < 3; p++) {

                            //Si es el mini-tablero donde debe jugarse el siguiente turno
                            if (m == fila && n == columna) {
                                botonesPeq[m][n][o][p].setEnabled(true);
                            } else {
                                botonesPeq[m][n][o][p].setEnabled(false);
                            }
                        }
                    }
                }
            }
        }
    
        //Verificar si el mini-tablero donde se va a jugar no está ganado
        if(verificarGanador(botonesPeq[fila][columna])){

            //Si el mini-tablero que se va a jugar está ganado, habilitar las que aún están disponibles
            for(int m = 0; m < 3; m++){
                for(int n = 0; n < 3; n++){

                    //Revisar que el mini-tablero no haya sido ganado
                    if(!verificarGanador(botonesPeq[m][n])){
                        for(int o = 0; o < 3; o++){
                            for(int p = 0; p < 3; p++){
                                botonesPeq[m][n][o][p].setEnabled(true);
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * Verifica si hay un ganador en el tablero principal (botones grandes).
     * 
     * botonesGrandes: Un arreglo 2D de JButton que representa el tablero grande.
     * botonesPeq: Un arreglo 4D de JButton que representa los botones pequeños.
     * 
     * Retorna true si hay un ganador, de lo contrario false.
     */
    public boolean ganadorDefinitivo(JButton[][] botonesGrandes, JButton[][][][] botonesPeq){

        for (int i = 0; i < 3; i++) {
    
            // Verifica si en una misma fila los botones tienen el mismo texto (si no está vacío)
            if (!botonesGrandes[i][0].getText().equals(" ") && 
                botonesGrandes[i][0].getText().equals(botonesGrandes[i][1].getText()) && 
                botonesGrandes[i][1].getText().equals(botonesGrandes[i][2].getText())) {
                ganador = botonesGrandes[i][0].getText();  // Aquí se asigna correctamente el ganador
                return true;
            }
    
            // Verifica si en una misma columna los botones tienen el mismo texto (si no está vacío)
            if (!botonesGrandes[0][i].getText().equals(" ") && 
                botonesGrandes[0][i].getText().equals(botonesGrandes[1][i].getText()) && 
                botonesGrandes[1][i].getText().equals(botonesGrandes[2][i].getText())) {
                ganador = botonesGrandes[0][i].getText();  // Asignar correctamente el ganador de la columna
                return true;
            }
        }
    
        // Verifica la diagonal principal
        if (!botonesGrandes[0][0].getText().equals(" ") && 
            botonesGrandes[0][0].getText().equals(botonesGrandes[1][1].getText()) && 
            botonesGrandes[1][1].getText().equals(botonesGrandes[2][2].getText())) {
            ganador = botonesGrandes[0][0].getText();  // Cambia a la posición correcta para la diagonal principal
            return true;
        }
    
        // Verifica la diagonal secundaria
        if (!botonesGrandes[2][0].getText().equals(" ") && 
            botonesGrandes[2][0].getText().equals(botonesGrandes[1][1].getText()) && 
            botonesGrandes[1][1].getText().equals(botonesGrandes[0][2].getText())) {
            ganador = botonesGrandes[2][0].getText();  // Cambia a la posición correcta para la diagonal secundaria
            return true;
        }
    
        return false;
    }
    

    /**
     * Desactiva todos los botones del tablero.
     * 
     * botonesPeq: Un arreglo 4D de JButton que representa los botones pequeños.
     * botonesGrandes: Un arreglo 2D de JButton que representa los botones grandes.
     */
    public void desaparecerTodosLosBotones(JButton[][][][] botonesPeq, JButton[][] botonesGrandes) {
        //Recorrer todas las submatrices de botones pequeños
        for (int m = 0; m < 3; m++) {
            for (int n = 0; n < 3; n++) {
                for (int o = 0; o < 3; o++) {
                    for (int p = 0; p < 3; p++) {
                        // Deshabilitar cada botón pequeño
                        botonesPeq[m][n][o][p].setVisible(false);
                    }
                }
            }
        }
    
        //Recorrer botones grandes y hacerlos invisibles
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                botonesGrandes[i][j].setVisible(false);
            }
        }
    }
    
}
