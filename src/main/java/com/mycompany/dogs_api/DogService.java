/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dogs_api;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author jo
 */
public class DogService {

    public static void watchDogs() throws IOException{
        OkHttpClient client = new OkHttpClient();
        
        Request request = new Request.Builder().url("https://api.thedogapi.com/v1/images/search").get().build();
        
        Response response = client.newCall(request).execute();
        
        String rel = response.body().string();
        
        rel= rel.substring(1,rel.length());
        rel= rel.substring(0,rel.length()-1);
        
        Gson gson = new Gson();
        Dog dogs = gson.fromJson(rel, Dog.class);
        
        Image image = null;
        try {
            URL url = new URL(dogs.getUrl());
            image = ImageIO.read(url);
            ImageIcon dogIcon = new ImageIcon(image);
            
            if(dogIcon.getIconWidth()>200){
                Image img = dogIcon.getImage();
                Image imgMod = img.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
                dogIcon = new ImageIcon(imgMod);
            }
            
            String menuDogService = "Options: \n"
                    + "1.- Watch other image \n"
                    + "2.- Favorites \n"
                    + "3.- Back \n";
            
            String[] buttons = {"Watch other image", "Favorites","Back"};
            
            String id_dog = dogs.getId();
            
            String option = (String) JOptionPane.showInputDialog(null,menuDogService,id_dog,JOptionPane.INFORMATION_MESSAGE,dogIcon,buttons,buttons[0]);
            
            int selection = -1;
            
            for (int i = 0; i < buttons.length; i++) {
                if(option.equals(buttons[i])){
                    selection=i;
                }
            }
            switch(selection){
                case 0:
                    watchDogs();
                case 1:
                    addFavorites(dogs);
                default:
                    break;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void addFavorites(Dog dogs) {
        
    }
}
