/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.dao;

import br.com.jairo.fabrica.FabricaConexao;
import br.com.jairo.modelo.Produtos;
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
public class ProdutoDAO {

    private Connection conexao;

    public ProdutoDAO() {
        this.conexao = new FabricaConexao().getConnection();
    }

    //metodo que retorna a lista de produtos paginado
    public List getListaProdutoPaginado(int pagina, String ordenacao, String pesquisa, String campoapesquisar) throws SQLException {

        int limite = 8;
        int offset = (pagina * limite) - limite;

        String sql = "";
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Produtos> listaProdutos = new ArrayList<Produtos>();
        try {
            if (campoapesquisar.equals("prodcodigo")) {
                if (pesquisa.equals("")) {
                    sql = "select * from produtos where " + campoapesquisar + " > 0 order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
                } else {
                    sql = "select * from produtos where " + campoapesquisar + " = " + pesquisa + " order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
                }
            } else {
                sql = "select * from produtos where " + campoapesquisar + " like '%" + pesquisa + "%' order by " + ordenacao + " LIMIT 8 OFFSET " + offset;
            }
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Produtos produtos = new Produtos();
                produtos.setProdCodigo(rs.getInt("prodcodigo"));
                produtos.setProdDescricao(rs.getString("proddescricao"));
                produtos.setProdValor(rs.getDouble("prodvalor"));
                listaProdutos.add(produtos);
            }
            return listaProdutos;
        } catch (SQLException er) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, er);
        } finally {
            //conexao.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }

    //metodo para excluir produto
    public boolean excluiProduto(Produtos produto) throws SQLException {
        String sql = "delete from produtos where prodcodigo=?";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setInt(1, produto.getProdCodigo());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
        return false;
    }

    //metodo para alterar produto
    public void alteraProduto(Produtos produto) throws SQLException {
        String sql = "update produtos set proddescricao=?,prodvalor=? where prodcodigo=?";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, produto.getProdDescricao());
            ps.setDouble(2, produto.getProdValor());
            ps.setInt(3, produto.getProdCodigo());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            ps.close();
        }
    }

    //metodo para incluir produto
    public void novoProduto(Produtos produto) throws SQLException {
        String sql = "insert into produtos (proddescricao, prodvalor)values (?,?)";
        PreparedStatement ps = null;
        try {
            ps = conexao.prepareStatement(sql);
            ps.setString(1, produto.getProdDescricao());
            ps.setDouble(2, produto.getProdValor());
            ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
            if (campoapesquisar.equals("prodcodigo")) {
                if (pesquisa.equals("")) {
                    sqlConta = "select count(*) as contaRegistros from produtos where " + campoapesquisar + " > 0 ";
                } else {
                    sqlConta = "select count(*) as contaRegistros from produtos where " + campoapesquisar + " = " + pesquisa;
                }
            } else {
                sqlConta = "select count(*) as contaRegistros from produtos where " + campoapesquisar + " like '%" + pesquisa + "%'";
            }
            psConta = conexao.prepareStatement(sqlConta);
            rsConta = psConta.executeQuery();
            rsConta.next();
            String qtdTotalRegistros = rsConta.getString("contaRegistros");
            return qtdTotalRegistros;
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            conexao.close();
            psConta.close();
            rsConta.close();
        }
        return null;
    }

    //lista produtos cadastrados no sistema
    public List getListaProdutoCombo() throws SQLException {
        String sql = "select * from produtos order by proddescricao";

        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Produtos> listaProdutos = new ArrayList<Produtos>();
        try {
            ps = conexao.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Produtos produtos = new Produtos();
                produtos.setProdCodigo(rs.getInt("prodCodigo"));
                produtos.setProdDescricao(rs.getString("prodDescricao"));
                listaProdutos.add(produtos);
            }
            return listaProdutos;
        } catch (SQLException erro) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, erro);
        } finally {
            //connection.close();
            //ps.close();
            //rs.close();
        }
        return null;
    }
}
