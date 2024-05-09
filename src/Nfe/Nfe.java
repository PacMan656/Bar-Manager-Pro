/**
 *
 */
package Nfe;

import Enum.TipoManifestacao;
import Util.CertificadoUtil;
import Util.Estados;
import Util.XmlUtil;
import br.com.swconsultoria.nfe.schema.consCad.TRetConsCad;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema.retdistdfeint.RetDistDFeInt;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TRetInutNFe;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import javax.xml.bind.JAXBException;

/**
 * @author Samuel Oliveira - samuk.exe@hotmail.com - www.samuelweb.com.br
 */
public class Nfe {

    /**
     * Construtor privado
     */
    private Nfe() {
    }

    /**
     * Classe Reponsavel Por Consultar a Distribuiçao da NFE na SEFAZ
     *
     * @param tipoCliente Informar DistribuicaoDFe.CPF ou DistribuicaoDFe.CNPJ
     * @param cpfCnpj
     * @param tipoConsulta Informar DistribuicaoDFe.NSU ou DistribuicaoDFe.CHAVE
     * @param nsuChave
     * @return
     * @throws NfeException
     */
    public static RetDistDFeInt distribuicaoDfe(String tipoCliente, String cpfCnpj, String tipoConsulta,
            String nsuChave) throws NfeException {

        return DistribuicaoDFe.consultaNfe(CertificadoUtil.iniciaConfiguracoes(), tipoCliente, cpfCnpj, tipoConsulta,
                nsuChave);

    }

    /**
     * Metodo Responsavel Buscar o Status de Serviço do Servidor da Sefaz No
     * tipo Informar ConstantesUtil.NFE ou ConstantesUtil.NFCE
     *
     * @param tipo
     * @return
     * @throws NfeException
     */
    public static TRetConsStatServ statusServico(String tipo) throws NfeException {

        return (TRetConsStatServ) Status.statusServico(CertificadoUtil.iniciaConfiguracoes(), tipo);

    }

