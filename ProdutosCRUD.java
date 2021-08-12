/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ProdutoDAO;
import br.com.jairo.modelo.Produtos;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jairo
 */
public class ProdutosCRUD extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        RequestDispatcher rd = null;

        String prodCodigo = request.getParameter("prodcodigo");
        String prodDescricao = request.getParameter("proddescricao");
        String prodValor = request.getParameter("prodvalor");

        Produtos produtos = new Produtos();
        if (prodCodigo != null) {
            produtos.setProdCodigo(Integer.parseInt(prodCodigo));
        }
        produtos.setProdDescricao(prodDescricao);

        if (prodValor != null) {
            produtos.setProdValor(Double.parseDouble(prodValor));
        }

        ProdutoDAO produtoDAO = new ProdutoDAO();

        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarProduto";
        }

        if (acao.equals("Alterar")) {
            produtoDAO.alteraProduto(produtos);
            rd = request.getRequestDispatcher("/ProdutosCRUD?acao=listarProduto");
        } else if (acao.equals("excluir")) {
            produtoDAO.excluiProduto(produtos);
            rd = request.getRequestDispatcher("/ProdutosCRUD?acao=listarProduto");
        } else if (acao.equals("listarProduto")) {
            int numPagina = 1;

            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {
                String ordenacao = request.getParameter("ordenacao");
                if (ordenacao == null) {
                    ordenacao = "prodcodigo";
                }

                String pesquisa = request.getParameter("pesquisa");
                if (pesquisa == null) {
                    pesquisa = "";
                }

                String campoapesquisar = request.getParameter("campoapesquisar");
                if (campoapesquisar == null) {
                    campoapesquisar = "prodcodigo";
                }

                List listaProdutos = produtoDAO.getListaProdutoPaginado(numPagina, ordenacao, pesquisa, campoapesquisar);
                String totalRegistros = produtoDAO.totalRegistros(pesquisa, campoapesquisar);
                request.setAttribute("sessaoListaProduto", listaProdutos);
                request.setAttribute("sessaoQtdTotalRegistros", totalRegistros);
                rd = request.getRequestDispatcher("/produtos.jsp");
            } catch (SQLException e) {
                Logger.getLogger(ProdutosCRUD.class.getName()).log(Level.SEVERE, null, e);
            }
        } else if (acao.equals("Gravar")) {
            produtoDAO.novoProduto(produtos);
            rd = request.getRequestDispatcher("/ProdutosCRUD?acao=listarProduto");
        }
        rd.forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutosCRUD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
