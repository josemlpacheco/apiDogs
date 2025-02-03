/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dogs_api;

/**
 *
 * @author jo
 */
public class DogFav {
    String id;
    String image_id;
    String APIKEY = "live_ASINuH2fDjpLCCCMysmGJBgmQAOckYf9dBm6SNGRkCxiGomy8vTqIOW4MqLEwd3j";
    ImageDogFav image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getAPIKEY() {
        return APIKEY;
    }

    public void setAPIKEY(String APIKEY) {
        this.APIKEY = APIKEY;
    }

    public ImageDogFav getImage() {
        return image;
    }

    public void setImage(ImageDogFav image) {
        this.image = image;
    }
}
