/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Vista.Ventana;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.HeadlessException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author diosl
 */
public class Consultas extends Conexion {

    private Ventana vista;
    private Ventana ventana;
    private Estudiante estudiante;

    private Connection connexion;
    private PreparedStatement preparedStatement;
    private final String campos = "(nombre,apellido,edad,curso)";
    private final String campos2 = "?,?,?,?";
    private String where = "";
    private int cantidadDeRegistros = 0;

    public int getCantidadDeRegistros() {
        return cantidadDeRegistros;
    }

    public void setCantidadDeRegistros(int cantidadDeRegistros) {
        this.cantidadDeRegistros = cantidadDeRegistros;
    }

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public boolean registrarEstudiante(Estudiante est) {
        connexion = getConexion();

        try {
            preparedStatement = connexion.prepareStatement("INSERT INTO Estudiantes " + campos + " VALUES " + campos2);
            preparedStatement.setString(1, est.getNombre());
            preparedStatement.setString(2, est.getApellido());
            preparedStatement.setInt(3, est.getEdad());
            preparedStatement.setString(4, est.getCurso());
            preparedStatement.execute();
            return true;
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            JOptionPane.showMessageDialog(vista, "ERROR: " + e, "ERROR SQL", ERROR_MESSAGE);
            return false;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Conexion Cerrada");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                }
            }
        }
    }

    public boolean ModificarEstudiante(Estudiante est) {
        connexion = getConexion();

        try {
            preparedStatement = connexion.prepareStatement("UPDATE Estudiantes SET nombre = ?, apellido = ?, edad = ?, curso = ? where id = " + est.getId());

            preparedStatement.setString(1, est.getNombre());
            preparedStatement.setString(2, est.getApellido());
            preparedStatement.setInt(3, est.getEdad());
            preparedStatement.setString(4, est.getCurso());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            JOptionPane.showMessageDialog(vista, "ERROR: " + e, "ERROR SQL", ERROR_MESSAGE);
            return false;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Conexion Cerrada");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                }
            }
        }
    }

    public boolean EliminarEstudiante(Estudiante est) {
        connexion = getConexion();

        try {
            preparedStatement = connexion.prepareStatement("DELETE FROM Estudiantes WHERE Id = ?");
            preparedStatement.setInt(1, est.getId());

            preparedStatement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(vista, "ERROR: " + e, "ERROR SQL", ERROR_MESSAGE);
            return false;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Conexion Cerrada");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                }
            }
        }
    }

    public boolean BuscarEstudiante(Estudiante est, Ventana v) {
        connexion = getConexion();
        ResultSet rs;
        String sql = null;

        if (!"".equals(v.getTexId().getText().trim())) {

            if (v.getCbBuscar().getSelectedIndex() == 0) {
                where = "WHERE Id LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

            if (v.getCbBuscar().getSelectedIndex() == 1) {
                where = "WHERE nombre LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

            if (v.getCbBuscar().getSelectedIndex() == 2) {
                where = "WHERE apellido LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

            if (v.getCbBuscar().getSelectedIndex() == 3) {
                where = "WHERE edad LIKE '%" + v.getTexId().getText().trim() + "%'";
            }
            if (v.getCbBuscar().getSelectedIndex() == 4) {
                where = "WHERE curso LIKE '%" + v.getTexId().getText().trim() + "%'";
            }
        }

        try {
            sql = "SELECT * FROM Estudiantes " + where;

            preparedStatement = connexion.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {

                est.setNombre(rs.getString("nombre"));
                est.setApellido(rs.getString("apellido"));
                est.setEdad(rs.getInt("edad"));
                est.setCurso(rs.getString("curso"));

                JOptionPane.showMessageDialog(vista, "Datos Encontrados Correctamente");
                return true;
            } else {
                JOptionPane.showMessageDialog(vista, "Datos NO Encontrados", "Advertencia", WARNING_MESSAGE);
            }
            return false;
        } catch (Exception e) {
            System.err.println(e);
            JOptionPane.showMessageDialog(vista, "Error al Buscar Datos", "ERROR SQL", ERROR_MESSAGE);
            JOptionPane.showMessageDialog(vista, "ERROR: " + e, "ERROR SQL", ERROR_MESSAGE);
            return false;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Conexion Cerrada");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                }
            }
        }
    }

    public boolean CargarTabla(Ventana v) {
        cantidadDeRegistros = 0;
        ResultSet rs;
        String sql;

        if (!"".equals(v.getTexId().getText().trim())) {

            if (v.getCbBuscar().getSelectedIndex() == 0) {
                where = "WHERE Id LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

            if (v.getCbBuscar().getSelectedIndex() == 1) {
                where = "WHERE nombre LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

            if (v.getCbBuscar().getSelectedIndex() == 2) {
                where = "WHERE apellido LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

            if (v.getCbBuscar().getSelectedIndex() == 3) {
                where = "WHERE edad LIKE '%" + v.getTexId().getText().trim() + "%'";
            }
            if (v.getCbBuscar().getSelectedIndex() == 4) {
                where = "WHERE curso LIKE '%" + v.getTexId().getText().trim() + "%'";
            }

        }

        connexion = getConexion();

        try {

            DefaultTableModel modelo = new DefaultTableModel();
            v.getJtEstudiantes().setModel(modelo);

            sql = "SELECT * FROM Estudiantes " + where;
            preparedStatement = connexion.prepareStatement(sql);
            rs = preparedStatement.executeQuery();

            ResultSetMetaData rsmd = rs.getMetaData();
            int cantidadDeCulumnas = rsmd.getColumnCount();

            modelo.addColumn("Id");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Edad");
            modelo.addColumn("Curso");

            v.getJtEstudiantes().setFont(new java.awt.Font("Arial", 0, 15));
            v.getJtEstudiantes().getTableHeader().setOpaque(false);
            v.getJtEstudiantes().getTableHeader().setBackground(new Color(175, 32, 49));
            v.getJtEstudiantes().getTableHeader().setForeground(Color.WHITE);
            v.getJtEstudiantes().setRowHeight(15);
            v.getJtEstudiantes().setBorder(null);
            v.getJtEstudiantes().setForeground(Color.BLACK);
            v.getJtEstudiantes().setRowHeight(20);

            int[] Ancho = {5, 100, 100, 100, 100};

            for (int x = 0; x < cantidadDeCulumnas; x++) {
                v.getJtEstudiantes().getColumnModel().getColumn(x).setPreferredWidth(Ancho[x]);
            }

            while (rs.next()) {

                Object[] fila = new Object[cantidadDeCulumnas];

                for (int i = 0; i < cantidadDeCulumnas; i++) {
                    fila[i] = rs.getObject(i + 1);

                }
                modelo.addRow(fila);
                cantidadDeRegistros++;
            }
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(v, "Error al Buscar Datos", "ERROR SQL", ERROR_MESSAGE);
            JOptionPane.showMessageDialog(v, "ERROR: " + e, "ERROR SQL", ERROR_MESSAGE);
            return false;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Conexion Cerrada");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                }
            }
        }
    }

    public boolean MouseClickedTabla(Ventana v) {
        cantidadDeRegistros = 0;
        ResultSet rs = null;
        connexion = getConexion();
        try {

            int fila = v.getJtEstudiantes().getSelectedRow();
            String codigo = v.getJtEstudiantes().getValueAt(fila, 0).toString();

            preparedStatement = connexion.prepareStatement("SELECT * FROM Estudiantes WHERE Id = ?");
            preparedStatement.setString(1, codigo);
            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                v.getTexId().setText(rs.getString(1));
                v.getTexNombre().setText(rs.getString(2));
                v.getTexApellido().setText(rs.getString(3));
                v.getTexEdad().setText(rs.getString(4));
                v.getTexCurso().setText(rs.getString(5));
                cantidadDeRegistros++;
            }
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(v, "Error al Buscar Datos", "ERROR SQL", ERROR_MESSAGE);
            JOptionPane.showMessageDialog(v, "ERROR: " + e, "ERROR SQL", ERROR_MESSAGE);
            return false;
        } finally {
            if (connexion != null) {
                try {
                    connexion.close();
                    System.out.println("Conexion Cerrada");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                }
            }
        }
    }

    public void GenerarPDF() {
        Document doc = new Document();
        Connection connection;
        PreparedStatement preparedStatement;
        ResultSet rs;
        Statement st;
        String sql;
        try {
            String ruta = System.getProperty("user.home");
            PdfWriter.getInstance(doc, new FileOutputStream(ruta + "/Desktop/Reporte PDF.pdf"));

            //Create Image and Text
            Image image = Image.getInstance("C:\\BD\\foto.png");
            image.scaleToFit(550, 1000);
            image.setAlignment(Chapter.ALIGN_CENTER);

            Paragraph parafo = new Paragraph();
            parafo.setAlignment(Chapter.ALIGN_CENTER);
            parafo.add("@ BrunoDev \n\n");
            parafo.setFont(FontFactory.getFont("Arial", 14, Font.BOLD, BaseColor.RED));
            parafo.add("Reporte PDF \n\n");

            doc.open();

            //Add Image
            doc.add(image);

            //Add Texto
            doc.add(parafo);

            PdfPTable tabla = new PdfPTable(6);
            tabla.addCell("Id");
            tabla.addCell("Nombre");
            tabla.addCell("Apellido");
            tabla.addCell("Edad");
            tabla.addCell("Curso");
            tabla.addCell("Estado");

            try {
                connection = getConexion();

                sql = "SELECT * FROM Estudiantes";

                st = (Statement) connection.createStatement();

                rs = st.executeQuery(sql);

                if (rs.next()) {
                    do {
                        tabla.addCell(rs.getString(1));
                        tabla.addCell(rs.getString(2));
                        tabla.addCell(rs.getString(3));
                        tabla.addCell(rs.getString(4));
                        tabla.addCell(rs.getString(5));
                        tabla.addCell("Activo");
                    } while (rs.next());

                    doc.add(tabla);
                    JOptionPane.showMessageDialog(null, "Documento creado Correctamente");
                    JOptionPane.showMessageDialog(null, "Ruta de PDF: /Desktop/");
                }

            } catch (DocumentException | HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null, "Error BD", "ERROR SQL", ERROR_MESSAGE);
            } finally {
                if (connexion != null) {
                    try {
                        connexion.close();
                        System.out.println("Conexion Cerrada");
                    } catch (SQLException sqle) {
                        JOptionPane.showMessageDialog(null, "Error al cerrar la conexion");
                    }
                }
            }
            doc.close();

        } catch (DocumentException | HeadlessException | FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error al generar Reporte", "ERROR SQL", ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al añadir image", "ERROR SQL", ERROR_MESSAGE);
        }
    }

}
