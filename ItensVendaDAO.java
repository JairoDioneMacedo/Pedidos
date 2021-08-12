/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
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
public class ItensVendaDAO {

    private Connection conexao;

    public ItensVendaDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    public void novoItemVenda(ItensVenda itensVenda) throws SQLException {
        String sql = "insert into itensvenda(itenscodvenda,itenscodprod,itensquant,itenstotalvenda) values(?,?,?,?)";
        PreparedStatement ps = null;

        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, itensVenda.getItensCodVenda());
            ps.setInt(2, itensVenda.getItensCodProd());
            ps.setInt(3, itensVenda.getItensQuant());
            ps.setDouble(4, itensVenda.getItensTotalVenda());
            ps.execute();
        } catch (SQLException er) {
            Logger.getLogger(ItensVendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            ps.close();
        }
    }

    //metodo que retorna a lista de itens da venda
    public List<ItensVenda> getItensVenda(int itensCodVenda) throws SQLException {
        String sql = "select i.*, p.proddescricao, p.prodvalor from itensvenda i inner join produtos p on p.prodcodigo = i.itenscodprod where itenscodvenda = " + itensCodVenda;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItensVenda> listaItensVenda = new ArrayList<ItensVenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItensVenda itensVenda = new ItensVenda();
                itensVenda.setItensCodVenda(rs.getInt("itenscodvenda"));
                itensVenda.setItensCodProd(rs.getInt("itenscodprod"));
                itensVenda.setProdDescricao(rs.getString("proddescricao"));
                itensVenda.setItensQuant(rs.getInt("itensquant"));
                itensVenda.setProdValor(rs.getDouble("prodvalor"));
                itensVenda.setItensTotalVenda(rs.getDouble("itenstotalvenda"));
                listaItensVenda.add(itensVenda);
            }
        } catch (SQLException er) {
            Logger.getLogger(ItensVendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            rs.close();
            ps.close();
        }
        return listaItensVenda;
    }

    //metodo que retorna a lista de itens da venda
    public List getListaItensVenda(int itensCodVenda) throws SQLException {
        String sql = "select * from itensvenda, produtos where itenscodvenda = " + itensCodVenda + " and itensvenda.itenscodprod = produtos.prodcodigo";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItensVenda> listaItensVenda = new ArrayList<ItensVenda>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItensVenda itensVenda = new ItensVenda();
                itensVenda.setItensCodVenda(rs.getInt("itenscodvenda"));
                itensVenda.setItensCodProd(rs.getInt("itenscodprod"));
                itensVenda.setProdDescricao(rs.getString("proddescricao"));
                itensVenda.setItensQuant(rs.getInt("itensquant"));
                itensVenda.setProdValor(rs.getDouble("prodvalor"));
                itensVenda.setItensTotalVenda(rs.getDouble("itenstotalvenda"));
                listaItensVenda.add(itensVenda);
            }
            return listaItensVenda;
        } catch (SQLException er) {
            Logger.getLogger(ItensVendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo que retorna a lista de itens da venda pendentes
    public List getListaItensVendaPendentes() throws SQLException {
        String sqli = "select * from itensvenda";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<ItensVenda> listaItensVendaPendentes = new ArrayList<ItensVenda>();
        try {
            ps = conexao.prepareStatement(sqli);
            rs = ps.executeQuery();
            while (rs.next()) {
                ItensVenda itensVenda = new ItensVenda();
                itensVenda.setItensCodigo(rs.getInt("itenscodigo"));
                itensVenda.setItensCodVenda(rs.getInt("itenscodvenda"));
                itensVenda.setItensCodProd(rs.getInt("itenscodprod"));
                itensVenda.setItensQuant(rs.getInt("itensquant"));
                itensVenda.setItensTotalVenda(rs.getDouble("itenstotalvenda"));
                listaItensVendaPendentes.add(itensVenda);
            }
            //System.out.println(listaItensVendaPendentes);
            return listaItensVendaPendentes;
        } catch (SQLException er) {
            Logger.getLogger(ItensVendaDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }
}
