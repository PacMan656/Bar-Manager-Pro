/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.ADM;
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
public class AdmDAO {
    
    public void creat(ADM a){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("INSERT INTO erp_beer.adm (idUsuario,Senha) VALUES(?,?)");
            stmt.setString(1,a.getUsuario());
            stmt.setString(2,a.getSenha());
            
            
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public List<ADM> read(){
    
        Connection con = ConnectorMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<ADM> adms = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM erp_beer.adm");
            rs = stmt.executeQuery();
            
            while(rs.next()){
            
                ADM adm = new ADM();
                
                adm.setUsuario(rs.getString("idUsuario"));
                adm.setSenha(rs.getString("Senha"));
                
                
                adms.add(adm);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro: "+ex);
        }finally{
            ConnectorMySQL.closeConnection(con, stmt, rs);        
            
        }
        
        return adms;
        
    
    }
    
    public void update(ADM a){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("UPDATE erp_beer.adm SET Senha = ? WHERE idUsuario = ?");
            stmt.setString(1,a.getSenha());
            stmt.setString(2,a.getUsuario());
            
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atalizar: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public void delete(ADM a){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("DELETE FROM erp_beer.adm WHERE idUsuario = ? AND Senha = ?");
            stmt.setString(1,a.getUsuario());
            stmt.setString(2,a.getSenha());
            
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
}
