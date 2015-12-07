package com.example.ipo.crudandroidmysql;

/**
 * Created by ipo on 01/12/15.
 */
public class Karyawan {
    private String id_kar;
    private String nama_kar;
    private String alamat_kar;
    private String email_kar;

    public String getId_kar() {
        return id_kar;
    }

    public void setId_kar(String id_kar) {
        this.id_kar = id_kar;
    }

    public String getNama_kar() {
        return nama_kar;
    }

    public void setNama_kar(String nama_kar) {
        this.nama_kar = nama_kar;
    }

    public String getAlamat_kar() {
        return alamat_kar;
    }

    public void setAlamat_kar(String alamat_kar) {
        this.alamat_kar = alamat_kar;
    }

    public String getEmail_kar() {
        return email_kar;
    }

    public void setEmail_kar(String email_kar) {
        this.email_kar = email_kar;
    }
}
