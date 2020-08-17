/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package App;

import Controlador.CtrlEstudiante;
import Controlador.Eventos;
import Modelo.Consultas;
import Modelo.Estudiante;
import Vista.Cargar;
import Vista.Ventana;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author BRUNO BELTRE GUZMAN
 */
public class App {

    public static void main(String[] args) {
       
        Cargar cargar = new Cargar();
        cargar.setVisible(true);  
        
       
        
        Timer t = new Timer();
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                cargar.dispose();
                
                Estudiante est = new Estudiante();
                Consultas conslt = new Consultas();
                Ventana vent = new Ventana();
                Eventos eventos = new Eventos(vent);
                CtrlEstudiante ctrlEstudiante = new CtrlEstudiante(est, conslt, vent);
                ctrlEstudiante.IniciarVentana();
                
                vent.setVisible(true);
            }
        };
        t.schedule(tarea, 3000);

    }

}
