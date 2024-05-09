/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ServicoProduto;
import Connection.ConnectorMySQL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
/**
 *
 * @author Marcos
 */
public class ServicoProdutoDAO {
    
    public void creat(ServicoProduto c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("INSERT INTO erp_beer.produtosservicos (quantidade,preço,SubTotal,descrição,id) VALUES (?, ?, ?, ?, ?)");
            
            stmt.setString(4,c.getDescrição());
            stmt.setInt(5,c.getId());
            stmt.setInt(1,c.getQuantidade());
            stmt.setFloat(2,c.getPreço());
            stmt.setFloat(3,c.getSubTotal());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar conta:  "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public List<ServicoProduto> read(){
    
        Connection con = ConnectorMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ServicoProduto> servicoprodutos = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM erp_beer.produtosservicos");
            rs = stmt.executeQuery();
            
            while(rs.next()){
            
                ServicoProduto servico = new ServicoProduto();
                
                servico.setDescrição(rs.getString("descrição"));
                servico.setId(rs.getInt("id"));
                servico.setPreço(rs.getFloat("preço"));
                servico.setQuantidade(rs.getInt("quantidade"));
                servico.setSubTotal(rs.getFloat("Subtotal"));
                
                
                
                
                servicoprodutos.add(servico);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler contas: "+ex);
        }finally{
            ConnectorMySQL.closeConnection(con, stmt, rs);        
            
        }
        
        return servicoprodutos;
        
    
    }
    
    public void update(ServicoProduto c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("UPDATE erp_beer.produtosservicos SET quantidade = ?, descrição = ?, preço = ?,"
                    + "SubTotal = ?, WHERE id = ?");
            stmt.setInt(1, c.getQuantidade());
            stmt.setString(2, c.getDescrição());
            stmt.setFloat(3, c.getPreço());
            stmt.setFloat(4, c.getSubTotal());
            stmt.setInt(5, c.getId());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atalizar conta: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public void delete(ServicoProduto c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("DELETE FROM erp_beer.produtosservicos WHERE id = ?");
            stmt.setInt(1 ,c.getId());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar nota: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
}
