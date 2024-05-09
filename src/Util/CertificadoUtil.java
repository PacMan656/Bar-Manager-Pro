package Util;

import Nfe.ConfiguracoesIniciaisNfe;
import Nfe.ConfiguracoesWebNfe;
import Nfe.NfeException;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;

/**
 * Classe Responsavel Por Carregar as informações do Certificado Digital
 * 
 * @author Samuel Oliveira
 * 
 */
public class CertificadoUtil {

	public static ConfiguracoesIniciaisNfe iniciaConfiguracoes() throws NfeException {

		try {
			Certificado certificado = ConfiguracoesIniciaisNfe.getInstance().getCertificado();
			if (!certificado.isValido()) {
				throw new CertificadoException("Certificado vencido.");
			}
			CertificadoService.inicializaCertificado(certificado, CertificadoUtil.class.getResourceAsStream("/Cacert"));
		} catch (CertificadoException e) {
			throw new NfeException(e.getMessage());
		}

		return ConfiguracoesIniciaisNfe.getInstance();
	}

    public static ConfiguracoesWebNfe iniciaConfiguracoes(ConfiguracoesWebNfe config) throws NfeException {

        try {
            Certificado certificado = config.getCertificado();
            if (!certificado.isValido()) {
                throw new CertificadoException("Certificado vencido.");
            }
            CertificadoService.inicializaCertificado(certificado, CertificadoUtil.class.getResourceAsStream("/Cacert"));
        } catch (CertificadoException e) {
            throw new NfeException(e.getMessage());
        }

        return config;
    }



}