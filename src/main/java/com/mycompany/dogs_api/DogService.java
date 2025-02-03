/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dogs_api;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
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

    public static void watchDogs() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://api.thedogapi.com/v1/images/search").get().build();

        Response response = client.newCall(request).execute();

        String rel = response.body().string();

        rel = rel.substring(1, rel.length());
        rel = rel.substring(0, rel.length() - 1);

        Gson gson = new Gson();
        Dog dogs = gson.fromJson(rel, Dog.class);

        Image image = null;
        try {
            URL url = new URL(dogs.getUrl());
            image = ImageIO.read(url);
            ImageIcon dogIcon = new ImageIcon(image);

            if (dogIcon.getIconWidth() > 200) {
                Image img = dogIcon.getImage();
                Image imgMod = img.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
                dogIcon = new ImageIcon(imgMod);
            }

            String menuDogService = "Options: \n"
                    + "1.- Watch other image \n"
                    + "2.- Favorites \n"
                    + "3.- Back \n";

            String[] buttons = {"Watch other image", "Favorites", "Back"};

            String id_dog = dogs.getId();

            String option = (String) JOptionPane.showInputDialog(null, menuDogService, id_dog, JOptionPane.INFORMATION_MESSAGE, dogIcon, buttons, buttons[0]);

            int selection = -1;

            for (int i = 0; i < buttons.length; i++) {
                if (option.equals(buttons[i])) {
                    selection = i;
                }
            }
            switch (selection) {
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
        try {
            OkHttpClient client = new OkHttpClient();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n\t\"image_id\":\"" + dogs.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thedogapi.com/v1/favourites")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", dogs.getAPIKEY())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void deleteFavorite(DogFav dogFav) {
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://api.thedogapi.com/v1/favourites/" + dogFav.getId())
                    .delete()
                    .addHeader("x-api-key", dogFav.getAPIKEY())
                    .build();
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void watchFavorites(Dog dogs2) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.thedogapi.com/v1/favourites")
                .get()
                .addHeader("x-api-key", dogs2.getAPIKEY())
                .build();

        Response response = client.newCall(request).execute();

        String rel = response.body().string();

        Gson gson = new Gson();

        DogFav[] dogFavArray = gson.fromJson(rel, DogFav[].class);

        if (dogFavArray.length > 0) {
            int min = 1;
            int max = dogFavArray.length;
            int random = (int) (Math.random() * ((max - min) + 1)) + min;
            int index = random - 1;

            DogFav dogFav = dogFavArray[index];

            Image image = null;
            try {
                URL url = new URL(dogFav.image.getUrl());
                image = ImageIO.read(url);
                ImageIcon dogIcon = new ImageIcon(image);

                if (dogIcon.getIconWidth() > 200) {
                    Image img = dogIcon.getImage();
                    Image imgMod = img.getScaledInstance(400, 300, java.awt.Image.SCALE_SMOOTH);
                    dogIcon = new ImageIcon(imgMod);
                }

                String menuDogService = "Options: \n"
                        + "1.- Watch other image \n"
                        + "2.- Delete favorite \n"
                        + "3.- Back \n";

                String[] buttons = {"Watch other image", "Delete favorite", "Back"};

                String id_dog = dogFav.getId();

                String option = (String) JOptionPane.showInputDialog(null, menuDogService, id_dog, JOptionPane.INFORMATION_MESSAGE, dogIcon, buttons, buttons[0]);

                int selection = -1;

                for (int i = 0; i < buttons.length; i++) {
                    if (option.equals(buttons[i])) {
                        selection = i;
                    }
                }
                switch (selection) {
                    case 0:
                        watchFavorites(dogs2);
                    case 1:
                        deleteFavorite(dogFav);
                    default:
                        break;
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }
}
