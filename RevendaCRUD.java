/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ItensRevendaDAO;
import br.com.jairo.dao.RevendaDAO;
import br.com.jairo.modelo.ItensRevenda;
import br.com.jairo.modelo.Revenda;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class RevendaCRUD extends HttpServlet {

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
            throws ServletException, IOException, ParseException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        RequestDispatcher rd = null;

        String revenCodigo = request.getParameter("revencodigo");
        String revenData = request.getParameter("revendata");
        String revenCli = request.getParameter("selectRevendendor");
        //String venTotal = request.getParameter("venvaltotal");
        String revenObs = request.getParameter("revenobs");
        String revenSituacao = request.getParameter("revensituacao");

        Revenda revenda = new Revenda();

        if (revenCodigo != null) {
            revenda.setRevenCodigo(Integer.parseInt(revenCodigo));
        }
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (revenData != null) {
            Date revenDataFormatada = formatoData.parse(revenData);
            revenda.setRevenData(revenDataFormatada);
        }
        revenda.setRevenCli(revenCli);
        //if(venTotal != null)
        //  venda.setVenValTotal(Double.parseDouble(venTotal));
        revenda.setRevenObs(revenObs);
        if(revenSituacao != null){
            revenda.setRevenSituacao(true);
        }
        

        RevendaDAO revendaDAO = new RevendaDAO();

        String codRevenda = request.getParameter("itenscodrevenda");
        String selectProduto = request.getParameter("selectProduto");
        String itensQuantRevenda = request.getParameter("itensquantrevenda");
        String itensTotalRevenda = request.getParameter("itenstotalrevenda");

        ItensRevenda itensRevenda = new ItensRevenda();
        ItensRevendaDAO itensRevendaDAO = new ItensRevendaDAO();

        if (codRevenda != null) {
            itensRevenda.setCodRevenda(Integer.parseInt(codRevenda));
        }
        if (selectProduto != null) {
            itensRevenda.setItensCodProd(Integer.parseInt(selectProduto));
        }
        if (itensQuantRevenda != null) {
            itensRevenda.setItensQuantRevenda(Integer.parseInt(itensQuantRevenda));
        }

        String pesquisa = "";
        String campoapesquisar = "";

        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listarRevenda";
        } else if (acao.equals("Escolher Produto")) {
            revendaDAO.novaRevenda(revenda);
            request.setAttribute("sessaoUltimoRegistroRevenda", revendaDAO.totalRegistros());
            rd = request.getRequestDispatcher("/revenda.jsp");
        } else if (acao.equals("Incluir Produto")) {
            itensRevendaDAO.novoItemRevenda(itensRevenda);
            List listaItensRevenda = itensRevendaDAO.getListaItensRevenda(Integer.parseInt(codRevenda));
            request.setAttribute("sessaolistaItensRevenda", listaItensRevenda);
            //request.setAttribute("sessaoUltimoRegistroVenda", vendaDAO.totalRegistros(pesquisa, campoapesquisar));
            rd = request.getRequestDispatcher("/revenda.jsp");
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
        } catch (ParseException ex) {
            Logger.getLogger(RevendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RevendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
        } catch (ParseException ex) {
            Logger.getLogger(RevendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RevendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
