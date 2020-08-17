/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Consultas;
import Modelo.Estudiante;
import java.awt.event.ActionListener;
import Vista.Ventana;
import java.awt.Color;
import static java.awt.Frame.MAXIMIZED_BOTH;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author diosl
 */
public class CtrlEstudiante implements ActionListener{

    private Ventana ventana;
    private Estudiante estudiante;
    private Consultas consultas;

    public CtrlEstudiante(Estudiante est, Consultas conslt, Ventana vent) {

        this.estudiante = est;
        this.consultas = conslt;
        this.ventana = vent;

        this.ventana.getBtnRegistrar().addActionListener(this);
        this.ventana.getBtnModificar().addActionListener(this);
        this.ventana.getBtnEliminar().addActionListener(this);
        this.ventana.getBtnBruscar().addActionListener(this);
        this.ventana.getBtnLimpiar().addActionListener(this);
        this.ventana.getBtnPdf().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        /**
         * Action Registar
         */
        if (e.getSource() == ventana.getBtnRegistrar()) {
            estudiante.setNombre(ventana.getTexNombre().getText().trim());
            estudiante.setApellido(ventana.getTexApellido().getText().trim());
            estudiante.setEdad(Integer.parseInt(ventana.getTexEdad().getText().trim()));
            estudiante.setCurso(ventana.getTexCurso().getText().trim());

            if (consultas.registrarEstudiante(estudiante)) {
                JOptionPane.showMessageDialog(ventana, "Datos Registrados Correctamente");
                Limpiar();
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  consultas.getCantidadDeRegistros());
            } else {
                JOptionPane.showMessageDialog(ventana, "Error al Registrar Datos", "ERROR SQL", ERROR_MESSAGE);
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  consultas.getCantidadDeRegistros());
            }
        }

        /**
         * Action Mofificar
         */
        if (e.getSource() == ventana.getBtnModificar()) {
            estudiante.setId(Integer.parseInt(ventana.getTexId().getText().trim()));
            estudiante.setNombre(ventana.getTexNombre().getText().trim());
            estudiante.setApellido(ventana.getTexApellido().getText().trim());
            estudiante.setEdad(Integer.parseInt(ventana.getTexEdad().getText().trim()));
            estudiante.setCurso(ventana.getTexCurso().getText().trim());

            if (consultas.ModificarEstudiante(estudiante)) {
                JOptionPane.showMessageDialog(ventana, "Datos Modificados Correctamente");
                Limpiar();
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  consultas.getCantidadDeRegistros());
            } else {
                JOptionPane.showMessageDialog(ventana, "Error al Modificar Datos", "ERROR SQL", ERROR_MESSAGE);
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  + consultas.getCantidadDeRegistros());
            }
        }

        /**
         * Action Eliminar
         */
        if (e.getSource() == ventana.getBtnEliminar()) {
            estudiante.setId(Integer.parseInt(ventana.getTexId().getText().trim()));
            if (consultas.EliminarEstudiante(estudiante)) {
                JOptionPane.showMessageDialog(ventana, "Datos Eliminados Correctamente");
                Limpiar();
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  consultas.getCantidadDeRegistros());
            } else {
                JOptionPane.showMessageDialog(ventana, "Error al Eliminar Datos", "ERROR SQL", ERROR_MESSAGE);
                Limpiar();
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  consultas.getCantidadDeRegistros());
            }
        }

        /**
         * Action Buscar/CargarTabla
         */
        if (e.getSource() == ventana.getBtnBruscar()) {

            if (consultas.CargarTabla(ventana)) {
                if (consultas.getCantidadDeRegistros() == 0) {
                    JOptionPane.showMessageDialog(ventana, "No hay ningun Registros con ese Id", "Registros no encontrados", WARNING_MESSAGE);
                }
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: " + consultas.getCantidadDeRegistros());
                consultas.setWhere("");

            } else {
                JOptionPane.showMessageDialog(ventana, "Error al Buscar Datos", "ERROR SQL", ERROR_MESSAGE);
                consultas.CargarTabla(ventana);
                ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+  consultas.getCantidadDeRegistros());
            }
        }

        /**
         * Action Limpiar
         */
        if (e.getSource() == ventana.getBtnLimpiar()) {
            Limpiar();
        }
        
         /**
         * Action GenerarPDF
         */
        if (e.getSource() == ventana.getBtnPdf()) {
            consultas.GenerarPDF();
        }
    }

    public void IniciarVentana() {
        ventana.setTitle("MMG");
        ventana.setExtendedState(MAXIMIZED_BOTH);
        ventana.getTexResultado().setText("Cargando datos de la tabla, por favor espere...");
        ventana.getTexResultado().setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/gifLoading.gif")));
        consultas.CargarTabla(ventana);
        ventana.getTexResultado().setIcon(null);
        ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados :" + consultas.getCantidadDeRegistros());
    }

    public void Limpiar() {
        ventana.getTexNombre().setText("");
        ventana.getTexApellido().setText("");
        ventana.getTexEdad().setText("");
        ventana.getTexCurso().setText("");
        ventana.getTexId().setText("");
        consultas.CargarTabla(ventana);
        ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem()+": registros encontrados: "+ consultas.getCantidadDeRegistros());
    }

}
