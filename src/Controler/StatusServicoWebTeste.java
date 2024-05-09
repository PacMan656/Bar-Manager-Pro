/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import Bean.Fiscal;
import DAO.FiscalDAO;
import Nfe.ConfiguracoesWebNfe;
import Nfe.NfeException;
import Nfe.NfeWeb;
import Util.ConstantesUtil;
import Util.Estados;
import br.com.swconsultoria.certificado.Certificado;
import br.com.swconsultoria.certificado.CertificadoService;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;
import java.io.FileNotFoundException;

/**
 *
 * @author Marcos
 */
public class StatusServicoWebTeste {

    public static void main(String[] args) throws FileNotFoundException {

        try {

            FiscalDAO fdao = new FiscalDAO();
            String caminhoCertificado = null;
            String senha = null;
            String estado = null;

            for (Fiscal f : fdao.read()) {
                caminhoCertificado = f.getCertificado();
                senha = f.getCSenha();
                estado = f.getUf();

            }
            String patch = "C:\\Users\\Marcos\\Documents\\Plymouth Tecnologia\\gerenciador de vendas\\Gerenciador 4.0 Supermercado Simples\\schemas";

            // Inicia As Certificado
            Certificado certificado = CertificadoService.certificadoPfx(caminhoCertificado, senha);
            //Esse Objeto Você pode guardar em uma Session.
            ConfiguracoesWebNfe config = ConfiguracoesWebNfe.iniciaConfiguracoes(Estados.BA,
                    ConstantesUtil.AMBIENTE.HOMOLOGACAO,
                    certificado,
                    patch, //PEGAR SCHEMAS EM AMBIENTE WEB ESTA PASTA ESTA DENTRO DE RESOURCES
                    false);

            TRetConsStatServ retorno = NfeWeb.statusServico(config, ConstantesUtil.NFCE);
            System.out.println("Status:" + retorno.getCStat());
            System.out.println("Motivo:" + retorno.getXMotivo());
            System.out.println("Data:" + retorno.getDhRecbto());

        } catch (CertificadoException | NfeException e) {
            System.err.println(e.getMessage());
        }

    }

}
