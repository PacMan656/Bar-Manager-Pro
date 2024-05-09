/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controler;

import View.Login;
import View.Server;

/**
 *
 * @author Marcos
 */
public class Gerenciador {

    public static void main(String[] args) {

        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(false);
        });

        java.awt.EventQueue.invokeLater(() -> {
            new Server().setVisible(true);
        });
    }

}
