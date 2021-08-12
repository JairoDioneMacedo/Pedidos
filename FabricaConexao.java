/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.fabrica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Jairo
 */
public class FabricaConexao {

    String driver = "com.mysql.jdbc.Driver";//"org.postgresql.Driver"
    private String url = "jdbc:mysql://localhost/pvcxeqrf_balbino";//"jdbc:mysql://localhost/pvcxeqrf_balbino";"jdbc:postgresql://localhost/pedidos";
    private String usuario = "root";//"postgres";"pvcxeqrf_lbmn";"root";
    private String senha = "";//"2344524246";"71186853Balbino";"";

    public Connection getConnection() {
        try {
            Class.forName(driver);
            //System.out.println("logou");
            return DriverManager.getConnection(url, usuario, senha);
        } catch (SQLException er) {
            throw new RuntimeException(er);
        } catch (ClassNotFoundException er) {
            throw new RuntimeException(er);
        }
    }
}
