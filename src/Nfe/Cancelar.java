package Nfe;

import javax.xml.bind.JAXBException;

import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento;

import nfe.dom.ConfiguracoesNfe;
import nfe.exception.NfeException;
import nfe.util.ConstantesUtil;
import nfe.util.XmlUtil;

/**
 * @author Samuel Oliveira - samuk.exe@hotmail.com Data: 28/09/2017 - 11:11
 */
class Cancelar {

	static TRetEnvEvento eventoCancelamento(ConfiguracoesNfe config, TEnvEvento enviEvento, boolean valida, String tipo)
			throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

		try {

			String xml = XmlUtil.objectToXml(enviEvento);
			xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
			xml = xml.replaceAll("<evento v", "<evento xmlns=\"http://www.portalfiscal.inf.br/nfe\" v");

			xml = Eventos.enviarEvento(config, xml, ConstantesUtil.EVENTO.CANCELAR, valida, tipo);

			return XmlUtil.xmlToObject(xml, TRetEnvEvento.class);

		} catch (JAXBException e) {
			throw new NfeException(e.getMessage());
		}

	}

}
