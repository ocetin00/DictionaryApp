package com.example.szlkapp.Modal;

import java.io.Serializable;

public class Kelime implements Serializable {

    private String kelime_id;
    private String ingilizce;
    private String turkce;


    public Kelime() {
    }

    public Kelime(String kelime_id, String ingilizce, String turkce) {
        this.kelime_id = kelime_id;
        this.ingilizce = ingilizce;
        this.turkce = turkce;
    }

    public String getKelime_id() {
        return kelime_id;
    }

    public void setKelime_id(String kelime_id) {
        this.kelime_id = kelime_id;
    }

    public String getIngilizce() {
        return ingilizce;
    }

    public void setIngilizce(String ingilizce) {
        this.ingilizce = ingilizce;
    }

    public String getTurkce() {
        return turkce;
    }

    public void setTurkce(String turkce) {
        this.turkce = turkce;
    }
}
