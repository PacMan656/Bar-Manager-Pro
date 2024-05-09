/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt.LoteDistDFeInt.DocZip;
import static gerenciador.AcoesNfe.ConsultarNFeSefaz.iniciaConfigurações;
import gerenciador.Modulos.NotasFiscais;

import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import model.bean.Fiscal;
import model.dao.FiscalDAO;
import nfe.Nfe;
import nfe.dom.ConfiguracoesIniciaisNfe;
import nfe.dom.Enum.StatusEnum;
import nfe.exception.NfeException;
import nfe.util.ConstantesUtil;
import nfe.util.XmlUtil;
/**
 *
 * @author Marcos
 */
public class DownloadNfeDistDfe {
    //Download Nfe Por NSU(Número Sequencial Único de NF-e.) e Chave
    
    public static String chave;
    public static String cnpj;
    
    public void chave(String chave1){
    
        chave = chave1;
        
        distnfe();
    }
    
    public static void distnfe() {

        try {
            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesIniciaisNfe config = iniciaConfigurações(); 

           // RetDistDFeInt retorno = consultaNsu();
			RetDistDFeInt retorno = consultaChave();

            System.out.println("Status:" + retorno.getCStat());
            System.out.println("Motivo:" + retorno.getXMotivo());
            System.out.println("NSU:" + retorno.getUltNSU());
            System.out.println("Max NSU:" + retorno.getMaxNSU());
            NotasFiscais.Retornonfe.insert("\nStatus:" + retorno.getCStat() +"\n"
                    + "Motivo:" + retorno.getXMotivo() +"\n"
                    + "NSU:" + retorno.getUltNSU() +"\n"
                    + "Max NSU:" + retorno.getMaxNSU(),
                    NotasFiscais.Retornonfe.getCaretPosition());

            if(StatusEnum.DOC_LOCALIZADO_PARA_DESTINATARIO.getCodigo().equals(retorno.getCStat())){

                List<DocZip> listaDoc = retorno.getLoteDistDFeInt().getDocZip();

                System.out.println("Encontrado " + listaDoc.size() + " Notas.");
                NotasFiscais.Retornonfe.insert("\nEncontrado " + listaDoc.size() + " Notas.", NotasFiscais.Retornonfe.getCaretPosition());
                
                for (DocZip docZip : listaDoc) {
                    System.out.println("Schema: " + docZip.getSchema());
                    System.out.println("NSU:" + docZip.getNSU());
                    System.out.println("XML: " + XmlUtil.gZipToXml(docZip.getValue()));
                    NotasFiscais.Retornonfe.insert("\nSchema: " + docZip.getSchema()+"\n"
                            + "NSU:" + docZip.getNSU()+"\n"
                            + "XML: " + XmlUtil.gZipToXml(docZip.getValue())
                            ,NotasFiscais.Retornonfe.getCaretPosition());
                }
            }

        } catch (NfeException | IOException | CertificadoException e) {
            System.out.println("Erro:" + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro:" + e.getMessage());
        }

    }

    public static RetDistDFeInt consultaNsu() throws NfeException {

        String cnpj = "XXX";
        String nsu = "000000000000000";

        return Nfe.distribuicaoDfe(ConstantesUtil.TIPOS.CNPJ, cnpj , ConstantesUtil.TIPOS.NSU , nsu);

    }
    public static RetDistDFeInt consultaChave() throws NfeException {

       
         FiscalDAO fdao = new FiscalDAO();
         for(Fiscal f: fdao.read()){
         cnpj = f.getCNPJ();
          
         }
       cnpj = cnpj.replaceAll("[^0-9]", "");
       // String chave = "XXX";

        return Nfe.distribuicaoDfe(ConstantesUtil.TIPOS.CNPJ, cnpj , ConstantesUtil.TIPOS.CHAVE , chave);

    }
    
}
