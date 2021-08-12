/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.RevendendorDAO;
import br.com.jairo.modelo.Revendendores;
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
public class RevendendoresCRUD extends HttpServlet {

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

        String revCodigo = request.getParameter("revcodigo");
        String revNome = request.getParameter("revnome");
        String revNomeCompleto = request.getParameter("revnomecompleto");
        String revCpf = request.getParameter("revcpf");
        String revFone = request.getParameter("revfone");
        String revEndereco = request.getParameter("revendereco");

        Revendendores revendendores = new Revendendores();
        if (revCodigo != null) {
            revendendores.setRevCodigo(Integer.parseInt(revCodigo));
        }
        revendendores.setRevNome(revNome);
        revendendores.setRevNomeCompleto(revNomeCompleto);
        revendendores.setRevCPF(revCpf);
        revendendores.setRevFone(revFone);
        revendendores.setRevEndereco(revEndereco);

        RevendendorDAO revendendorDAO = new RevendendorDAO();

        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarRevendendor";
        }

        if (acao.equals("Alterar")) {
            revendendorDAO.alteraRevendendor(revendendores);
            rd = request.getRequestDispatcher("/RevendendoresCRUD?acao=listarRevendendor");
        } else if (acao.equals("excluir")) {
            revendendorDAO.excluiRevendendor(revendendores);
            rd = request.getRequestDispatcher("/RevendendoresCRUD?acao=listarRevendendor");
        } else if (acao.equals("listarRevendendor")) {
            int numPagina = 1;

            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {
                String ordenacao = request.getParameter("ordenacao");
                if (ordenacao == null) {
                    ordenacao = "revcodigo";
                }

                String pesquisa = request.getParameter("pesquisa");
                if (pesquisa == null) {
                    pesquisa = "";
                }

                String campoapesquisar = request.getParameter("campoapesquisar");
                if (campoapesquisar == null) {
                    campoapesquisar = "revcodigo";
                }

                List listaRevendendores = revendendorDAO.getListaRevendedorPaginado(numPagina, ordenacao, pesquisa, campoapesquisar);
                String totalRegistros = revendendorDAO.totalRegistros(pesquisa, campoapesquisar);
                request.setAttribute("sessaoListaRevendendor", listaRevendendores);
                request.setAttribute("sessaoQtdTotalRegistros", totalRegistros);
                rd = request.getRequestDispatcher("/revendendor.jsp");
            } catch (SQLException e) {
                Logger.getLogger(RevendendoresCRUD.class.getName()).log(Level.SEVERE, null, e);
            }
        } else if (acao.equals("Gravar")) {
            revendendorDAO.novoRevendendor(revendendores);
            rd = request.getRequestDispatcher("/RevendendoresCRUD?acao=listarRevendendor");
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
            Logger.getLogger(RevendendoresCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(RevendendoresCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
