/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Revenda;
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
public class RevendaDAO {

    private Connection conexao;

    public RevendaDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    //metodo que inclui um novo pedido para revenda
    public void novaRevenda(Revenda revenda) throws SQLException {
        String sql = "insert into revendas(revendata,revencli,revenvaltotal,revenobs,revensituacao) values(?,?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(revenda.getRevenData().getTime()));
            ps.setString(2, revenda.getRevenCli());
            ps.setDouble(3, revenda.getRevenValorTotal());
            ps.setString(4, revenda.getRevenObs());
            ps.setBoolean(5, revenda.isRevenSituacao());
            ps.execute();
        } catch (SQLException er) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            ps.close();
        }
    }//metodo que inclui um novo pedido para revenda

    //metodo que carrega o pedido
    public Revenda carregaPedido(int codigo) throws SQLException {
        String sql = "select revencodigo, revendata, revencli, revenvaltotal, revenobs, revensituacao from revendas where revencodigo=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Revenda revenda = new Revenda();

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();

            while (rs.next()) {
                revenda.setRevenCodigo(rs.getInt(1));
                revenda.setRevenData(rs.getDate(2));
                revenda.setRevenCli(rs.getString(3));
                revenda.setRevenValorTotal(rs.getDouble(4));
                revenda.setRevenObs(rs.getString(5));
                revenda.setRevenSituacao(rs.getBoolean(6));
            }
        } catch (SQLException er) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            rs.close();
            ps.close();
        }

        return revenda;
    }//fim do metodo que carrega o pedido

    //metodo que altera o pedido feito para revenda
    public void alteraPedido(Revenda revendas) throws SQLException {
        String sql = "update revendas set revendata=?, revencli=?, revenvaltotal=?, revenobs=?, revensituacao=? where revencodigo=?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(revendas.getRevenData().getTime()));
            ps.setString(2, revendas.getRevenCli());
            ps.setDouble(3, revendas.getRevenValorTotal());
            ps.setString(4, revendas.getRevenObs());
            ps.setBoolean(5, revendas.isRevenSituacao());
            ps.setInt(6, revendas.getRevenCodigo());
            ps.execute();
        } catch (SQLException er) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            ps.close();
        }
    }//fim do metodo que altera o pedido feito para revenda

    //metodo que retorna o total de registros
    public String totalRegistros() throws SQLException {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        String sqlConta = "select count(*) as contaRegistros from revendas";
        try {
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }//fim do metodo que retorna o total de registros

    //metodo que retorna o total de registros pendentes
    public String totalRegistrosPendentes() throws SQLException {
        PreparedStatement psContaPendente = null;
        ResultSet rsContaPendente = null;
        try {
            String sqlContaPendentes = "select count(*) AS contaPendentes from revendas where revensituacao is null or revensituacao=false";
            psContaPendente = conexao.prepareStatement(sqlContaPendentes);
            rsContaPendente = psContaPendente.executeQuery();
            rsContaPendente.next();
            String totalPendentes = rsContaPendente.getString("contaPendentes");
            return totalPendentes;
        } catch (SQLException ex) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            psContaPendente.close();
            rsContaPendente.close();
        }
        return null;
    }//fim do metodo que retorna o total de registros pendentes

    //metodo que retorna a lista de pedidos para revenda
    public List getListaRevenda() throws SQLException {
        String sql = "select * from revendas where revensituacao is null";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Revenda> listaRevendas = new ArrayList<Revenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Revenda revenda = new Revenda();
                revenda.setRevenCodigo(rs.getInt("revencodigo"));
                revenda.setRevenData(rs.getDate("revendata"));
                revenda.setRevenCli(rs.getString("revencli"));
                revenda.setRevenValorTotal(rs.getDouble("revenvaltotal"));
                revenda.setRevenObs(rs.getString("revenobs"));
                revenda.setRevenSituacao(rs.getBoolean("revensituacao"));
                listaRevendas.add(revenda);
            }
            return listaRevendas;
        } catch (SQLException er) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }//fim do metodo que retorna a lista de pedidos para revenda

    //metodo que retorna a lista de pedidos para revenda paginada
    public List getListaRevendaPaginada(int pagina, String ordenacao, String pesquisa, String campoapesquisar) throws SQLException {
        int limite = 8;
        int offset = (pagina * limite) - limite;

        //String sql = "select * from vendas where vensituacao is null or vensituacao = false order by vencodigo limit 8 offset " + offset;
        String sql = "select * from revendas where " + campoapesquisar + " like '%" + pesquisa + "%' order by " + ordenacao + " limit 8 offset " + offset;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Revenda> listaRevendas = new ArrayList<Revenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Revenda revenda = new Revenda();
                revenda.setRevenCodigo(rs.getInt("revencodigo"));
                revenda.setRevenData(rs.getDate("revendata"));
                revenda.setRevenCli(rs.getString("revencli"));
                revenda.setRevenValorTotal(rs.getDouble("revenvaltotal"));
                revenda.setRevenObs(rs.getString("revenobs"));
                revenda.setRevenSituacao(rs.getBoolean("revensituacao"));
                listaRevendas.add(revenda);
            }
            return listaRevendas;
        } catch (SQLException er) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }//fim do metodo que retorna a lista de pedidos para revenda paginada

    //metodo que retorna a lista de pedidos pendentes para revenda paginada
    public List getListaRevendaPendentePaginada(int pagina, String ordenacao) throws SQLException {

        int limite = 8;

        int offset = (pagina * limite) - limite;

        String sql = "select * from revendas where revensituacao is null or revensituacao=false order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
        //String sql = "select * from vendas where "+campoapesquisar+" like '%"+pesquisa+"%' order by "+ordenacao+" limit 8 offset " + offset;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Revenda> listaRevendasPendente = new ArrayList<Revenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Revenda revenda = new Revenda();
                revenda.setRevenCodigo(rs.getInt("revencodigo"));
                revenda.setRevenData(rs.getDate("revendata"));
                revenda.setRevenCli(rs.getString("revencli"));
                revenda.setRevenValorTotal(rs.getDouble("revenvaltotal"));
                revenda.setRevenObs(rs.getString("revenobs"));
                revenda.setRevenSituacao(rs.getBoolean("revensituacao"));
                listaRevendasPendente.add(revenda);
            }
            return listaRevendasPendente;
        } catch (SQLException er) {
            Logger.getLogger(RevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }//fim do metodo que retorna a lista de pedidos pendentes para revenda paginada
}
