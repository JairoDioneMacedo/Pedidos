/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.VendaDAO;
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
public class PedidosCRUD extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int numPagina = 1;
        if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }

        VendaDAO vendaDAO = new VendaDAO();
        try{
            String ordenacao = request.getParameter("ordenacao");
            if(ordenacao == null)
                ordenacao = "vencodigo";

            String pesquisa = request.getParameter("pesquisa");
            if(pesquisa == null)
                pesquisa = "";

            String campoapesquisar = request.getParameter("campoapesquisar");
            if(campoapesquisar == null)
                campoapesquisar = "vencli";

            List listaVenda = vendaDAO.getListaVendaPaginada(numPagina, ordenacao, pesquisa, campoapesquisar);
            String totalRegistros = vendaDAO.totalRegistros();
            request.setAttribute("sessaoListaVenda", listaVenda);
            request.setAttribute("sessaoQtdTotalPedidos", totalRegistros);
            RequestDispatcher rd = request.getRequestDispatcher("/listapedidos.jsp");
            rd.forward(request, response);
        }catch(SQLException er){
            Logger.getLogger(PedidosCRUD.class.getName()).log(Level.SEVERE, null, er);
        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
