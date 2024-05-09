/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.Caixa;
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
public class CaixaDAO {
    
    public void creat(Caixa c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("INSERT INTO erp_beer.caixa (idCaixa, Operador, Senha, Loja,serieini) VALUES (?, ?, ?, ?,?)");
            stmt.setInt(1,c.getIdCaixa());
            stmt.setString(2,c.getOperador());
            stmt.setString(3,c.getSenha());
            stmt.setString(4,c.getLoja());
            stmt.setString(5,c.getSIni());
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao criar caixa:  "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public List<Caixa> read(){
    
        Connection con = ConnectorMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Caixa> caixas = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM erp_beer.caixa");
            rs = stmt.executeQuery();
            
            while(rs.next()){
            
                Caixa caixa = new Caixa();
                
                caixa.setIdCaixa(rs.getInt("idCaixa"));
                caixa.setOperador(rs.getString("Operador"));
                caixa.setLoja(rs.getString("Loja"));
                caixa.setSenha(rs.getString("Senha"));
                caixa.setImpressora(rs.getInt("Impressora"));
                caixa.setSIni(rs.getString("serieini"));
                caixa.setNomeImpressora(rs.getString("nomeimpressora"));
                caixa.setModeloImpressora(rs.getString("modeloimpressora"));
                caixa.setMarcaImpressora(rs.getString("marcaimpressora"));
                caixa.setPortaBalanca(rs.getString("portabalanca"));
                caixa.setFabricanteBalanca(rs.getString("fabricantebalanca"));
                caixa.setModeloBalanca(rs.getString("modelobalanca"));
                caixa.setComunicaçãoBalança(rs.getString("comunicacaobalanca"));
                
                
                caixas.add(caixa);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler caixa: "+ex);
        }finally{
            ConnectorMySQL.closeConnection(con, stmt, rs);        
            
        }
        
        return caixas;
        
    
    }
    
    public void update(Caixa c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("UPDATE erp_beer.caixa SET senha = ? WHERE idCaixa = ? and Loja = ?");
            stmt.setString(1, c.getSenha());
            stmt.setInt(2, c.getIdCaixa());
            stmt.setString(3, c.getLoja());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atalizar caixa: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public void delete(int id, String loja){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("DELETE FROM erp_beer.caixa WHERE idCaixa = ? AND Loja = ?");
            stmt.setInt(1,id);
            stmt.setString(2,loja);
            
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Deletado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao Deletar caixa: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public void atualizarImpressora(int c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("UPDATE erp_beer.caixa SET impressora = ? WHERE idCaixa = 1 and Loja = Sede");
            stmt.setInt(1, c);
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atalizar caixa: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
    public List<Caixa> readdesc(String loja, int id){
    
        Connection con = ConnectorMySQL.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Caixa> caixas = new ArrayList<>();
        try {
            stmt = con.prepareStatement("SELECT * FROM erp_beer.caixa where loja like ? and idcaixa = ?");
            stmt.setString(1,loja);
            stmt.setInt(2, id);
            rs = stmt.executeQuery();
            
            while(rs.next()){
            
               Caixa caixa = new Caixa();
                
                caixa.setIdCaixa(rs.getInt("idCaixa"));
                caixa.setOperador(rs.getString("Operador"));
                caixa.setLoja(rs.getString("Loja"));
                caixa.setSenha(rs.getString("Senha"));
                caixa.setImpressora(rs.getInt("Impressora"));
                caixa.setSIni(rs.getString("serieini"));
                
                caixas.add(caixa);
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao ler caixa: "+ex);
        }finally{
            ConnectorMySQL.closeConnection(con, stmt, rs);        
            
        }
        
        return caixas;
    
    }
    
    public void atualiza(Caixa c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
    
        try {
            stmt = con.prepareStatement("UPDATE erp_beer.caixa SET serieini = ?,senha = ?, operador = ?"
                    + " WHERE idCaixa = ? and Loja = ?");
            stmt.setString(1, c.getSIni());
            stmt.setString(2, c.getSenha());
            stmt.setString(3, c.getOperador());
            stmt.setInt(4, c.getIdCaixa());
            stmt.setString(5, c.getLoja());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atalizar caixa: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
    
     public void atualizaComponentes(Caixa c){
    
    Connection con = ConnectorMySQL.getConnection();
    PreparedStatement stmt = null;
    
        try {
            stmt = con.prepareStatement("UPDATE erp_beer.caixa SET nomeimpressora = ?,modeloimpressora = ?,"
                    + " marcaimpressora = ?, portabalanca = ?, fabricantebalanca = ?, modelobalanca = ?, comunicacaobalanca = ?"
                    + " WHERE idCaixa = ? and Loja = ?");
            stmt.setString(1, c.getNomeImpressora());
            stmt.setString(2, c.getModeloImpressora());
            stmt.setString(3, c.getMarcaImpressora());
            stmt.setString(4, c.getPortaBalanca());
            stmt.setString(5, c.getFabricanteBalanca());
            stmt.setString(6, c.getModeloBalanca());
            stmt.setString(7, c.getComunicaçãoBalança());
            stmt.setInt(8, c.getIdCaixa());
            stmt.setString(9, c.getLoja());
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
                    } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar caixa: "+ex);
            System.err.println("Erro ao atualizar caixa: "+ex);
        } finally{
        
        ConnectorMySQL.closeConnection(con, stmt);
        }
    
    }
     
    
}
