/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Produto;

/**
 *
 * @author internet
 */
public class ProdutoDAO {
    
    
   private String URL;
    
    private String USER = "root";
    private String PASSWORD = "root";



    public void cadastrarProduto(Produto produto) throws ClassNotFoundException, SQLException {

        String sql = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque) "
                + "VALUES ('" + produto.getNomeProduto()
                + "', " + produto.getPrecoProduto()
                + ", " + produto.getQuantidadeEstoque()
                + ");";

        this.executaQuerieUpdate(sql);

    }

    public void editarProduto(Produto produto) throws ClassNotFoundException, SQLException {

        String sql1 = "UPDATE tb_produto "
                + "SET nomeProduto = '" + produto.getNomeProduto()
                + "', precoProduto =  " + produto.getPrecoProduto()
                + ", quantidadeEstoque = " + produto.getQuantidadeEstoque()
                + " WHERE codigo = " + produto.getCodigo() + ";";

        this.executaQuerieUpdate(sql1);
    }


    public void removerProduto(int codigo) throws SQLException, ClassNotFoundException {

        String sql = "DELETE FROM tb_produto WHERE codigo = "
                + codigo + ";";

        this.executaQuerieUpdate(sql);
    }

    public List<Produto> consultaProdutos() throws ClassNotFoundException, SQLException {

        String sql = "SELECT * FROM tb_produto;";
    
        ResultSet rs = this.executaQuerieResultSet(sql);

        return extraiListaProdutos(rs);

    }

 

   

    public Produto findByCodigo(Integer codigo) throws ClassNotFoundException, SQLException {

        
        String sql = "SELECT * FROM tb_produto WHERE codigo = "
                + codigo + ";";

        ResultSet rs = null;
        try {
            rs = this.executaQuerieResultSet(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        Produto produto = this.extraiProdutoResultSet(rs);
        rs.close();

        return produto;

    }

    
    
    
    public Produto extraiProdutoResultSet(ResultSet rs) throws SQLException, ClassNotFoundException {

        Produto produto = new Produto();
        
        while(rs.next()){
            
        produto.setCodigo(rs.getInt("codigo"));
        produto.setNomeProduto(rs.getString("nomeProduto"));
        produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
        produto.setPrecoProduto(rs.getDouble("precoProduto"));
            
            
        }    
        
        rs.close();

        return produto;
    }

    private List<Produto> extraiListaProdutos(ResultSet rs) throws SQLException {

            List<Produto> listaProdutos = new ArrayList();

        while (rs.next()) {
           
        Produto produto = new Produto();
           
        produto.setCodigo(rs.getInt("codigo"));
        produto.setNomeProduto(rs.getString("nomeProduto"));
        produto.setQuantidadeEstoque(rs.getInt("quantidadeEstoque"));
        produto.setPrecoProduto(rs.getDouble("precoProduto"));
            
           
        listaProdutos.add(produto);
                        
        }
        
        rs.close();

        return listaProdutos;
        

    }

    public List<Produto> consultaProdutosByNome(String nome) throws ClassNotFoundException, SQLException {
    
    
      String sql = "SELECT * FROM tb_produto WHERE nomeProduto LIKE '"+nome+"%';";
    
        ResultSet rs = this.executaQuerieResultSet(sql);

        return extraiListaProdutos(rs);
    
    
    
    }


    

    public void criaInfraestrutura() throws SQLException, ClassNotFoundException {

 
        ArrayList<String> listaSQLs = new ArrayList();

        String sql1 = "CREATE TABLE IF NOT EXISTS tb_produto ("
                + "codigo INTEGER AUTO_INCREMENT NOT NULL, "
                + "nomeProduto  VARCHAR(255) NOT NULL, "
                + "precoProduto DOUBLE NOT NULL, "
                + "quantidadeEstoque INT NOT NULL, "
                + "PRIMARY KEY (codigo));";
        listaSQLs.add(sql1);


        String sql2 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('Mouse DELL', 20, 100);";

        listaSQLs.add(sql2);
        
         String sql10 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('Mouse LENOVO', 25, 147);";

        listaSQLs.add(sql10);



        String sql3 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('TECLADO DELL', 30, 200);";

        listaSQLs.add(sql3);
        
        String sql4 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('MONITOR DELL', 500, 300);";

        listaSQLs.add(sql4);
        
          String sql7 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('MONITOR LENOVO', 500, 280);";

        listaSQLs.add(sql7);
        
        
             String sql5 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('Carregador LENOVO', 80, 150);";

        listaSQLs.add(sql5);

             String sql6 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('Maleta Notebook', 200, 180);";

        listaSQLs.add(sql6);
        
            String sql8 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('Teclado LG', 25, 175);";

        listaSQLs.add(sql8);


        String sql9 = "INSERT INTO tb_produto(nomeProduto, precoProduto, quantidadeEstoque)"
                + "VALUES('Mouse Óptico LG', 45, 195);";

        listaSQLs.add(sql9);

        

        executaBatchUpdate(listaSQLs);

    }

    public void criaBaseDados() throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.jdbc.Driver");
       
        this.setURL("jdbc:mysql://localhost:3306/");

        Connection conn = null;
        Statement stmt = null;
        //STEP 3: Open a connection
        System.out.println("Conectando ao servidor com a seguinte URL : " + this.URL);

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            String sql = "CREATE DATABASE IF NOT EXISTS controleEstoqueUNINOVE";
            System.out.println(sql);
            stmt.execute(sql);
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            fecharConexao(conn);
            stmt.close();

        }

    }

    public Connection criaConexao() throws ClassNotFoundException  {
        
        this.setURL("jdbc:mysql://localhost:3306/controleEstoqueUNINOVE");
       
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        //STEP 3: Open a connection
        System.out.println("Conectando ao servidor com a seguinte URL : "+ this.URL);
       
        try {
         
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
       
        } catch (SQLException ex) {

            ex.printStackTrace();
            
        }

return conn;
        
        
    }

    public void fecharConexao(Connection conn) {

        System.out.println("Fechando a conexão com o banco de dados");
        try {
            if (conn != null) {
                
                conn.close();

                System.out.println("Conexão com o banco de dados fechada com sucesso para a "
                        + "seguinte conexão :" + this.URL);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("********ATENÇÃO ! Conexão com o banco de dados não foi fechada !");

        }
    }

    public ResultSet executaQuerieResultSet(String sql) throws ClassNotFoundException, SQLException {

        Connection conn = this.criaConexao();
        Statement stmt = conn.createStatement();

        ResultSet rs = null;
        try {
            System.out.println("Executando a seguinte query .....");
            System.out.println(sql);
            rs = stmt.executeQuery(sql);
            System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Query não executada!");
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            //rs.close(); NÃO POSSO FECHAR O RESULTSET AQUI
            //stmt.close();
            //fecharConexao(conn);

        }

        return rs;

    }

    public void executaQuerieSemResultado(String sql) throws ClassNotFoundException, SQLException {

            
        Connection conn = this.criaConexao();
        Statement stmt = conn.createStatement();

        try {
            System.out.println("Executando a seguinte query .....");
            System.out.println(sql);
            stmt.execute(sql);
            System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {

            System.out.println("Query não executada!");
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            stmt.close();
            fecharConexao(conn);

        }

    }

    public void executaQuerieUpdate(String sql) throws ClassNotFoundException, SQLException {

        
        Connection conn = this.criaConexao();
       
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);

        try {
            System.out.println("Executando commit da seguinte query .....");
            System.out.println(sql);
            stmt.executeUpdate(sql);
            conn.commit();
           System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {

            System.out.println("Query não executada! Efetuando rollback");
            conn.rollback();
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            stmt.close();
            fecharConexao(conn);

        }

    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public void executaBatchUpdate(ArrayList listaSQLs) throws SQLException, ClassNotFoundException {

        Connection conn = this.criaConexao();
        Statement stmt = conn.createStatement();
        conn.setAutoCommit(false);
        
        for (int i = 0; i < listaSQLs.size(); i++) {
            System.out.println(listaSQLs.get(i));
            stmt.addBatch((String) listaSQLs.get(i));

        }

        try {
           System.out.println("Executando query em lote dentro de uma transação .....");
            stmt.executeBatch();
            conn.commit();
           System.out.println("Executada com sucesso!");

        } catch (SQLException ex) {

           System.out.println("Query não executada! Efetuando rollback");
            conn.rollback();
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            stmt.close();
            fecharConexao(conn);
        }
    }

    
    

    public void deletaBanco() throws ClassNotFoundException, SQLException{
        
        Class.forName("com.mysql.jdbc.Driver");
        
        this.setURL("jdbc:mysql://localhost:3306/");

        Connection conn = null;
        Statement stmt = null;
        //STEP 3: Open a connection
        System.out.println("Conectando ao servidor com a seguinte URL : " + this.URL);

        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            stmt = conn.createStatement();
            conn.setAutoCommit(false);
            System.out.println("DELETANDO BASE DE DADOS controleEstoqueUNINOVE");
            stmt.execute("DROP DATABASE IF EXISTS controleEstoqueUNINOVE");
            System.out.println("BASE DE DADOS controleEstoqueUNINOVE DELETADA COM SUCESSO!");
            conn.commit();
        } catch (SQLException ex) {
            conn.rollback();
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            fecharConexao(conn);
            stmt.close();
            
           }
    }

}

