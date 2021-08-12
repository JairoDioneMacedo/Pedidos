/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ItensVendaDAO;
import br.com.jairo.dao.VendaDAO;
import br.com.jairo.modelo.ItensVenda;
import br.com.jairo.modelo.Venda;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class VendaCRUD extends HttpServlet {

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
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        RequestDispatcher rd = null;

        String venCodigo = request.getParameter("vencodigo");
        String venData = request.getParameter("vendata");
        String venCli = request.getParameter("vencli");
        //String venTotal = request.getParameter("venvaltotal");

        Venda venda = new Venda();

        if (venCodigo != null) {
            venda.setVenCodigo(Integer.parseInt(venCodigo));
        }
        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (venData != null) {
            Date venDataFormatada = formatoData.parse(venData);
            venda.setVenData(venDataFormatada);
        }
        venda.setVenCli(venCli);
        //if(venTotal != null)
        //  venda.setVenValTotal(Double.parseDouble(venTotal));

        VendaDAO vendaDAO = new VendaDAO();

        String itensCodVenda = request.getParameter("vencodigo");
        String selectProduto = request.getParameter("selectProduto");
        String itensQuant = request.getParameter("itensquant");
        String itensTotalVenda = request.getParameter("itenstotalvenda");

        ItensVenda itensVenda = new ItensVenda();
        ItensVendaDAO itensVendaDAO = new ItensVendaDAO();

        if (itensCodVenda != null) {
            itensVenda.setItensCodVenda(Integer.parseInt(itensCodVenda));
        }
        if (selectProduto != null) {
            itensVenda.setItensCodProd(Integer.parseInt(selectProduto));
        }
        if (itensQuant != null) {
            itensVenda.setItensQuant(Integer.parseInt(itensQuant));
        }

        String pesquisa = "";
        String campoapesquisar = "";

        String acao = request.getParameter("acao");
        if (acao == null) {
            acao = "listarVenda";
        } else if (acao.equals("Escolher Produto")) {
            vendaDAO.novaVenda(venda);
            request.setAttribute("sessaoUltimoRegistroVenda", vendaDAO.totalRegistros());
            rd = request.getRequestDispatcher("/venda.jsp");
        } else if (acao.equals("Incluir Produto")) {
            itensVendaDAO.novoItemVenda(itensVenda);
            List listaItensVenda = itensVendaDAO.getListaItensVenda(Integer.parseInt(itensCodVenda));
            request.setAttribute("sessaolistaItensVenda", listaItensVenda);
            //request.setAttribute("sessaoUltimoRegistroVenda", vendaDAO.totalRegistros(pesquisa, campoapesquisar));
            rd = request.getRequestDispatcher("/venda.jsp");
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
            Logger.getLogger(VendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(VendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(VendaCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
