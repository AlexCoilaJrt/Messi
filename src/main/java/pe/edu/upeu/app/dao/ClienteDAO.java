/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.upeu.app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import pe.edu.upeu.app.dao.conx.Conn;
import pe.edu.upeu.app.modelo.ClienteTO;
import pe.edu.upeu.app.util.ErrorLogger;

public class ClienteDAO implements ClienteDaoI {

    Statement stmt = null;
    Vector columnNames;
    Vector visitdata;
    Connection connection = Conn.connectSQLite();
    static PreparedStatement ps;
    static ErrorLogger log = new ErrorLogger(ClienteDAO.class.getName());
    ResultSet rs = null;

    public ClienteDAO() {
        columnNames = new Vector();
        visitdata = new Vector();
    }

    @Override
    public int create(ClienteTO d) {
        int rsId = 0;
        String[] returns = {"dniruc"};
        String sql = "INSERT INTO cliente(doc_identidad, nombres, temp_contrato, nacionalidad, ultimo_club, fecha_fin) "
                + "VALUES(?,?,?,?,?,?)";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql, returns);
            ps.setString(++i, d.getDoc_identidad());
            ps.setString(++i, d.getNombres());
            ps.setString(++i, d.getTemp_contrato());
            ps.setString(++i, d.getNacionalidad());
            ps.setString(++i, d.getUltimo_club());
            ps.setString(++i, d.getFecha_fin());

            rsId = ps.executeUpdate();// 0 no o 1 si commit
            try ( ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    rsId = rs.getInt(1);
                }
                rs.close();
            }
        } catch (SQLException ex) {
//System.err.println("create:" + ex.toString());
            log.log(Level.SEVERE, "create", ex);
        }
        return rsId;
    }

    @Override
    public int update(ClienteTO d) {
        System.out.println("actualizar d.getDoc_identidad: " + d.getDoc_identidad());
        int comit = 0;
        String sql = "UPDATE cliente SET "
                + "nombres=?, "
                + "temp_contrato=?, "
                + "nacionalidad=?, "
                + "ultimo_club=? "
                + "fecha_fin=?, "
                + "WHERE doc_identidad=?";
        int i = 0;
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(++i, d.getNombres());// RS?
            ps.setString(++i, d.getTemp_contrato());
            ps.setString(++i, d.getNacionalidad());
            ps.setString(++i, d.getUltimo_club());
            ps.setString(++i, d.getFecha_fin());
            ps.setString(++i, d.getDoc_identidad());

            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "update", ex);
        }
        return comit;
    }

    @Override
    public int delete(String id) throws Exception {
        int comit = 0;
        String sql = "DELETE FROM cliente WHERE doc_identidad = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, id);
            comit = ps.executeUpdate();
        } catch (SQLException ex) {
            log.log(Level.SEVERE, "delete", ex);
            // System.err.println("NO del " + ex.toString());
            throw new Exception("Detalle:" + ex.getMessage());
        }
        return comit;
    }

    @Override
    public List<ClienteTO> listCmb(String filter) {

        List<ClienteTO> ls = new ArrayList();
        ls.add(new ClienteTO());
        ls.addAll(listarClientes());
        return ls;
    }

    @Override
    public List<ClienteTO> listarClientes() {
        List<ClienteTO> listarclientes = new ArrayList();
        String sql = "SELECT * FROM cliente";
        try {
            connection = new Conn().connectSQLite();
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ClienteTO cli = new ClienteTO();
                cli.setDoc_identidad(rs.getString("doc_identidad"));
                cli.setNombres(rs.getString("nombres"));
                cli.setTemp_contrato(rs.getString("temp_contrato"));
                cli.setNacionalidad(rs.getString("nacionalidad"));
                cli.setUltimo_club(rs.getString("ultimo_club"));
                cli.setFecha_fin(rs.getString("fecha_fin"));

                listarclientes.add(cli);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return listarclientes;
    }

    @Override
    public ClienteTO buscarClientes(String dni) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void reportarCliente() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
