package vista;

import modelo.Conexiondb;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class vistaFarmacia extends JFrame{
    public JTextField idtextField;
    public JTextField nombretextField;
    public JTextField preciotextField;
    public JButton crearButton;
    public JButton buscarButton;
    public JButton actualizarButton;
    public JButton borrarButton;
    private JPanel panelVista;
    private JComboBox disponibilidadcomboBox;
    private JButton cargarOpcionesButton;
    private JComboBox proveedorcomboBox;
    Conexiondb conectar = new Conexiondb();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    String sql;
    public vistaFarmacia(){
        setContentPane(panelVista);
        pack();
        setTitle("SISTEMA");
        setLocation(100,100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        crearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql = "INSERT INTO productos VALUES (?,?,?,?,?)";
                try{
                    con = conectar.getConexion();
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, Integer.parseInt(idtextField.getText()));
                    ps.setString(2, nombretextField.getText());
                    ps.setDouble(3, Double.parseDouble(preciotextField.getText()));
                    ps.setString(4, (String) disponibilidadcomboBox.getSelectedItem());
                    ps.setString(5,(String) proveedorcomboBox.getSelectedItem());
                    ps.executeUpdate();

                    con.close();
                    ps.close();
                    JOptionPane.showMessageDialog(null, "Registro creado con éxito.");
                } catch (Exception ecp){
                    JOptionPane.showMessageDialog(null, "ERROR en creación de registro.");
                    ecp.printStackTrace();
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql = "SELECT * FROM productos WHERE idp=" + idtextField.getText();
                try {
                    con = conectar.getConexion();
                    ps = con.prepareStatement(sql);
                    rs = ps.executeQuery();
                    //System.out.println(rs.);
                    rs.next();
                    idtextField.setText("");
                    nombretextField.setText("");
                    preciotextField.setText("");
                    idtextField.setText(String.valueOf(rs.getString(1)));
                    nombretextField.setText(rs.getString(2));
                    preciotextField.setText(String.valueOf(rs.getString(3)));
                    disponibilidadcomboBox.removeAllItems();
                    disponibilidadcomboBox.addItem(rs.getString(4));
                    proveedorcomboBox.removeAllItems();
                    proveedorcomboBox.addItem(rs.getString(5));
                    con.close();
                    ps.close();
                    rs.close();
                    //JOptionPane.showMessageDialog(null, "Registro encontrado con éxito.");
                } catch (Exception ecp){
                    JOptionPane.showMessageDialog(null, "ERROR en la búsqueda del registro.");
                    ecp.printStackTrace();
                }
            }
        });
        cargarOpcionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String sql1 = "SELECT * FROM disponibilidad";
                String sql2 = "SELECT * FROM proveedor";
                try {
                    con = conectar.getConexion();
                    ps = con.prepareStatement(sql1);
                    rs = ps.executeQuery();
                    disponibilidadcomboBox.removeAllItems();
                    rs.next();
                    disponibilidadcomboBox.addItem(rs.getString(1));
                    rs.next();
                    disponibilidadcomboBox.addItem(rs.getString(1));
                    rs.next();
                    disponibilidadcomboBox.addItem(rs.getString(1));

                    ps = con.prepareStatement(sql2);
                    rs = ps.executeQuery();
                    proveedorcomboBox.removeAllItems();
                    rs.next();
                    proveedorcomboBox.addItem(rs.getString(1));
                    rs.next();
                    proveedorcomboBox.addItem(rs.getString(1));

                    con.close();
                    ps.close();
                    rs.close();
                } catch (Exception ecp){
                    ecp.printStackTrace();
                }
            }
        });
        borrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql = "DELETE FROM productos WHERE idp=" + idtextField.getText();
                try {
                    con = conectar.getConexion();
                    ps = con.prepareStatement(sql);
                    ps.executeUpdate();
                    con.close();
                    ps.close();
                    JOptionPane.showMessageDialog(null, "Registro borrado con éxito.");
                } catch (Exception ecp){
                    JOptionPane.showMessageDialog(null, "ERROR en borrado de registro.");
                    ecp.printStackTrace();
                }
            }
        });
        actualizarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sql = "UPDATE productos SET nombrep=?, preciop=?,disponibilidadp=?,proveedorp=? WHERE idp=?";
                try {
                    con = conectar.getConexion();
                    ps = con.prepareStatement(sql);
                    //ps.setInt(1, Integer.parseInt(idtextField.getText()));
                    ps.setString(1, nombretextField.getText());
                    ps.setDouble(2, Double.parseDouble(preciotextField.getText()));
                    ps.setString(3, (String) disponibilidadcomboBox.getSelectedItem());
                    ps.setString(4,(String)proveedorcomboBox.getSelectedItem());
                    ps.setInt(5, Integer.parseInt(idtextField.getText()));
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Registro actualizado con éxito.");
                } catch (Exception ecp){
                    JOptionPane.showMessageDialog(null,"ERROR en la actualización del registro.");
                    ecp.printStackTrace();
                }
            }
        });
    }

}
