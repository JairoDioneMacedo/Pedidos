/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.ItensVendaDAO;
import br.com.jairo.dao.VendaDAO;
import br.com.jairo.modelo.Venda;
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
public class CarregaPedido extends HttpServlet {

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
        Venda venda = new Venda();

        String codigo = request.getParameter("vencodigo");
        /*
        //String venData = request.getParameter("vendata");
        //String cliente = request.getParameter("vencli");
        //String valorTotal = request.getParameter("venvaltotal");
        //String obs = request.getParameter("venobs");
        //String sit = request.getParameter("vensituacao");

        venda.setVenCodigo(Integer.parseInt(codigo));

        DateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
        if (venData != null) {
            Date dataFormatada = formatoData.parse(venData);
            venda.setVenData(dataFormatada);
        }

        venda.setVenCli(cliente);
        venda.setVenValTotal(Double.parseDouble(valorTotal));
        venda.setVenObs(obs);

        if (sit != null) {
            venda.setVenSituacao(true);
        }
        //venda.isVenSituacao();
*/
        VendaDAO vendaDAO = new VendaDAO();
        ItensVendaDAO itensVendaDAO = new ItensVendaDAO();
        try {
            venda = vendaDAO.carregaPedido(Integer.parseInt(codigo));
            List listaPedidosPendentes = itensVendaDAO.getItensVenda(Integer.parseInt(codigo));
            request.getSession().setAttribute("venda", venda);
            request.getSession().setAttribute("itensVenda", listaPedidosPendentes);
        } catch (SQLException ex) {
            Logger.getLogger(CarregaPedido.class.getName()).log(Level.SEVERE, null, ex);
        }

        RequestDispatcher rd = request.getRequestDispatcher("alterapedidos.jsp");
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
