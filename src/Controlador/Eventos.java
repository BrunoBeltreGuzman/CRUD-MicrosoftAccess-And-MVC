/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Consultas;
import Modelo.Estudiante;
import Vista.Ventana;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.WARNING_MESSAGE;

/**
 *
 * @author diosl
 */
public class Eventos implements MouseListener, KeyListener {

    private Ventana ventana;
    private Estudiante estudiante;

    Color color = new Color(175, 32, 49);

    public Eventos(Ventana v) {

        this.ventana = v;

        ventana.getBtnBruscar().addMouseListener(this);
        ventana.getBtnRegistrar().addMouseListener(this);
        ventana.getBtnModificar().addMouseListener(this);
        ventana.getBtnEliminar().addMouseListener(this);
        ventana.getBtnLimpiar().addMouseListener(this);
        ventana.getBtnPdf().addMouseListener(this);
        
        ventana.getTexId().addKeyListener(this);
        ventana.getTexApellido().addKeyListener(this);
        ventana.getTexEdad().addKeyListener(this);
        ventana.getTexCurso().addKeyListener(this);
        ventana.getTexNombre().addKeyListener(this);
        
        ventana.getJtEstudiantes().addMouseListener(this);
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == ventana.getJtEstudiantes()) {
            Consultas consultas = new Consultas();
            consultas.MouseClickedTabla(ventana);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

        if (e.getSource() == ventana.getBtnBruscar()) {
            ventana.getBtnBruscar().setBackground(color.brighter());
        }
        if (e.getSource() == ventana.getBtnRegistrar()) {
            ventana.getBtnRegistrar().setBackground(color.brighter());
        }
        if (e.getSource() == ventana.getBtnModificar()) {
            ventana.getBtnModificar().setBackground(color.brighter());
        }
        if (e.getSource() == ventana.getBtnEliminar()) {
            ventana.getBtnEliminar().setBackground(color.brighter());
        }
        if (e.getSource() == ventana.getBtnLimpiar()) {
            ventana.getBtnLimpiar().setBackground(color.brighter());
        }
        if (e.getSource() == ventana.getBtnPdf()) {
            ventana.getBtnPdf().setBackground(color.brighter());
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == ventana.getBtnBruscar()) {
            ventana.getBtnBruscar().setBackground(color);
        }
        if (e.getSource() == ventana.getBtnRegistrar()) {
            ventana.getBtnRegistrar().setBackground(color);
        }
        if (e.getSource() == ventana.getBtnEliminar()) {
            ventana.getBtnEliminar().setBackground(color);
        }
        if (e.getSource() == ventana.getBtnModificar()) {
            ventana.getBtnModificar().setBackground(color);
        }
        if (e.getSource() == ventana.getBtnLimpiar()) {
            ventana.getBtnLimpiar().setBackground(color);
        }
        if (e.getSource() == ventana.getBtnPdf()) {
            ventana.getBtnPdf().setBackground(color);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
        if (e.getSource() == ventana.getTexId()) {
            Consultas consultas = new Consultas();
            consultas.CargarTabla(ventana);
            ventana.getTexResultado().setText(ventana.getCbBuscar().getSelectedItem() + ": registros encontrados: " + consultas.getCantidadDeRegistros());
           
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                   ventana.getBtnBruscar().doClick();
            }
        }
        
        if(e.getSource() == ventana.getTexNombre()){
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                  ventana.getTexApellido().requestFocus();                  
            }
            
        }
         if(e.getSource() == ventana.getTexApellido()){
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                  ventana.getTexEdad().requestFocus();                  
            }
            
        }
          if(e.getSource() == ventana.getTexEdad()){
            if (e.getExtendedKeyCode() == KeyEvent.VK_ENTER) {
                  ventana.getTexCurso().requestFocus();                  
            }
            
        }
    }

}
