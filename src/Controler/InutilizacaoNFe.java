/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;
import Nfe.ConfiguracoesIniciaisNfe;
import static Nfe.ConsultarNFeSefaz.iniciaConfigurações;
import Nfe.Nfe;
import Nfe.NfeException;
import Util.ConstantesUtil;
import Util.XmlUtil;
import View.NotasFiscais;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TProcInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 *
 * @author Marcos
 */
public class InutilizacaoNFe {
    public static String id;
    public static String motivo;
    
    public void recebechave(String id1, String motivo1) throws CertificadoException, br.com.swconsultoria.nfe.exception.NfeException, NfeException, FileNotFoundException{
    
    id = id1;
    motivo = motivo1;
        try {
            inutiliza();
        } catch (JAXBException ex) {
            Logger.getLogger(InutilizacaoNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void inutiliza() throws JAXBException, br.com.swconsultoria.nfe.exception.NfeException, NfeException, FileNotFoundException {

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
            sb.append("\nStatus:").append(infRetorno.getCStat());
            sb.append("\nMotivo:").append(infRetorno.getXMotivo());
            sb.append("\nData:").append(infRetorno.getDhRecbto());

            // Criação do ProcInutNfe
            TProcInutNFe procInutNFe = new TProcInutNFe();
            procInutNFe.setInutNFe(Nfe.criaObjetoInutilizacao(id, motivo, ConstantesUtil.NFE));
            procInutNFe.setRetInutNFe(retorno);
            procInutNFe.setVersao(ConstantesUtil.VERSAO.INUTILIZACAO);

            System.out.println(XmlUtil.objectToXml(procInutNFe));
            sb.append("\n").append(XmlUtil.objectToXml(procInutNFe));
            //JOptionPane.showMessageDialog(null, sb.toString());
            NotasFiscais.RetornoInutilização.setText(sb.toString());
            
        } catch (CertificadoException | NfeException e) {
            System.err.println(e.getMessage());
        }

    }
    
}

