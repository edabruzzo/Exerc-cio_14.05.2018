<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" 
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Home Page</title>
    </head>
    <body style="background-color: activeborder">


        <div style="background: darkgray; height: 100px; padding: 5px;">
            <div style="float: left">
                <h1>SISTEMA CONTROLE ESTOQUE</h1>

            </div>
            <div style="float: right; padding: 10px; text-align: center;">

                <p> <h4>Data e hora : <%
                    java.util.Date dNow = new java.util.Date();
                    SimpleDateFormat ft
                            = new SimpleDateFormat("E dd/MM/yyyy 'às' k:mm:ss");
                    //https://www.tutorialspoint.com/jsp/jsp_handling_date.htm
                    out.print(ft.format(dNow));
                    %></p></h4>


            </div>

        </div>


        <div style="padding: 5px;">
    <h4 style="color:red">${errorString}</h4>
        

            <form method="POST" action="${pageContext.request.contextPath}/produtos">
                <input type="hidden"  name="tipoAcao" value="acessar"/>
                <input type="hidden"  name="tipoOperacao" value="consultar"/>
                <input type="submit" value="ACESSAR O SISTEMA"/>
            </form>
            <h4 style="color:red">ATENÇÃO: Alterar USER e PASSWORD da conexão ao banco de dados
                na classe 'util.FabricaConexao' de acordo com os parâmetros do seu banco. 
                Projeto roda em MYSQL.
            </h4>
        </div> 

        <form method="POST" action="${pageContext.request.contextPath}/produtos">     
            <input type="hidden"  name="tipoAcao" value="criarInfra"/>
            <input type="submit" value="CRIAR INFRAESTRUTURA"/>       
        </form>
        <form method="POST" action="${pageContext.request.contextPath}/produtos">
            <input type="hidden"  name="tipoAcao" value="deletarInfra"/>
            <input type="submit" value="DELETAR TODA INFRAESTRUTURA CRIADA"/>       
        </form>
        <strong>
            <h3>Home Page</h3>
            <br>PROJETO DESENVOLVIDO PARA A MATÉRIA PROGRAMAÇÃO ORIENTADA A OBJETOS.<br>
            <b>Propósito do projeto:</b>
            <ul>
                <li>Gerenciamento de estoque</li>
                <li>Cadastro de Produto</li>
                <li>Atualização do Produto</li>
                <li>Remoção do Produto</li>
                <li>Listagem de Produtos Cadastrados</li>
            </ul>
        </strong>
        <strong><text>
            O botão criar infraestrutura irá criar o banco de dados de 
            nome 'controleEstoqueUninove' com  a seguinte tabela 'tb_produto'.
            Inicie o serviço MYSQL e, após, clique no botão 'CRIAR INFRAESTRUTURA'.
            
            Para deletar a infraestrutura criada, clique no botão 'DELETAR TODA INFRAESTRUTURA CRIADA' .
            </text></strong>


        <div style="background: darkgray; text-align: left; padding: 0.5px; margin-top: 1px;">
            <h3>Trabalho elaborado pelos alunos <strong style="color:red">Edwin, Emmanuel e Welington</strong> para a 
                disciplina Programação Orientada a Objetos - <strong style="color:red">Prof. Gerson Risso </strong>
            </h3>
        </div>

    </body>
</html>