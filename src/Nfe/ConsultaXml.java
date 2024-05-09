package Nfe;

import Util.ConstantesUtil;
import Util.ObjetoUtil;
import Util.WebServiceUtil;
import Util.XmlUtil;
import br.com.swconsultoria.nfe.schema_4.consSitNFe.TConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.wsdl.NFeConsultaProtocolo.NFeConsultaProtocolo4Stub;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.transport.http.HTTPConstants;

/**
 * Classe responsavel por Consultar a Situaçao do XML na SEFAZ.
 *
 * @author Samuel Oliveira - samuk.exe@hotmail.com - www.samuelweb.com.br
 */
class ConsultaXml {

    /**
     * Classe Reponsavel Por Consultar o status da NFE na SEFAZ
     *
     * @param chave
     * @param tipo
     * @return
     * @throws NfeException
     */
    static TRetConsSitNFe consultaXml(ConfiguracoesNfe config, String chave, String tipo) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        try {

            TConsSitNFe consSitNFe = new TConsSitNFe();
            consSitNFe.setVersao(config.getVersaoNfe());
            consSitNFe.setTpAmb(config.getAmbiente());
            consSitNFe.setXServ("CONSULTAR");
            consSitNFe.setChNFe(chave);

            String xml = XmlUtil.objectToXml(consSitNFe);

            if (config.isLog()) {
                System.out.println("Xml Consulta: " + xml);
            }
            OMElement ome = AXIOMUtil.stringToOM(xml);

            NFeConsultaProtocolo4Stub.NfeDadosMsg dadosMsg = new NFeConsultaProtocolo4Stub.NfeDadosMsg();
            dadosMsg.setExtraElement(ome);

            NFeConsultaProtocolo4Stub stub = new NFeConsultaProtocolo4Stub(tipo.equals(ConstantesUtil.NFCE)
                    ? WebServiceUtil.getUrl(config, ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.CONSULTA_XML)
                    : WebServiceUtil.getUrl(config, ConstantesUtil.NFE, ConstantesUtil.SERVICOS.CONSULTA_XML));
            // Timeout
            if (!ObjetoUtil.isEmpty(config.getTimeout())) {
                stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, config.getTimeout());
                stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT,
                        config.getTimeout());
            }
            NFeConsultaProtocolo4Stub.NfeResultMsg result = stub.nfeConsultaNF(dadosMsg);

            return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetConsSitNFe.class);

        } catch (RemoteException | XMLStreamException | JAXBException e) {
            throw new NfeException(e.getMessage());
        }

    }

}
