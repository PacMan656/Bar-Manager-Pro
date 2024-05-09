package Nfe;

import Util.ConstantesUtil;
import Util.WebServiceUtil;
import Util.XmlUtil;
import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.util.AXIOMUtil;


import br.com.swconsultoria.nfe.schema_4.consStatServ.TConsStatServ;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import br.com.swconsultoria.nfe.wsdl.NFeStatusServico4.NFeStatusServico4Stub;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe responsavel por fazer a Verificacao do Status Do Webservice
 * 
 * @author Samuel Oliveira
 *
 */
class Status {

	/**
	 * Metodo para Consulta de Status de Serviço
	 *
	 * @param tipo
	 *            ConstantesUtil.NFE e ConstantesUtil.NFCE
	 * @return
	 * @throws NfeException
	 */
	static TRetConsStatServ statusServico(ConfiguracoesNfe config, String tipo) throws NfeException {

		try {

			TConsStatServ consStatServ = new TConsStatServ();
			consStatServ.setTpAmb(config.getAmbiente());
			consStatServ.setCUF(config.getEstado().getCodigoIbge());
			consStatServ.setVersao(config.getVersaoNfe());
			consStatServ.setXServ("STATUS");
			String xml = XmlUtil.objectToXml(consStatServ);

			if (config.isLog()) {
				System.out.println("Xml Status: " + xml);
			}
			OMElement ome = AXIOMUtil.stringToOM(xml);

			NFeStatusServico4Stub.NfeDadosMsg dadosMsg = new NFeStatusServico4Stub.NfeDadosMsg();
			dadosMsg.setExtraElement(ome);

			NFeStatusServico4Stub stub = new NFeStatusServico4Stub(tipo.equals(ConstantesUtil.NFCE)
					? WebServiceUtil.getUrl(config, ConstantesUtil.NFCE, ConstantesUtil.SERVICOS.STATUS_SERVICO)
					: WebServiceUtil.getUrl(config, ConstantesUtil.NFE, ConstantesUtil.SERVICOS.STATUS_SERVICO));
			NFeStatusServico4Stub.NfeResultMsg result = stub.nfeStatusServicoNF(dadosMsg);

			return XmlUtil.xmlToObject(result.getExtraElement().toString(), TRetConsStatServ.class);

		} catch (RemoteException | XMLStreamException | JAXBException e) {
			throw new NfeException(e.getMessage());
		} catch (br.com.swconsultoria.nfe.exception.NfeException ex) {
                Logger.getLogger(Status.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;

	}

}