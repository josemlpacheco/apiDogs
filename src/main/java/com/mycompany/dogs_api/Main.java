/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dogs_api;

import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author jo
 */
public class Main {
    public static void main(String[] args) throws IOException {
        int menu_option = -1;
        String[] buttons = {"1.- Watch dogs","2. Watch favorites","3.- Exit"};
        
        do {            
            String option = (String) JOptionPane.showInputDialog(null, "Dogs", "Menu", JOptionPane.INFORMATION_MESSAGE,null,buttons,buttons[0]);
            
            for (int i = 0; i < buttons.length; i++) {
                if(option.equals(buttons[i])){
                    menu_option=i;
                }
            }
            switch(menu_option){
                case 0:
                    DogService.watchDogs();
                case 1: 
                    Dog dog = new Dog();
                    DogService.watchFavorites(dog);
                default:
                    break;
            }
        } while (menu_option != 1);
    }
}
