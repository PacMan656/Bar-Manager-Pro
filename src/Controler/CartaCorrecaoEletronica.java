/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Enum.StatusEnum;
import Nfe.ConfiguracoesIniciaisNfe;
import static Nfe.ConsultarNFeSefaz.iniciaConfigurações;
import Nfe.Nfe;
import Nfe.NfeException;
import Util.ConstantesUtil;
import Util.XmlUtil;
import View.NotasFiscais;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.schema.envcce.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envcce.TProcEvento;
import br.com.swconsultoria.nfe.schema.envcce.TRetEnvEvento;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBException;

/**
 *
 * @author Marcos
 */
public class CartaCorrecaoEletronica {

    public static String chave;
    public static String cnpj;
    public static String sequencia;
    public static String motivo;

    public void recebe(String chave1, String cnpj1, String sequencia1, String motivo1) throws NfeException, FileNotFoundException {

        chave = chave1;
        cnpj = cnpj1;
        sequencia = sequencia1;
        motivo = motivo1;

        mandacarta();

    }

    public static void mandacarta() throws NfeException, FileNotFoundException {

        StringBuilder sb = new StringBuilder();

        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesIniciaisNfe config = iniciaConfigurações();

            //String chave = "XXX";
            // String cnpj = "XXX";
            //String sequencia = "XXX";
            //String motivo = "XXX";
            String id = "ID" + ConstantesUtil.EVENTO.CCE + chave + (sequencia.length() < 2 ? "0" + sequencia : sequencia);

            TEnvEvento envEvento = new TEnvEvento();
            envEvento.setVersao(ConstantesUtil.VERSAO.EVENTO_CCE);
            envEvento.setIdLote("1");

            br.com.swconsultoria.nfe.schema.envcce.TEvento evento = new br.com.swconsultoria.nfe.schema.envcce.TEvento();
            evento.setVersao(ConstantesUtil.VERSAO.EVENTO_CCE);

            br.com.swconsultoria.nfe.schema.envcce.TEvento.InfEvento infEvento = new br.com.swconsultoria.nfe.schema.envcce.TEvento.InfEvento();
            infEvento.setId(id);
            infEvento.setCOrgao(config.getEstado().getCodigoIbge());
            infEvento.setTpAmb(config.getAmbiente());

            infEvento.setCNPJ(cnpj);
            infEvento.setChNFe(chave);

            try {
                // Altere a Data
                infEvento.setDhEvento(XmlUtil.dataNfe());
            } catch (br.com.swconsultoria.nfe.exception.NfeException ex) {
                Logger.getLogger(CartaCorrecaoEletronica.class.getName()).log(Level.SEVERE, null, ex);
            }
            infEvento.setTpEvento(ConstantesUtil.EVENTO.CCE);
            infEvento.setNSeqEvento(sequencia);
            infEvento.setVerEvento(ConstantesUtil.VERSAO.EVENTO_CCE);

            br.com.swconsultoria.nfe.schema.envcce.TEvento.InfEvento.DetEvento detEvento = new br.com.swconsultoria.nfe.schema.envcce.TEvento.InfEvento.DetEvento();
            detEvento.setVersao(ConstantesUtil.VERSAO.EVENTO_CCE);
            detEvento.setDescEvento("Carta de Correcao");

            // Informe a Correção
            detEvento.setXCorrecao(motivo);
            detEvento.setXCondUso("A Carta de Correcao e disciplinada pelo paragrafo 1o-A do art. 7o do Convenio S/N, de 15 de dezembro de 1970 e pode ser utilizada para regularizacao de erro ocorrido na emissao de documento fiscal, desde que o erro nao esteja relacionado com: I - as variaveis que determinam o valor do imposto tais como: base de calculo, aliquota, diferenca de preco, quantidade, valor da operacao ou da prestacao; II - a correcao de dados cadastrais que implique mudanca do remetente ou do destinatario; III - a data de emissao ou de saida.");
            infEvento.setDetEvento(detEvento);
            evento.setInfEvento(infEvento);
            envEvento.getEvento().add(evento);

            TRetEnvEvento retorno = Nfe.cce(envEvento, false, ConstantesUtil.NFE);

            if (!StatusEnum.LOTE_EVENTO_PROCESSADO.getCodigo().equals(retorno.getCStat())) {
                sb.append("Status:").append(retorno.getCStat()).append(" - Motivo:").append(retorno.getXMotivo());
                NotasFiscais.jTextPane4.setText(sb.toString());
                throw new NfeException("Status:" + retorno.getCStat() + " - Motivo:" + retorno.getXMotivo());
            }

            if (!StatusEnum.EVENTO_VINCULADO.getCodigo().equals(retorno.getRetEvento().get(0).getInfEvento().getCStat())) {
                sb.append("Status:").append(retorno.getRetEvento().get(0).getInfEvento().getCStat()).append(" - Motivo:").append(retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
                NotasFiscais.jTextPane4.setText(sb.toString());
                throw new NfeException("Status:" + retorno.getRetEvento().get(0).getInfEvento().getCStat() + " - Motivo:" + retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
            }

            System.out.println("Status:" + retorno.getRetEvento().get(0).getInfEvento().getCStat());
            System.out.println("Motivo:" + retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
            System.out.println("Data:" + retorno.getRetEvento().get(0).getInfEvento().getDhRegEvento());
            sb.append("Status:").append(retorno.getRetEvento().get(0).getInfEvento().getCStat());
            sb.append("Motivo:").append(retorno.getRetEvento().get(0).getInfEvento().getXMotivo());
            sb.append("Data:").append(retorno.getRetEvento().get(0).getInfEvento().getDhRegEvento());

            // Criação do ProcEventoNFe
            TProcEvento procEvento = new TProcEvento();
            procEvento.setEvento(envEvento.getEvento().get(0));
            procEvento.setRetEvento(retorno.getRetEvento().get(0));
            procEvento.setVersao(ConstantesUtil.VERSAO.EVENTO_CCE);

            String xmlProcEventoNfe = null;
            try {
                xmlProcEventoNfe = XmlUtil.objectToXml(procEvento);
            } catch (br.com.swconsultoria.nfe.exception.NfeException ex) {
                Logger.getLogger(CartaCorrecaoEletronica.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                sb.append(XmlUtil.objectToXml(procEvento));
            } catch (br.com.swconsultoria.nfe.exception.NfeException ex) {
                Logger.getLogger(CartaCorrecaoEletronica.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println(xmlProcEventoNfe);
            NotasFiscais.jTextPane4.setText(sb.toString());

        } catch (CertificadoException | NfeException | JAXBException e) {
            System.err.println(e.getMessage());
        }

    }

}
