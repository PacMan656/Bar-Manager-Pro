/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TProcInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import static gerenciador.AcoesNfe.ConsultarNFeSefaz.iniciaConfigurações;
import gerenciador.Modulos.NotasFiscais;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;
import nfe.Nfe;
import nfe.dom.ConfiguracoesIniciaisNfe;
import nfe.exception.NfeException;
import nfe.util.ConstantesUtil;
import nfe.util.XmlUtil;
/**
 *
 * @author Marcos
 */
public class InutilizacaoNFe {
    public static String id;
    public static String motivo;
    
    public void recebechave(String id1, String motivo1) throws CertificadoException, br.com.swconsultoria.nfe.exception.NfeException{
    
    id = id1;
    motivo = motivo1;
        try {
            inutiliza();
        } catch (JAXBException ex) {
            Logger.getLogger(InutilizacaoNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void inutiliza() throws JAXBException, br.com.swconsultoria.nfe.exception.NfeException {

        StringBuilder sb = new StringBuilder();
        
        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesIniciaisNfe config = iniciaConfigurações();  

            //String id = "XXXXX";
            //String motivo = "XXXX";

            TRetInutNFe retorno = Nfe.inutilizacao(id, motivo, ConstantesUtil.NFE, true);
            TRetInutNFe.InfInut infRetorno = retorno.getInfInut();
            System.out.println("Status:" + infRetorno.getCStat());
            System.out.println("Motivo:" + infRetorno.getXMotivo());
            System.out.println("Data:" + infRetorno.getDhRecbto());
            sb.append("\nStatus:" + infRetorno.getCStat());
            sb.append("\nMotivo:" + infRetorno.getXMotivo());
            sb.append("\nData:" + infRetorno.getDhRecbto());

            // Criação do ProcInutNfe
            TProcInutNFe procInutNFe = new TProcInutNFe();
            procInutNFe.setInutNFe(Nfe.criaObjetoInutilizacao(id, motivo, ConstantesUtil.NFE));
            procInutNFe.setRetInutNFe(retorno);
            procInutNFe.setVersao(ConstantesUtil.VERSAO.INUTILIZACAO);

            System.out.println(XmlUtil.objectToXml(procInutNFe));
            sb.append("\n"+XmlUtil.objectToXml(procInutNFe));
            //JOptionPane.showMessageDialog(null, sb.toString());
            NotasFiscais.RetornoInutilização.setText(sb.toString());
            
        } catch (CertificadoException | NfeException e) {
            System.err.println(e.getMessage());
        }

    }
    
}