    /**
     * Classe Reponsavel Por Consultar o status da NFE na SEFAZ No tipo Informar
     * ConstantesUtil.NFE ou ConstantesUtil.NFCE
     *
     * @param chave
     * @param tipo
     * @return TRetConsSitNFe
     * @throws NfeException
     * @throws br.com.swconsultoria.nfe.exception.NfeException
     */
    public static br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe consultaXml(String chave, String tipo) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return ConsultaXml.consultaXml(CertificadoUtil.iniciaConfiguracoes(), chave, tipo);

    }

    /**
     * Classe Reponsavel Por Consultar o cadastro do Cnpj/CPF na SEFAZ
     *
     * @param tipo Usar ConsultaCadastro.CNPJ ou ConsultaCadastro.CPF
     * @param cnpjCpf
     * @param estado
     * @return TRetConsCad
     * @throws NfeException
     * @throws br.com.swconsultoria.nfe.exception.NfeException
     */
    public static TRetConsCad consultaCadastro(String tipo, String cnpjCpf, Estados estado) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return ConsultaCadastro.consultaCadastro(CertificadoUtil.iniciaConfiguracoes(), tipo, cnpjCpf, estado);

    }

    /**
     * Classe Reponsavel Por Consultar o retorno da NFE na SEFAZ No tipo
     * Informar ConstantesUtil.NFE ou ConstantesUtil.NFCE
     *
     * @param recibo
     * @param tipo
     * @return
     * @throws NfeException
     * @throws br.com.swconsultoria.nfe.exception.NfeException
     */
    public static br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetConsReciNFe consultaRecibo(String recibo, String tipo) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return ConsultaRecibo.reciboNfe(CertificadoUtil.iniciaConfiguracoes(), recibo, tipo);

    }

    /**
     * Classe Reponsavel Por Inutilizar a NFE na SEFAZ No tipo Informar
     * ConstantesUtil.NFE ou ConstantesUtil.NFCE Id = Código da UF + Ano (2
     * posições) + CNPJ + modelo + série + número inicial e número final
     * precedida do literal “ID”
     *
     * @param id
     * @param motivo
     * @param validar
     * @param tipo
     * @return
     * @throws NfeException
     * @throws br.com.swconsultoria.nfe.exception.NfeException
     */
    public static TRetInutNFe inutilizacao(String id, String motivo, String tipo, boolean validar) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return Inutilizar.inutiliza(CertificadoUtil.iniciaConfiguracoes(), id, motivo, tipo, validar);

    }

    /**
     * Classe Reponsavel Por criar o Objeto de Inutilização No tipo Informar
     * ConstantesUtil.NFE ou ConstantesUtil.NFCE Id = Código da UF + Ano (2
     * posições) + CNPJ + modelo + série + número inicial e número final
     * precedida do literal “ID”
     *
     * @param id
     * @param motivo
     * @param valida
     * @param tipo
     * @return
     * @throws NfeException
     * @throws javax.xml.bind.JAXBException
     * @throws br.com.swconsultoria.nfe.exception.NfeException
     */
    public static TInutNFe criaObjetoInutilizacao(String id, String motivo, String tipo)
            throws NfeException, JAXBException, br.com.swconsultoria.nfe.exception.NfeException {

        TInutNFe inutNFe = Inutilizar.criaObjetoInutiliza(CertificadoUtil.iniciaConfiguracoes(), id, motivo, tipo);

        String xml = XmlUtil.objectToXml(inutNFe);
        xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");

        return XmlUtil.xmlToObject(Assinar.assinaNfe(CertificadoUtil.iniciaConfiguracoes(), xml, Assinar.INFINUT),
                TInutNFe.class);

    }

    /**
     * Metodo para Montar a NFE.
     *
     * @param enviNFe
     * @param valida
     * @return
     * @throws NfeException
     */
    public static TEnviNFe montaNfe(TEnviNFe enviNFe, boolean valida) throws NfeException {

        return Enviar.montaNfe(CertificadoUtil.iniciaConfiguracoes(), enviNFe, valida);

    }

    /**
     * Metodo para Enviar a NFE. No tipo Informar ConstantesUtil.NFE ou
     * ConstantesUtil.NFCE
     *
     * @param enviNFe
     * @param tipo
     * @return
     * @throws NfeException
     */
    public static br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TRetEnviNFe enviarNfe(TEnviNFe enviNFe, String tipo) throws NfeException {

        return Enviar.enviaNfe(CertificadoUtil.iniciaConfiguracoes(), enviNFe, tipo);

    }

    /**
     * * Metodo para Cancelar a NFE. No tipo Informar ConstantesUtil.NFE ou
     * ConstantesUtil.NFCE
     *
     * @param envEvento
     * @return
     * @throws NfeException
     */
    public static br.com.swconsultoria.nfe.schema.envEventoCancNFe.TRetEnvEvento cancelarNfe(TEnvEvento envEvento, boolean valida, String tipo) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return Cancelar.eventoCancelamento(CertificadoUtil.iniciaConfiguracoes(), envEvento, valida, tipo);

    }

    /**
     * * Metodo para Enviar o EPEC. No tipo Informar ConstantesUtil.NFE ou
     * ConstantesUtil.NFCE
     *
     * @param envEvento
     * @return
     * @throws NfeException
     */
    public static br.com.swconsultoria.nfe.schema.envEpec.TRetEnvEvento enviarEpec(br.com.swconsultoria.nfe.schema.envEpec.TEnvEvento envEvento, boolean valida, String tipo) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return Epec.eventoEpec(CertificadoUtil.iniciaConfiguracoes(), envEvento, valida, tipo);

    }

    /**
     * * Metodo para Envio da Carta De Correção da NFE. No tipo Informar
     * ConstantesUtil.NFE ou ConstantesUtil.NFCE
     *
     * @param evento
     * @param valida
     * @param tipo
     * @return
     * @throws NfeException
     */
    public static br.com.swconsultoria.nfe.schema.envcce.TRetEnvEvento cce(
            br.com.swconsultoria.nfe.schema.envcce.TEnvEvento evento, boolean valida, String tipo) throws NfeException {

        return CartaCorrecao.eventoCCe(CertificadoUtil.iniciaConfiguracoes(), evento, valida, tipo);

    }

    /**
     * Metodo para Manifestação da NFE.
     *
     * @param chave
     * @param manifestacao
     * @param cnpj
     * @param motivo
     * @param data
     * @return
     * @throws NfeException
     * @throws br.com.swconsultoria.nfe.exception.NfeException
     */
    public static br.com.swconsultoria.nfe.schema.envConfRecebto.TRetEnvEvento manifestacao(String chave,
            TipoManifestacao manifestacao, String cnpj, String motivo, String data) throws NfeException, br.com.swconsultoria.nfe.exception.NfeException {

        return ManifestacaoDestinatario.eventoManifestacao(CertificadoUtil.iniciaConfiguracoes(), chave, manifestacao,
                cnpj, data, motivo);

    }

}
