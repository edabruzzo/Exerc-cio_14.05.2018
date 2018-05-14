<%-- 
    Document   : atualizar
    Created on : 14/05/2018, 09:21:37
    Author     : internet
--%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  

<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SISTEMA CONTROLE ESTOQUE</title>
    </head>
    <body>
    
        
   <div style="background: darkgray; height: 100px; padding: 5px;">
  <div style="float: left">
    <h1>SISTEMA CONTROLE ESTOQUE</h1>
   
  </div>
   <div style="float: right; padding: 10px; text-align: center;">
   
   <p> <h4>Data e hora : <%
         java.util.Date dNow = new java.util.Date( );
         SimpleDateFormat ft = 
         new SimpleDateFormat ("E dd/MM/yyyy 'às' k:mm:ss");
         //https://www.tutorialspoint.com/jsp/jsp_handling_date.htm
         out.print(ft.format(dNow));
      %></p></h4>
     
 
  </div>
 
</div>
  <form method="POST" action="${pageContext.request.contextPath}/produtos">
                <input type="hidden"  name="tipoAcao" value="home"/>  
                <input type="submit" value="Voltar à página inicial"/>
  </form>
      
                  
         <h3>BUSCA DE PRODUTO POR NOME</h3>           
         <form method="POST" action="${pageContext.request.contextPath}/produtos">
        
            <table border="0"> 
               <tr>
                  <td>Nome do Produto</td>
                  <td><input type="text" name="nomePesquisado" /></td>
               </tr>
                <tr>
                 <td colspan = "2">
                     <input type="hidden" name="tipoOperacao" value="buscarProduto">
                      <input type="submit" value="Buscar" />
                  </td>
               </tr>
            </table>
         </form>
       
      
      
      
      
        <h3>LISTA PRODUTOS CADASTRADOS</h3>
        <h4><a href="${pageContext.request.contextPath}/produtos?tipoOperacao=criar">Cadastrar Produto</a></h4>
            <p style="color: red;">${errorString}</p>

        
        <table border="1" cellpadding="5" cellspacing="1" >
            <tr>
                <th>Código do Produto</th>
                <th>Nome do Produto</th>
                <th>Preço do Produto</th>
                <th>Quantidade em Estoque</th>
                <th>Editar</th>
                <th>Deletar</th>
            </tr>

            <c:forEach items="${listaProdutos}" var="produto" >
                <tr>
                    <td>${produto.codigo}</td>
                    <td>${produto.nomeProduto}</td>
                    <td>${produto.precoProduto}</td>
                    <td>${produto.quantidadeEstoque}</td>
                    <td>
                        <a href="${pageContext.request.contextPath}/produtos?codigo=${produto.codigo}&tipoOperacao=editar">Editar</a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/produtos?codigo=${produto.codigo}&tipoOperacao=deletar">Deletar</a>
                    </td>
                </tr>
            </c:forEach>
        </table>

        
        
        
        
        
        
        
        
        

<div style="background: darkgray; text-align: left; padding: 0.5px; margin-top: 1px;">
   <h3>Trabalho elaborado pelos alunos <strong style="color:red">Edwin, Emmanuel e Welington</strong> para a 
        disciplina Programação Orientada a Objetos - <strong style="color:red">Prof. Gerson Risso </strong>
  </h3>
</div>
</body>
</html>
