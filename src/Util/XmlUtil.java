package Util;

import br.com.swconsultoria.nfe.exception.NfeException;
import br.com.swconsultoria.nfe.schema.consCad.TConsCad;
import br.com.swconsultoria.nfe.schema.distdfeint.DistDFeInt;
import br.com.swconsultoria.nfe.schema.envEventoCancNFe.TEnvEvento;
import br.com.swconsultoria.nfe.schema_4.consReciNFe.TConsReciNFe;
import br.com.swconsultoria.nfe.schema_4.consSitNFe.TConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.consStatServ.TConsStatServ;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TEnviNFe;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TNfeProc;
import br.com.swconsultoria.nfe.schema_4.enviNFe.TRetEnviNFe;

import br.com.swconsultoria.nfe.schema_4.inutNFe.TInutNFe;
import br.com.swconsultoria.nfe.schema_4.inutNFe.TProcInutNFe;
import br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TRetConsSitNFe;
import br.com.swconsultoria.nfe.schema_4.retEnviNFe.TProtNFe;
import br.com.swconsultoria.nfe.util.XsdUtil;
import javax.xml.bind.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.GregorianCalendar;
import java.util.zip.GZIPInputStream;

/**
 * Classe Responsavel por Metodos referentes ao XML
 *
 * @author Samuel Oliveira
 */
public class XmlUtil {

    private static final String STATUS = "TConsStatServ";
    private static final String SITUACAO_NFE = "TConsSitNFe";
    private static final String ENVIO_NFE = "TEnviNFe";
    private static final String DIST_DFE = "DistDFeInt";
    private static final String INUTILIZACAO = "TInutNFe";
    private static final String NFEPROC = "TNfeProc";
    private static final String EVENTO = "TEnvEvento";
    private static final String TPROCEVENTO = "TProcEvento";
    private static final String TCONSRECINFE = "TConsReciNFe";
    private static final String TConsCad = "TConsCad";
    private static final String TPROCINUT = "TProcInutNFe";
    private static final String RETORNO_ENVIO = "TRetEnviNFe";
    private static final String SITUACAO_NFE_RET = "TRetConsSitNFe";

    private static final String TPROCCANCELAR = "br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TProcEvento";
    private static final String TPROCCCE = "br.inf.portalfiscal.nfe.schema.envcce.TProcEvento";
    private static final String TPROCEPEC = "br.inf.portalfiscal.nfe.schema.envEpec.TProcEvento";

    private static final String TProtNFe = "TProtNFe";
    private static final String TProtEnvi = "br.inf.portalfiscal.nfe.schema_4.enviNFe.TProtNFe";
    private static final String TProtCons = "br.inf.portalfiscal.nfe.schema_4.retConsSitNFe.TProtNFe";
    private static final String TProtReci = "br.inf.portalfiscal.nfe.schema_4.retConsReciNFe.TProtNFe";

    private static final String CANCELAR = "br.inf.portalfiscal.nfe.schema.envEventoCancNFe.TEnvEvento";
    private static final String CCE = "br.inf.portalfiscal.nfe.schema.envcce.TEnvEvento";
    private static final String EPEC = "br.inf.portalfiscal.nfe.schema.envEpec.TEnvEvento";
    private static final String MANIFESTAR = "br.inf.portalfiscal.nfe.schema.envConfRecebto.TEnvEvento";

    /**
     * Transforma o String do XML em Objeto
     *
     * @param <T>
     * @param xml
     * @param classe
     * @return T
     * @throws javax.xml.bind.JAXBException
     */
    public static <T> T xmlToObject(String xml, Class<T> classe) throws JAXBException {

        JAXBContext context = JAXBContext.newInstance(classe);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        return unmarshaller.unmarshal(new StreamSource(new StringReader(xml)), classe).getValue();
    }

