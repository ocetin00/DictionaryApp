package com.example.szlkapp.Modal;

import java.util.List;
import java.util.Map;

public class Kisi {
    private String kisi_id;
    private String kisi_email;
    private List<String> kelimes_id;

    public Kisi() {
    }

    public Kisi(String kisi_id, String kisi_email, List<String> kelimes_id) {
        this.kisi_id = kisi_id;
        this.kisi_email = kisi_email;
        this.kelimes_id = kelimes_id;
    }

    public String getKisi_id() {
        return kisi_id;
    }

    public void setKisi_id(String kisi_id) {
        this.kisi_id = kisi_id;
    }

    public String getKisi_email() {
        return kisi_email;
    }

    public void setKisi_email(String kisi_email) {
        this.kisi_email = kisi_email;
    }

    public List<String> getKelimes_id() {
        return kelimes_id;
    }

    public void setKelimes_id(List<String> kelimes_id) {
        this.kelimes_id = kelimes_id;
    }
}
