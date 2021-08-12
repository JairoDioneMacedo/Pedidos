/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Revendendores;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jairo
 */
public class RevendendorDAO {

    private Connection conexao;

    public RevendendorDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }
    
        //metodo que retorna a lista de revendendores paginado
    public List getListaRevendedorPaginado(int pagina, String ordenacao, String pesquisa, String campoapesquisar) throws SQLException {

        int limite = 8;
        int offset = (pagina * limite) - limite;

        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Revendendores> listaRevendendores = new ArrayList<Revendendores>();
        try {
            if (campoapesquisar.equals("revcodigo")) {
                if (pesquisa.equals("")) {
                    sql = "select * from revendendores where " + campoapesquisar + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
                } else {
                    sql = "select * from revendendores where " + campoapesquisar + " = " + pesquisa + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
                }
            } else {
                sql = "select * from revendendores where " + campoapesquisar + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Revendendores revendendores = new Revendendores();
                revendendores.setRevCodigo(rs.getInt("revcodigo"));
                revendendores.setRevNome(rs.getString("revnome"));
                revendendores.setRevNomeCompleto(rs.getString("revnomecompleto"));
                revendendores.setRevCPF(rs.getString("revcpf"));
                revendendores.setRevFone(rs.getString("revfone"));
                revendendores.setRevEndereco(rs.getString("revendereco"));
                listaRevendendores.add(revendendores);
            }
            return listaRevendendores;
        } catch (SQLException er) {
            Logger.getLogger(RevendendorDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo para excluir revendendor
    public boolean excluiRevendendor(Revendendores revendendores) throws SQLException {
        String sql = "delete from revendendores where revcodigo=?";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, revendendores.getRevCodigo());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RevendendorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
        return false;
    }

    //metodo para alterar revendendor
    public void alteraRevendendor(Revendendores revendendores) throws SQLException {
        String sql = "update revendendores set revnome=?,revnomecompleto=?,revcpf=?,revfone=?,revendereco=? where revcodigo=?";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, revendendores.getRevNome());
            ps.setString(2, revendendores.getRevNomeCompleto());
            ps.setString(3, revendendores.getRevCPF());
            ps.setString(4, revendendores.getRevFone());
            ps.setString(5, revendendores.getRevEndereco());
            ps.setInt(6, revendendores.getRevCodigo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RevendendorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
    }

    //metodo para incluir revendendor
    public void novoRevendendor(Revendendores revendendores) throws SQLException {
        String sql = "insert into revendendores (revnome, revnomecompleto, revcpf, revfone, revendereco)values (?,?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, revendendores.getRevNome());
            ps.setString(2, revendendores.getRevNomeCompleto());
            ps.setString(3, revendendores.getRevCPF());
            ps.setString(4, revendendores.getRevFone());
            ps.setString(5, revendendores.getRevEndereco());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(RevendendorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
    }

    //metodo que retorna o total de registros
    public String totalRegistros(String pesquisa, String campoapesquisar) throws SQLException {
        String sqlConta = "";
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        try {
            if (campoapesquisar.equals("revcodigo")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from revendendores where " + campoapesquisar + " > 0 ";
                } else {
                    sqlConta = "select count(*) as contaRegistros from revendendores where " + campoapesquisar + " = " + pesquisa;
                }
            } else {
                sqlConta = "select count(*) as contaRegistros from revendendores where " + campoapesquisar + " like '%" + pesquisa + "%'";
            }
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(RevendendorDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }

    //lista revendendores cadastrados no sistema
    public List getListaRevendedorCombo() throws SQLException {
        String sql = "select * from revendendores order by revnome";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Revendendores> listaRevendendores = new ArrayList<Revendendores>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Revendendores revendendores = new Revendendores();
                revendendores.setRevCodigo(rs.getInt("revcodigo"));
                revendendores.setRevNome(rs.getString("revnome"));
                listaRevendendores.add(revendendores);
            }
            return listaRevendendores;
        } catch (SQLException erro) {
            Logger.getLogger(RevendendorDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }
}