    /**
     * Transforma o Objeto em XML(String)
     *
     * @param <T>
     * @param obj
     * @return
     * @throws JAXBException
     * @throws NfeException
     */
    public static <T> String objectToXml(Object obj) throws NfeException, JAXBException  {

        JAXBContext context = null;
        JAXBElement<?> element = null;

        switch (obj.getClass().getSimpleName()) {

            case STATUS -> {
                context = JAXBContext.newInstance(TConsStatServ.class);
                element = new br.com.swconsultoria.nfe.schema_4.consStatServ.ObjectFactory().createConsStatServ((TConsStatServ) obj);
            }

            case ENVIO_NFE -> {
                context = JAXBContext.newInstance(TEnviNFe.class);
                element = new br.com.swconsultoria.nfe.schema_4.enviNFe.ObjectFactory().createEnviNFe((TEnviNFe) obj);
            }

            case RETORNO_ENVIO -> {
                context = JAXBContext.newInstance(TRetEnviNFe.class);
                element = XsdUtil.enviNfe.createTRetEnviNFe((TRetEnviNFe) obj);
            }

            case SITUACAO_NFE -> {
                context = JAXBContext.newInstance(TConsSitNFe.class);
                element = new br.com.swconsultoria.nfe.schema_4.consSitNFe.ObjectFactory().createConsSitNFe((TConsSitNFe) obj);
            }

            case DIST_DFE -> {
                context = JAXBContext.newInstance(DistDFeInt.class);
                element = new br.com.swconsultoria.nfe.schema.distdfeint.ObjectFactory().createDistDFeInt((DistDFeInt) obj);
            }

            case TCONSRECINFE -> {
                context = JAXBContext.newInstance(TConsReciNFe.class);
                element = new br.com.swconsultoria.nfe.schema_4.consReciNFe.ObjectFactory().createConsReciNFe((TConsReciNFe) obj);
            }

            case TConsCad -> {
                context = JAXBContext.newInstance(TConsCad.class);
                element = new br.com.swconsultoria.nfe.schema.consCad.ObjectFactory().createConsCad((TConsCad) obj);
            }

            case INUTILIZACAO -> {
                context = JAXBContext.newInstance(TInutNFe.class);
                element = new br.com.swconsultoria.nfe.schema_4.inutNFe.ObjectFactory().createInutNFe((TInutNFe) obj);
            }

            case SITUACAO_NFE_RET -> {
                context = JAXBContext.newInstance(TRetConsSitNFe.class);
                element = new br.com.swconsultoria.nfe.schema_4.retConsSitNFe.ObjectFactory().createRetConsSitNFe((TRetConsSitNFe) obj);
            }

            case TPROCEVENTO -> {
                switch (obj.getClass().getName()) {
                    case TPROCCANCELAR -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema.envEventoCancNFe.TProcEvento.class);
                        element = new br.com.swconsultoria.nfe.schema.envEventoCancNFe.ObjectFactory().createTProcEvento((br.com.swconsultoria.nfe.schema.envEventoCancNFe.TProcEvento) obj);
                }
                    case TPROCCCE -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema.envcce.TProcEvento.class);
                        element = new br.com.swconsultoria.nfe.schema.envcce.ObjectFactory().createTProcEvento((br.com.swconsultoria.nfe.schema.envcce.TProcEvento) obj);
                }
                    case TPROCEPEC -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema.envEpec.TProcEvento.class);
                        element = XsdUtil.epec.createTProcEvento((br.com.swconsultoria.nfe.schema.envEpec.TProcEvento) obj);
                }
                }
            }

            case NFEPROC -> {
                context = JAXBContext.newInstance(TNfeProc.class);
                element = XsdUtil.enviNfe.createTNfeProc((TNfeProc) obj);
            }

            case TPROCINUT -> {
                context = JAXBContext.newInstance(TProcInutNFe.class);
                element = XsdUtil.inutNfe.createTProcInutNFe((TProcInutNFe) obj);
            }

            case EVENTO -> {
                switch (obj.getClass().getName()) {
                    case CANCELAR -> {
                        context = JAXBContext.newInstance(TEnvEvento.class);
                        element = new br.com.swconsultoria.nfe.schema.envEventoCancNFe.ObjectFactory().createEnvEvento((TEnvEvento) obj);
                }
                    case CCE -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema.envcce.TEnvEvento.class);
                        element = new br.com.swconsultoria.nfe.schema.envcce.ObjectFactory().createEnvEvento((br.com.swconsultoria.nfe.schema.envcce.TEnvEvento) obj);
                }
                    case EPEC -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema.envEpec.TEnvEvento.class);
                        element = new br.com.swconsultoria.nfe.schema.envEpec.ObjectFactory().createEnvEvento((br.com.swconsultoria.nfe.schema.envEpec.TEnvEvento) obj);
                }
                    case MANIFESTAR -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema.envConfRecebto.TEnvEvento.class);
                        element = new br.com.swconsultoria.nfe.schema.envConfRecebto.ObjectFactory().createEnvEvento((br.com.swconsultoria.nfe.schema.envConfRecebto.TEnvEvento) obj);
                }
                }
            }

            case TProtNFe -> {
                switch (obj.getClass().getName()) {
                    case TProtEnvi -> {
                        context = JAXBContext.newInstance(TProtNFe.class);
                        element = XsdUtil.enviNfe.createTProtNFe((br.com.swconsultoria.nfe.schema_4.enviNFe.TProtNFe) obj);
                }
                    case TProtCons -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TProtNFe.class);
                        element = XsdUtil.retConsSitNfe.createTProtNFe((br.com.swconsultoria.nfe.schema_4.retConsSitNFe.TProtNFe) obj);
                }
                    case TProtReci -> {
                        context = JAXBContext.newInstance(br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TProtNFe.class);
                        element = XsdUtil.retConsReciNfe.createTProtNFe((br.com.swconsultoria.nfe.schema_4.retConsReciNFe.TProtNFe) obj);
                }
                }
            }

            default -> throw new NfeException("Objeto não mapeado no XmlUtil:" + obj.getClass().getSimpleName());
        }
        Marshaller marshaller = context.createMarshaller();

        marshaller.setProperty("jaxb.encoding", "Unicode");
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

        StringWriter sw = new StringWriter();

        if (obj.getClass().getSimpleName().equals(ENVIO_NFE) || obj.getClass().getSimpleName().equals(NFEPROC)) {
            CDATAContentHandler cdataHandler = new CDATAContentHandler(sw, "utf-8");
            marshaller.marshal(element, cdataHandler);
        } else {
            marshaller.marshal(element, sw);
        }
        StringBuilder xml = new StringBuilder();
        xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append(sw.toString());

        if ((obj.getClass().getSimpleName().equals(TPROCEVENTO))) {
            return replacesNfe(xml.toString().replaceAll("procEvento", "procEventoNFe"));
        } else {
            return replacesNfe(xml.toString());
        }

    }

    public static String gZipToXml(byte[] conteudo) throws IOException {
        if (conteudo == null || conteudo.length == 0) {
            return "";
        }
        GZIPInputStream gis;
        gis = new GZIPInputStream(new ByteArrayInputStream(conteudo));
        BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder outStr = new StringBuilder();
        String line;
        while ((line = bf.readLine()) != null) {
            outStr.append(line);
        }

        return outStr.toString();
    }

    public static String criaNfeProc(TEnviNFe enviNfe, Object retorno) throws JAXBException, NfeException {

        TNfeProc nfeProc = new TNfeProc();
        nfeProc.setVersao("4.00");
        nfeProc.setNFe(enviNfe.getNFe().get(0));
        String xml = XmlUtil.objectToXml(retorno);

        return XmlUtil.objectToXml(nfeProc);
    }

    public static String removeAcentos(String str) {

        str = str.replaceAll("\r", "");
        str = str.replaceAll("\t", "");
        str = str.replaceAll("\n", "");
        str = str.replaceAll("&", "E");
        str = str.replaceAll(">\\s+<", "><");
        CharSequence cs = new StringBuilder(str == null ? "" : str);
        return Normalizer.normalize(cs, Normalizer.Form.NFKD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

    }

    private static String replacesNfe(String xml) {

        xml = xml.replaceAll("ns2:", "");
        xml = xml.replaceAll("<!\\[CDATA\\[<!\\[CDATA\\[", "<!\\[CDATA\\[");
        xml = xml.replaceAll("\\]\\]>\\]\\]>", "\\]\\]>");
        xml = xml.replaceAll("ns3:", "");
        xml = xml.replaceAll("&lt;", "<");
        xml = xml.replaceAll("&amp;", "&");
        xml = xml.replaceAll("&gt;", ">");
        xml = xml.replaceAll("<Signature>", "<Signature xmlns=\"http://www.w3.org/2000/09/xmldsig#\">");
        xml = xml.replaceAll(" xmlns:ns2=\"http://www.w3.org/2000/09/xmldsig#\"", "");
        xml = xml.replaceAll(" xmlns=\"\" xmlns:ns3=\"http://www.portalfiscal.inf.br/nfe\"", "");

        return xml;

    }

    /**
     * Le o Arquivo XML e retona String
     *
     * @param arquivo
     * @return String
     * @throws NfeException
     */
    public static String leXml(String arquivo) throws NfeException {

        StringBuilder xml = new StringBuilder();
        BufferedReader in;
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(arquivo), "UTF-8"));
            String linha;

            while ((linha = in.readLine()) != null) {
                xml.append(linha);

            }
            in.close();
        } catch (IOException e) {
            throw new NfeException("Ler Xml: " + e.getMessage());
        }
        return xml.toString();
    }

    public static String dataNfe() throws NfeException {
        try {
            LocalDateTime dataASerFormatada = LocalDateTime.now();
            GregorianCalendar calendar = GregorianCalendar.from(dataASerFormatada.atZone(ZoneId.of("Brazil/East")));

            XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
            xmlCalendar.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);

            return (xmlCalendar.toString());
        } catch (DatatypeConfigurationException e) {
            throw new NfeException(e.getMessage());
        }

    }

}
