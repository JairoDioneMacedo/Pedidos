/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Venda;
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
public class VendaDAO {

    private Connection conexao;

    public VendaDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    //metodo que inclui um novo pedido feito pelo usuario
    public void novaVenda(Venda venda) throws SQLException {
        String sql = "insert into vendas(vendata,vencli,venvaltotal) values(?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(venda.getVenData().getTime()));
            ps.setString(2, venda.getVenCli());
            ps.setDouble(3, venda.getVenValTotal());
            ps.execute();
        } catch (SQLException er) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            ps.close();
        }
    }

    //metodo que carrega o pedido
    public Venda carregaPedido(int codigo) throws SQLException {
        String sql = "select vencodigo, vendata, vencli, venvaltotal, venobs, vensituacao from vendas where vencodigo=?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        Venda venda = new Venda();

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, codigo);
            rs = ps.executeQuery();

            while (rs.next()) {
                venda.setVenCodigo(rs.getInt(1));
                venda.setVenData(rs.getDate(2));
                venda.setVenCli(rs.getString(3));
                venda.setVenValTotal(rs.getDouble(4));
                venda.setVenObs(rs.getString(5));
                venda.setVenSituacao(rs.getBoolean(6));
            }
        } catch (SQLException er) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            rs.close();
            ps.close();
        }

        return venda;
    }

    //metodo que altera o pedido feito pelo usuario
    public void alteraPedido(Venda vendas) throws SQLException {
        String sql = "update vendas set vendata=?, vencli=?, venvaltotal=?, venobs=?, vensituacao=? where vencodigo=?";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setDate(1, new java.sql.Date(vendas.getVenData().getTime()));
            ps.setString(2, vendas.getVenCli());
            ps.setDouble(3, vendas.getVenValTotal());
            ps.setString(4, vendas.getVenObs());
            ps.setBoolean(5, vendas.getVenSituacao());
            ps.setInt(6, vendas.getVenCodigo());
            ps.execute();
        } catch (SQLException er) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            ps.close();
        }
    }

    //metodo que retorna o total de registros
    public String totalRegistros() throws SQLException {
        PreparedStatement psConta = null;
        ResultSet rsConta = null;
        String sqlConta = "select count(*) as contaRegistros from vendas";
        try {
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }

    //metodo que retorna o total de registros pendentes
    public String totalRegistrosPendentes() throws SQLException {
        PreparedStatement psContaPendente = null;
        ResultSet rsContaPendente = null;
        try {
            String sqlContaPendentes = "select count(*) AS contaPendentes from vendas where vensituacao is null or vensituacao=false";
            psContaPendente = conexao.prepareStatement(sqlContaPendentes);
            rsContaPendente = psContaPendente.executeQuery();
            rsContaPendente.next();
            String totalPendentes = rsContaPendente.getString("contaPendentes");
            return totalPendentes;
        } catch (SQLException ex) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            psContaPendente.close();
            rsContaPendente.close();
        }
        return null;
    }

    //metodo que retorna a lista de pedidos
    public List getListaVenda() throws SQLException {
        String sql = "select * from vendas where vensituacao is null";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Venda> listaVendas = new ArrayList<Venda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setVenCodigo(rs.getInt("vencodigo"));
                venda.setVenData(rs.getDate("vendata"));
                venda.setVenCli(rs.getString("vencli"));
                venda.setVenValTotal(rs.getDouble("venvaltotal"));
                venda.setVenObs(rs.getString("venobs"));
                venda.setVenSituacao(rs.getBoolean("vensituacao"));
                listaVendas.add(venda);
            }
            return listaVendas;
        } catch (SQLException er) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna a lista de pedidos paginada
    public List getListaVendaPaginada(int pagina, String ordenacao, String pesquisa, String campoapesquisar) throws SQLException {
        int limite = 8;
        int offset = (pagina * limite) - limite;

        //String sql = "select * from vendas where vensituacao is null or vensituacao = false order by vencodigo limit 8 offset " + offset;
        String sql = "select * from vendas where " + campoapesquisar + " like '%" + pesquisa + "%' order by " + ordenacao + " limit 8 offset " + offset;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Venda> listaVendas = new ArrayList<Venda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setVenCodigo(rs.getInt("vencodigo"));
                venda.setVenData(rs.getDate("vendata"));
                venda.setVenCli(rs.getString("vencli"));
                venda.setVenValTotal(rs.getDouble("venvaltotal"));
                venda.setVenObs(rs.getString("venobs"));
                venda.setVenSituacao(rs.getBoolean("vensituacao"));
                listaVendas.add(venda);
            }
            return listaVendas;
        } catch (SQLException er) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna a lista de pedidos pendentes paginada
    public List getListaVendaPendentePaginada(int pagina, String ordenacao) throws SQLException {

        int limite = 8;

        int offset = (pagina * limite) - limite;

        String sql = "select * from vendas where vensituacao is null or vensituacao=false order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
        //String sql = "select * from vendas where "+campoapesquisar+" like '%"+pesquisa+"%' order by "+ordenacao+" limit 8 offset " + offset;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Venda> listaVendasPendente = new ArrayList<Venda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venda venda = new Venda();
                venda.setVenCodigo(rs.getInt("vencodigo"));
                venda.setVenData(rs.getDate("vendata"));
                venda.setVenCli(rs.getString("vencli"));
                venda.setVenValTotal(rs.getDouble("venvaltotal"));
                venda.setVenObs(rs.getString("venobs"));
                venda.setVenSituacao(rs.getBoolean("vensituacao"));
                listaVendasPendente.add(venda);
            }
            return listaVendasPendente;
        } catch (SQLException er) {
            Logger.getLogger(VendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }
}
