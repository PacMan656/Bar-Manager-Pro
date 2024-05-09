/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nfe;

import Bean.Fiscal;
import DAO.FiscalDAO;
import Enum.StatusEnum;
import static Nfe.ConsultarNFeSefaz.iniciaConfigurações;
import Util.ConstantesUtil;
import Util.Estados;
import View.NotasFiscais;
import br.com.swconsultoria.certificado.exception.CertificadoException;
import javax.swing.JOptionPane;

/**
 *
 * @author Marcos
 */
public class ConsultaCadastro_1 {

    //Função para Consultar o Cadastro do Contribuinte na Sefaz.
    public static String cnpj = null;

    public ConsultaCadastro_1() throws br.com.swconsultoria.nfe.exception.NfeException {

        StringBuilder sb = new StringBuilder();

        try {

            // Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            ConfiguracoesIniciaisNfe config = iniciaConfigurações();

            FiscalDAO fdao = new FiscalDAO();
            for (Fiscal f : fdao.read()) {
                cnpj = f.getCNPJ();
            }
            cnpj = cnpj.replaceAll("[^0-9]", "");

            br.com.swconsultoria.nfe.schema.consCad.TRetConsCad retorno = Nfe.consultaCadastro(ConstantesUtil.TIPOS.CNPJ, cnpj, Estados.BA);

            if (retorno.getInfCons().getCStat().equals(StatusEnum.CADASTRO_ENCONTRADO.getCodigo())) {
                System.out.println("Razão Social: " + retorno.getInfCons().getInfCad().get(0).getXNome());
                System.out.println("Cnpj:" + retorno.getInfCons().getInfCad().get(0).getCNPJ());
                System.out.println("Ie:" + retorno.getInfCons().getInfCad().get(0).getIE());
                sb.append("Razão Social: ").append(retorno.getInfCons().getInfCad().get(0).getXNome());
                sb.append("Cnpj:").append(retorno.getInfCons().getInfCad().get(0).getCNPJ());
                sb.append("Ie:").append(retorno.getInfCons().getInfCad().get(0).getIE());
            } else {
                System.err.println(retorno.getInfCons().getCStat() + " - " + retorno.getInfCons().getXMotivo());
                sb.append(retorno.getInfCons().getCStat()).append(" - ").append(retorno.getInfCons().getXMotivo());
            }

            JOptionPane.showMessageDialog(null, sb.toString());
            NotasFiscais.jButton2.setBackground(new java.awt.Color(150, 0, 20));

        } catch (NfeException | CertificadoException e) {
            System.out.println(e.getMessage());
        }

    }

}
