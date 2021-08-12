/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jairo.controle;

import br.com.jairo.dao.UsuarioDAO;
import br.com.jairo.modelo.Usuarios;
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
public class UsuariosCRUD extends HttpServlet {

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

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String nomeCompleto = request.getParameter("nomecompleto");
        String endereco = request.getParameter("endereco");
        String fone = request.getParameter("fone");
        String nivel = request.getParameter("nivel");

        Usuarios usuarios = new Usuarios();
        usuarios.setUsuario(usuario);
        usuarios.setSenha(senha);
        usuarios.setNomeCompleto(nomeCompleto);
        usuarios.setEndereco(endereco);
        usuarios.setFone(fone);
        if(nivel != null)
            usuarios.setNivel(Integer.parseInt(nivel));

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        String acao = request.getParameter("acao");

        if (acao == null) {
            acao = "listarUsuario";
        }

        if (acao.equals("Alterar")) {
            usuarioDAO.alteraUsuario(usuarios);
            rd = request.getRequestDispatcher("/UsuariosCRUD?acao=listarUsuario");
        } else if (acao.equals("excluir")) {
            usuarioDAO.excluiUsuario(usuarios);
            rd = request.getRequestDispatcher("/UsuariosCRUD?acao=listarUsuario");
        } else if (acao.equals("listarUsuario")) {
            int numPagina = 1;

            if (request.getParameter("numpagina") != null) {
                numPagina = Integer.parseInt(request.getParameter("numpagina"));
            }
            try {
                String ordenacao = request.getParameter("ordenacao");
                if(ordenacao == null)
                    ordenacao = "usuario";

                String pesquisa = request.getParameter("pesquisa");
                if(pesquisa == null)
                    pesquisa = "";

                String campoapesquisar = request.getParameter("campoapesquisar");
                if(campoapesquisar == null)
                    campoapesquisar = "usuario";

                List listaUsuarios = usuarioDAO.getListaUsuarioPaginado(numPagina,ordenacao, pesquisa,campoapesquisar);
                String totalRegistros = usuarioDAO.totalRegistros(pesquisa,campoapesquisar);
                request.setAttribute("sessaoListaUsuario", listaUsuarios);
                request.setAttribute("sessaoQtdTotalUsuarios", totalRegistros);
                rd = request.getRequestDispatcher("/usuarios.jsp");
            } catch (SQLException e) {
                Logger.getLogger(UsuariosCRUD.class.getName()).log(Level.SEVERE, null, e);
            }
        }else if (acao.equals("Gravar")) {
            usuarioDAO.novoUsuario(usuarios);
            rd = request.getRequestDispatcher("/UsuariosCRUD?acao=listarUsuario");
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
            Logger.getLogger(UsuariosCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(UsuariosCRUD.class.getName()).log(Level.SEVERE, null, ex);
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
