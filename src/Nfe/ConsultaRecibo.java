package Nfe;

import br.com.swconsultoria.nfe.schema_4.consReciNFe.TConsReciNFe;
import br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe;
import br.com.swconsultoria.nfe.wsdl.NFeRetAutorizacao.NFeRetAutorizacao4Stub;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import nfe.dom.ConfiguracoesNfe;
import nfe.exception.NfeException;
import nfe.util.ConstantesUtil;
import nfe.util.ObjetoUtil;
import nfe.util.WebServiceUtil;
import nfe.util.XmlUtil;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;
import org.apache.axis2.transport.http.HTTPConstants;



/**
 * Classe Responsavel Por pegar o Retorno da NFE, apos o Envio.
 *
 * @author Samuel Oliveira
 */
class ConsultaRecibo {

	/**
	 * Metodo Responsavel Por Pegar o Xml De Retorno.
	 *
	 * @param tConsReciNFe
	 * @param valida
	 * @param tipo
	 * @return
	 * @throws NfeException
	 */

	static TRetConsReciNFe reciboNfe(ConfiguracoesNfe config, String recibo, String tipo) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

		try {

			/**
			 * Informaçoes do Certificado Digital.
			 */

			TConsReciNFe consReciNFe = new TConsReciNFe();
			consReciNFe.setVersao(config.getVersaoNfe());
			consReciNFe.setTpAmb(config.getAmbiente());
			consReciNFe.setNRec(recibo);

			String xml = XmlUtil.objectToXml(consReciNFe);

			OMElement ome = AXIOMUtil.stringToOM(xml);
			NFeRetAutorizacao4Stub.NfeDadosMsg dadosMsg = new NFeRetAutorizacao4Stub.NfeDadosMsg();
			dadosMsg.setExtraElement(ome);

			NFeRetAutorizacao4Stub stub = new NFeRetAutorizacao4Stub(tipo.equals(ConstantesUtil.NFCE)
					? WebServiceUtil.getUrl(config, ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.CONSULTA_RECIBO)
					: WebServiceUtil.getUrl(config, ConstantesUtil.NFE, ConstantesUtil.SERVICOS.CONSULTA_RECIBO));
			// Timeout
			if (!ObjetoUtil.isEmpty(config.getTimeout())) {
				stub._getServiceClient().getOptions().setProperty(HTTPConstants.SO_TIMEOUT, config.getTimeout());
				stub._getServiceClient().getOptions().setProperty(HTTPConstants.CONNECTION_TIMEOUT,
						config.getTimeout());
			}
			NFeRetAutorizacao4Stub.NfeResultMsg result = stub.nfeRetAutorizacaoLote(dadosMsg);

			return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetConsReciNFe.class);

		} catch (RemoteException | XMLStreamException | JAXBException e) {
			throw new NfeException(e.getMessage());
		}

	}
}
