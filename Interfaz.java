package Supergato;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.*;
import javax.swing.*;

public class Interfaz {
    JButton[][] botonesGrandes = new JButton[3][3]; // Arreglo para guardar los botones del tablero grande
    JButton[][][][] botonesPeq = new JButton[3][3][3][3]; // Matriz de matrices para guardar los botones de los tableros pequeños
    JFrame frame; //Crear frame
    JPanel panelConImagen; //Crear JPanel para agregar imagen de fondo
    Logica logica = new Logica(); //Crear objeto de la clase Logica
    Color colorBotones = new Color(250, 198, 218); //Crear color para botones (opcional)
    Image imagenDeFondo = new ImageIcon("GATO.jpg").getImage(); //Importar imagen de fondo

    //Constructor de la clase Interfaz
    public Interfaz(){
        frame = new JFrame("Gato"); //Frame

        //Configurar panel con imagen de fondo
        panelConImagen = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imagenDeFondo, 0, 0, getWidth(), getHeight(), this);
            }
        };

        panelConImagen.setLayout(null);       
        frame.setSize(580, 720); //Tamaño del frame
        frame.setLayout(null);
        frame.setVisible(true);  //Hacer frame visible 
        frame.setContentPane(panelConImagen); //Agregar panel con imagen al frame
    }

    public void crearTableros(){ // Método para crear todos los tableros

        // Variables para las posiciones iniciales de los botones pequeños y grandes en el eje X e Y
        int valorX = 50;
        int valorY = 92;
    
        // Ciclo para crear los botones del tablero grande (3x3)
        for(int filaGrande = 0; filaGrande < 3; filaGrande++){
            for(int columnaGrande = 0; columnaGrande < 3; columnaGrande++){

                // Calcular el desplazamiento basado en la fila y columna dentro de la cuadrícula grande
                int desplX = columnaGrande * (3 * 50 + 10); 
                int desplY = filaGrande * (3 * 47 + 10); 

                // Crear y posicionar los botones grandes
                botonesGrandes[filaGrande][columnaGrande] = new JButton(" ");
                botonesGrandes[filaGrande][columnaGrande].setBounds(valorX + desplX, valorY + desplY, 141, 141); // Tamaño y posición del botón grande
                botonesGrandes[filaGrande][columnaGrande].setContentAreaFilled(false); // Quitar color al botón
                botonesGrandes[filaGrande][columnaGrande].setBorderPainted(false); // Quitar borde
                botonesGrandes[filaGrande][columnaGrande].setEnabled(false);



                // Ciclo para crear los botones pequeños dentro de cada botón grande (3x3 botones por cada tablero)
                for(int filaPeq = 0; filaPeq < 3; filaPeq++){
                    for(int columnaPeq = 0; columnaPeq < 3; columnaPeq++){

                        int fila = filaPeq;
                        int columna = columnaPeq;

                        botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq] = new JButton(" "); //Crear botón sin texto
                        botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq].setBounds(valorX + desplX + columnaPeq * 49, valorY + desplY + filaPeq * 49, 47, 47); //Ubicación y tamaño de cada botón
                        botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq].setFont(botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq].getFont().deriveFont(16f)); //Definir tamaño del texto
                        botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq].setContentAreaFilled(false); //Quitar color al botón
                        botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq].setBorderPainted(false); //Quitar borde al botón

                        //Definir ActionListener de los botones
                        botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq].addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e){
                            JButton btn = (JButton) e.getSource(); //Crear copia de botón para el actionListener
                            if (btn.getText().equals(" ")){ //Acción si el botón está disponible
                                btn.setText(logica.turno); //Cambiar a X o O según turno
                                logica.verificarGanador(botonesPeq[fila][columna]); //Revisar si hay ganador en la submatriz
                                logica.matricesDisponibles(botonesGrandes, botonesPeq, fila, columna, frame);
                                logica.cambiarTurno(); //Cambiar turno de jugadores

                            }
                        }
                    });

                        // Agregar al frame
                        frame.add(botonesPeq[filaGrande][columnaGrande][filaPeq][columnaPeq]);
                    }
                }
                frame.add(botonesGrandes[filaGrande][columnaGrande]); 

            }
        }                
    }

    //Método del botón de reinicio
    public void botonReiniciar(){

        JButton btnReiniciar = new JButton("Reiniciar"); //Crear botón "Reiniciar"
        btnReiniciar.setBounds(34, 576, 160, 40); //Ubicación y tamaño del botón
        btnReiniciar.setFont(btnReiniciar.getFont().deriveFont(15f)); //Definir tamaño del texto
        btnReiniciar.setBackground(colorBotones); //Definir color del botón
        btnReiniciar.setBorderPainted(false); //Quitar borde al botón
        btnReiniciar.setContentAreaFilled(false);
        btnReiniciar.setForeground(new Color(0, 0, 0, 0));  

        //Definir ActionListener de botón Reiniciar
        btnReiniciar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reiniciarJuego(); //Llamar método reiniciarJuego
            }
        });
        frame.add(btnReiniciar); //Agregar botón al frame

    }

    //Método para reiniciar juego
    public void reiniciarJuego() {
        // Eliminar todos los componentes actuales del frame
        frame.getContentPane().removeAll();
    
        // Recrear los tableros desde cero
        crearTableros();
        
        // Vuelve a agregar el botón de reiniciar
        botonReiniciar();
        
        // Volver a validar y repintar la interfaz
        frame.revalidate();
        frame.repaint();
    
        // Reiniciar el turno
        logica.turno = "X";
    }
    

}
