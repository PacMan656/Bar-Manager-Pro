/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Nfe;

import Util.ConstantesUtil;
import br.com.swconsultoria.nfe.schema_4.retConsStatServ.TRetConsStatServ;

/**
 *
 * @author Marcos
 */
public class StatusServicoSefaz {

    public static void main(String[] args) {

        try {

// Inicia As Configurações - ver https://github.com/Samuel-Oliveira/Java_NFe/wiki/1-:-Configuracoes
            TRetConsStatServ retorno = Nfe.statusServico(ConstantesUtil.NFCE);
            System.out.println("Status:" + retorno.getCStat());
            System.out.println("Motivo:" + retorno.getXMotivo());
            System.out.println("Data:" + retorno.getDhRecbto());

        } catch (NfeException e) {
            System.err.println(e.getMessage());
        }

    }

}
