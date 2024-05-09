/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.NCMUntrib;
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
public class NCMUntribDAO {
    
      public List<NCMUntrib> LerNCMeUnTrib(String desc){
    
        Connection con = ConnectorMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<NCMUntrib> ncmuns= new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM erp_beer.UNTRIBUTAVEL where descricao like ?");
            stmt.setString(1, "%"+desc+"%");
            rs = stmt.executeQuery();
            
            while(rs.next()){
            
                NCMUntrib ncmun = new NCMUntrib();
                
                ncmun.setDescrição(rs.getString("descricao"));
                ncmun.setNcm(rs.getString("ncm"));
                ncmun.setUnTrib(rs.getString("untrib"));
                
                ncmuns.add(ncmun);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar Ncm: "+ex);
        }finally{
            ConnectorMySQL.closeConnection(con, stmt, rs);        
            
        }
        
        return ncmuns;
        
    
    }
    
}
