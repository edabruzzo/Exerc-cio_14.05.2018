/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import DAO.ProdutoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.Produto;

/**
 *
 * @author internet
 */
@WebServlet(name = "ProdutoServletController", urlPatterns = {"/produtos"})
public class ProdutoServletController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    ProdutoDAO produtoDAO = new ProdutoDAO();
    String errorString =  null;
    String tipoOperacao = "";
    String acao = "";


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        this.tipoOperacao = (String) request.getParameter("tipoOperacao");
        this.acao = (String) request.getParameter("tipoAcao");

        if ("criarInfra".equals(acao)) criarInfra(request, response);
        if ("deletarInfra".equals(acao)) this.deletarInfra(request, response);
        
        if ("home".equals(acao)) {
            
            this.direcionaHome(request, response);
        
        return;
        }
        
        
        extraiListaProdutos(request,response);

        if ("atualizar".equals(this.tipoOperacao)) {

            //EXTRAI PARÂMETROS DO PRODUTO DA TELA E COLOCA O PRODUTO COMO ATRIBUTO DE SESSÃO
           Produto produto =  extraiProdutoParametrosTela(request);
           
            try {
                produtoDAO.editarProduto(produto);
            } catch (ClassNotFoundException ex) {
                
                Logger.getLogger(ProdutoServletController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                
                errorString = ex.getMessage();
                
                request.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/view/consultar.jsp");
                dispatcher.forward(request, response);
                Logger.getLogger(ProdutoServletController.class.getName()).log(Level.SEVERE, null, ex);
            return;
            }finally{

                response.sendRedirect(request.getContextPath() + "/produtos");
                return;
            }
        }
        if ("editar".equals(this.tipoOperacao)) {

                this.pegaProdutoByCodigo(request, response);
                
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/view/atualizar.jsp");
                dispatcher.forward(request, response);

            }else if ("deletar".equals(this.tipoOperacao)) {

                pegaProdutoByCodigo(request, response);
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/view/remover.jsp");
                dispatcher.forward(request, response);

            }else if("buscarProduto".equals(this.tipoOperacao)){
                
                
              this.extraiListaProdutos(request, response);
              
                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/view/exibir.jsp");
                dispatcher.forward(request, response);
                
            }else if ("deletarProduto".equals(this.tipoOperacao)) {

                String codigoString = (String) request.getParameter("codigo");

                int codigo = 0;
                codigo = Integer.parseInt(codigoString);

                errorString = null;

                try {
                    produtoDAO.removerProduto(codigo);
                } catch (SQLException e) {
                    e.printStackTrace();
                    errorString = e.getMessage();
                } catch (ClassNotFoundException ex) {

                }

                // If has an error, redirecte to the error page.
                if (errorString != null) {
                    // Store the information in the request attribute, before forward to views.
                    request.setAttribute("errorString", errorString);

                } else {
                    response.sendRedirect(request.getContextPath() + "/produtos");
                    return;
                }

            }else if ("criar".equals(this.tipoOperacao)) {

                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/WEB-INF/view/cadastrar.jsp");
                dispatcher.forward(request, response);
                return;

            }
            if ("criarProduto".equals(this.tipoOperacao)) {

                Produto produto = this.extraiProdutoParametrosTela(request);

                try {
                produtoDAO.cadastrarProduto(produto);
              response.sendRedirect(request.getContextPath() + "/produtos");
              return;

                } catch (SQLException e) {
                e.printStackTrace();
                    errorString = e.getMessage();
                 response.sendRedirect(request.getContextPath() + "/produtos");
                 return;
                } catch (ClassNotFoundException ex) {

                }
          
               
            }else{
                
            RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/WEB-INF/view/consultar.jsp");
            dispatcher.forward(request, response);
            return;
            }

        }
        
    

    

    private Produto extraiProdutoParametrosTela(HttpServletRequest request) {

        errorString = null;
        String code = (String) request.getParameter("codigo");
        String nomeProduto = (String) request.getParameter("nomeProduto");
        String priceProduto = (String) request.getParameter("precoProduto");
        String quantEstoque = (String) request.getParameter("quantidadeEstoque");

        int codigo = 0;
        double precoProduto = 0;
        int quantidadeEstoque = 0;

        if((!code.contains("") || code  !=  null)&& !"criarProduto".equals(this.tipoOperacao)){
            codigo = Integer.parseInt(code);
        }
        
        precoProduto = Double.parseDouble(priceProduto);
        quantidadeEstoque = Integer.parseInt(quantEstoque);

        Produto produto = new Produto();

        produto.setCodigo(codigo);
        produto.setNomeProduto(nomeProduto);
        produto.setPrecoProduto(precoProduto);
        produto.setQuantidadeEstoque(quantidadeEstoque);

           
            return produto; 
    }

    
    private void pegaProdutoByCodigo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String codigoString = (String) request.getParameter("codigo");
        int codigo = Integer.parseInt(codigoString);
        Produto produto = null;
        errorString = null;

        try {
            produto = produtoDAO.findByCodigo(codigo);

        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        } catch (ClassNotFoundException ex) {

        }

        // If no error.
        // The produto does not exist to edit.
        // Redirect to produtos page.
        if (errorString != null && produto == null) {
            
            
             RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            
            return;
        }

        // Store errorString in request attribute, before forward to views.
        request.setAttribute("errorString", errorString);
        request.setAttribute("produto", produto);

    }

    
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

    private void extraiListaProdutos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
            List<Produto> listaProdutos = null;
        try {
            
            if("buscarProduto".equals(this.tipoOperacao)){
          
             String nome = (String) request.getParameter("nomePesquisado");
             listaProdutos = produtoDAO.consultaProdutosByNome(nome);
            
            }else{
                
                listaProdutos = produtoDAO.consultaProdutos();
            }
                

        } catch (SQLException e) {
            e.printStackTrace();
            errorString = e.getMessage();
        RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                return;
    } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        // Store info in request attribute, before forward to views
        request.setAttribute("errorString", errorString);
        request.setAttribute("listaProdutos", listaProdutos);

    
    }

    private void criarInfra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

     
        try {
                this.produtoDAO.criaBaseDados();
                this.produtoDAO.criaInfraestrutura();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ProdutoServletController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                request.setAttribute("errorString", "PROBLEMA NA CRIAÇÃO DA BASE DE DADOS");

                RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
                return;

            }




    }
    


    private void deletarInfra(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
     
        try {
            errorString = null;
            this.produtoDAO.deletaBanco();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ProdutoServletController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
         errorString = "Erro ao deletar base de dados";
            Logger.getLogger(ProdutoServletController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            
            request.setAttribute("errorString", errorString);
               RequestDispatcher dispatcher = request.getServletContext()
                        .getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
            return;
            
        }
           
             
    }



    private void direcionaHome(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
      RequestDispatcher dispatcher = request.getServletContext()
                    .getRequestDispatcher("/index.jsp");
                dispatcher.forward(request, response);
           
    
    }

    
    
    
    
}




