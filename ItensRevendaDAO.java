/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.ItensRevenda;
import br.com.jairo.modelo.ItensVenda;
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
public class ItensRevendaDAO {

    private Connection conexao;

    public ItensRevendaDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }
    
    public void novoItemRevenda(ItensRevenda itensRevenda) throws SQLException {
        String sql = "insert into itensrevenda(codrevenda,itenscodprod,itensquantrevenda,itenstotalrevenda) values(?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, itensRevenda.getCodRevenda());
            ps.setInt(2, itensRevenda.getItensCodProd());
            ps.setInt(3, itensRevenda.getItensQuantRevenda());
            ps.setDouble(4, itensRevenda.getItensTotalRevenda());
            ps.execute();
        } catch (SQLException er) {
            Logger.getLogger(ItensRevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            ps.close();
        }
    }

    //metodo que retorna a lista de itens da revenda
    public List<ItensRevenda> getItensRevenda(int codRevenda) throws SQLException {
        String sql = "select i.*, p.proddescricao, p.prodvalor from itensrevenda i inner join produtos p on p.prodcodigo = i.itenscodprod where codrevenda = " + codRevenda;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItensRevenda> listaItensRevenda = new ArrayList<ItensRevenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItensRevenda itensRevenda = new ItensRevenda();
                itensRevenda.setCodRevenda(rs.getInt("codrevenda"));
                itensRevenda.setItensCodProd(rs.getInt("itenscodprod"));
                itensRevenda.setProdDescricao(rs.getString("proddescricao"));
                itensRevenda.setItensQuantRevenda(rs.getInt("itensquantrevenda"));
                itensRevenda.setProdValor(rs.getDouble("prodvalor"));
                itensRevenda.setItensTotalRevenda(rs.getDouble("itenstotalrevenda"));
                listaItensRevenda.add(itensRevenda);
            }
        } catch (SQLException er) {
            Logger.getLogger(ItensRevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            rs.close();
            ps.close();
        }
        return listaItensRevenda;
    }

    //metodo que retorna a lista de itens da venda
    public List getListaItensRevenda(int codRevenda) throws SQLException {
        String sql = "select * from itensrevenda, produtos where codrevenda = " + codRevenda + " and itensrevenda.itenscodprod = produtos.prodcodigo";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItensRevenda> listaItensRevenda = new ArrayList<ItensRevenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItensRevenda itensRevenda = new ItensRevenda();
                itensRevenda.setCodRevenda(rs.getInt("codrevenda"));
                itensRevenda.setItensCodProd(rs.getInt("itenscodprod"));
                itensRevenda.setProdDescricao(rs.getString("proddescricao"));
                itensRevenda.setItensQuantRevenda(rs.getInt("itensquantrevenda"));
                itensRevenda.setProdValor(rs.getDouble("prodvalor"));
                itensRevenda.setItensTotalRevenda(rs.getDouble("itenstotalrevenda"));
                listaItensRevenda.add(itensRevenda);
            }
            return listaItensRevenda;
        } catch (SQLException er) {
            Logger.getLogger(ItensRevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna a lista de itens da venda pendentes
    public List getListaItensRevendaPendentes() throws SQLException {
        String sqli = "select * from itensrevenda";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItensRevenda> listaItensRevendaPendentes = new ArrayList<ItensRevenda>();
        try {
            ps = conexao.prepareStatement(sqli);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItensRevenda itensRevenda = new ItensRevenda();
                itensRevenda.setItensCodRevenda(rs.getInt("itenscodrevenda"));
                itensRevenda.setCodRevenda(rs.getInt("codrevenda"));
                itensRevenda.setItensCodProd(rs.getInt("itenscodprod"));
                itensRevenda.setItensQuantRevenda(rs.getInt("itensquantrevenda"));
                itensRevenda.setItensTotalRevenda(rs.getDouble("itenstotalrevenda"));
                listaItensRevendaPendentes.add(itensRevenda);
            }
            //System.out.println(listaItensVendaPendentes);
            return listaItensRevendaPendentes;
        } catch (SQLException er) {
            Logger.getLogger(ItensRevendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }
}
