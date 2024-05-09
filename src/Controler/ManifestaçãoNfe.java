/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.schema.envConfRecebto.TRetEnvEvento;
import static gerenciador.AcoesNfe.ConsultarNFeSefaz.iniciaConfigurações;
import gerenciador.Modulos.NotasFiscais;
import model.bean.Fiscal;
import model.dao.FiscalDAO;
import nfe.Nfe;
import nfe.dom.ConfiguracoesIniciaisNfe;
import nfe.dom.Enum.StatusEnum;
import nfe.dom.Enum.TipoManifestacao;
import nfe.exception.NfeException;
import nfe.util.XmlUtil;
/**
 *
 * @author Marcos
 */
public class ManifestaçãoNfe {
    
    public static String chave;
    public static String cnpj ;
    public static String tipo;
    
    public void recebechave(String chave1, String motivo1) throws CertificadoException, br.com.swconsultoria.nfe.exception.NfeException{
    
        tipo = motivo1;
    chave = chave1;
    
     FiscalDAO fdao = new FiscalDAO();
    for(Fiscal f: fdao.read()){
         cnpj = f.getCNPJ();
         }
    cnpj = cnpj.replaceAll("[^0-9]", "");
    
    manifesta();
    }
    
    public static void manifesta() throws br.com.swconsultoria.nfe.exception.NfeException {
         StringBuilder sb = new StringBuilder();
        
        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesIniciaisNfe config = iniciaConfigurações();  

            //String chave = "XXX";
            //String cnpj = "XXX";
            String motivo = null;
            TipoManifestacao tipoManifestacao = null;
            if(tipo.equals("Confirmação da Operação")){
                tipoManifestacao = TipoManifestacao.CONFIRMACAO_DA_OPERACAO;
            }
            if(tipo.equals("Desconhecimento da Operação")){
                tipoManifestacao = TipoManifestacao.DESCONHECIMENTO_DA_OPERACAO;
            }
            if(tipo.equals("Operação Não Realizada")){
                tipoManifestacao = TipoManifestacao.OPERACAO_NAO_REALIZADA;
            }
            if(tipo.equals("Ciência da Emissão")){
                tipoManifestacao = TipoManifestacao.CIENCIA_DA_OPERACAO;
            }
            

            TRetEnvEvento retorno = Nfe.manifestacao(chave, tipoManifestacao, cnpj, motivo, XmlUtil.dataNfe());

            if (!StatusEnum.LOTE_EVENTO_PROCESSADO.getCodigo().equals(retorno.getCStat())) {
                sb.append("Status:" + retorno.getCStat() + "\nMotivo:" + retorno.getXMotivo());
                NotasFiscais.retornoManifestação.setText(sb.toString());
                throw new NfeException("Status:" + retorno.getCStat() + " - Motivo:" + retorno.getXMotivo());
               
            }

            if (!StatusEnum.EVENTO_VINCULADO.getCodigo().equals(retorno.getRetEvento().get(0).getInfEvento().getCStat())) {
                sb.append("Status:" + retorno.getRetEvento().get(0).getInfEvento().getCStat() + "\n Motivo:" + retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
                NotasFiscais.retornoManifestação.setText(sb.toString());
                throw new NfeException("Status:" + retorno.getRetEvento().get(0).getInfEvento().getCStat() + " - Motivo:" + retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
            }

            System.out.println("Status:" + retorno.getRetEvento().get(0).getInfEvento().getCStat());
            System.out.println("Motivo:" + retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
            System.out.println("Data:" + retorno.getRetEvento().get(0).getInfEvento().getDhRegEvento());
            sb.append("Status:" + retorno.getRetEvento().get(0).getInfEvento().getCStat());
            sb.append("Motivo:" + retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
            sb.append("Data:" + retorno.getRetEvento().get(0).getInfEvento().getDhRegEvento());
            
            NotasFiscais.retornoManifestação.setText(sb.toString());

        } catch (CertificadoException | NfeException e) {
            System.err.println(e.getMessage());
        }

    }
    
}
