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
        <title>JSP Page</title>
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


        
       <h3>Remover Produto</h3>
       

              
    <p style="color: red;">${errorString}</p>
 
      <c:if test="${not empty produto}">
         <form method="POST" action="${pageContext.request.contextPath}/produtos">
            <input type="hidden" name="codigo" value="${produto.codigo}" />
            <table border="0"> 
               <tr>
                  <td>Código</td>
                  <td style="color:red;">${produto.codigo}</td>
               </tr>
               <tr>
                  <td>Nome do Produto</td>
                  <td><input type="text" name="nomeProduto" value="${produto.nomeProduto}" /></td>
               </tr>
               <tr>
                  <td>Preço do Produto</td>
                  <td><input type="text" name="precoProduto" value="${produto.precoProduto}" /></td>
               </tr>
               <tr>
                  <td>Quantidade em Estoque</td>
                  <td><input type="text" name="quantidadeEstoque" value="${produto.quantidadeEstoque}" /></td>
               </tr>
                <tr>
                 <td colspan = "2">
                <h4><p style="color: red;">Tem certeza que deseja deletar este produto da base de dados?</p></h4>
                 <input type="hidden" name="codigo" value="${produto.codigo}" />
                <input type="hidden" name="tipoOperacao" value="deletarProduto" />
                      <input type="submit" value="DELETAR" />
                      <a href="${pageContext.request.contextPath}/produtos">Cancelar</a>
                   </td>
               </tr>
            </table>
         </form>
</c:if>
   
        
        

<div style="background: darkgray; text-align: left; padding: 0.5px; margin-top: 1px;">
   <h3>Trabalho elaborado pelos alunos <strong style="color:red">Edwin, Emmanuel e Welington</strong> para a 
        disciplina Programação Orientada a Objetos - <strong style="color:red">Prof. Gerson Risso </strong>
  </h3>
</div>
</body>
</html>
