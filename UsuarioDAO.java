/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Usuarios;
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
public class UsuarioDAO {

    private Connection conexao;

    public UsuarioDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    //metodo que verifica usuario
    public boolean verificaUsuario(Usuarios usuarios) {
        String sql = "select * from usuarios where usuario=? and senha=? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuarios.getUsuario());
            ps.setString(2, usuarios.getSenha());
            rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException er) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, er);
        }
        return false;
    }

    //metodo que retorna o usuario
    public Usuarios getUsuario(String usuario, String senha) throws SQLException {
        String sql = "select * from usuarios where usuario=? and senha=? ";
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, senha);
            rs = ps.executeQuery();
            if (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(usuario);
                usuarios.setSenha(senha);
                usuarios.setNomeCompleto(rs.getString("nomeCompleto"));
                usuarios.setEndereco(rs.getString("endereco"));
                usuarios.setFone(rs.getString("fone"));
                usuarios.setNivel(rs.getInt("nivel"));
                //System.out.println("metodo DAO");
                return usuarios;
            }
        } catch (SQLException er) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna a lista de usuarios
    public List getListaUsuario() throws SQLException {
        String sql = "select * from usuarios";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setSenha(rs.getString("senha"));
                usuarios.setNomeCompleto(rs.getString("nomeCompleto"));
                usuarios.setEndereco(rs.getString("endereco"));
                usuarios.setFone(rs.getString("fone"));
                usuarios.setNivel(rs.getInt("nivel"));
                listaUsuarios.add(usuarios);
            }
            return listaUsuarios;
        } catch (SQLException er) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna a lista de usuarios paginado
    public List getListaUsuarioPaginado(int pagina, String ordenacao, String pesquisa, String campoapesquisar) throws SQLException {

        int limite = 8;
        int offset = (pagina * limite) - limite;

        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Usuarios> listaUsuarios = new ArrayList<Usuarios>();
        try {
            if (campoapesquisar.equals("nivel")) {
                if (pesquisa.equals("")) {
                    sql = "select * from usuarios where " + campoapesquisar + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET" + offset;
                } else {
                    sql = "select * from usuarios where " + campoapesquisar + " = " + pesquisa + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
                }
            } else {
                sql = "select * from usuarios where " + campoapesquisar + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setUsuario(rs.getString("usuario"));
                usuarios.setSenha(rs.getString("senha"));
                usuarios.setNomeCompleto(rs.getString("nomeCompleto"));
                usuarios.setEndereco(rs.getString("endereco"));
                usuarios.setFone(rs.getString("fone"));
                usuarios.setNivel(rs.getInt("nivel"));
                listaUsuarios.add(usuarios);
            }
            return listaUsuarios;
        } catch (SQLException er) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna o total de registros
    public String totalRegistros(String pesquisa, String campoapesquisar) throws SQLException {
        String sqlConta = "";
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        try {
            if (campoapesquisar.equals("nivel")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) as contaRegistrosUsuarios from usuarios where " + campoapesquisar + " > 0 ";
                } else {
                    sqlConta = "select count(*) as contaRegistrosUsuarios from usuarios where " + campoapesquisar + " = " + pesquisa;
                }
            } else {
                sqlConta = "select count(*) as contaRegistrosUsuarios from usuarios where " + campoapesquisar + " like '%" + pesquisa + "%'";
            }
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistrosUsuarios");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            rsConta.close();
            psConta.close();
            conexao.close();
        }
        return null;
    }

    //metodo para excluir usuario
    public boolean excluiUsuario(Usuarios usuario) throws SQLException {
        String sql = "delete from usuarios where usuario=?";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getUsuario());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
        return false;
    }

    //metodo para alterar usuario
    public void alteraUsuario(Usuarios usuario) throws SQLException {
        String sql = "update usuarios set senha=?,nivel=?,nomecompleto=? where usuario=?";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getSenha());
            ps.setInt(2, usuario.getNivel());
            ps.setString(3, usuario.getNomeCompleto());
            ps.setString(4, usuario.getUsuario());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
    }

    //metodo para incluir usuario
    public void novoUsuario(Usuarios usuario) throws SQLException {
        String sql = "insert into usuarios values (?,?,?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, usuario.getUsuario());
            ps.setString(2, usuario.getSenha());
            ps.setInt(3, usuario.getNivel());
            ps.setString(4, usuario.getNomeCompleto());
            ps.setString(5, usuario.getEndereco());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
    }
}
