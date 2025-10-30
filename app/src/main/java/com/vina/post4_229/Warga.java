package com.vina.post4_229;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "warga")
public class Warga {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nama;
    private String nik;
    private String kabupaten;
    private String kecamatan;
    private String desa;
    private String rt;
    private String rw;
    private String jenisKelamin;
    private String statusPernikahan;

    // Constructor tanpa parameter (wajib untuk Room)
    public Warga() {
    }

    // Constructor dengan parameter
    public Warga(String nama, String nik, String kabupaten, String kecamatan,
                 String desa, String rt, String rw, String jenisKelamin, String statusPernikahan) {
        this.nama = nama;
        this.nik = nik;
        this.kabupaten = kabupaten;
        this.kecamatan = kecamatan;
        this.desa = desa;
        this.rt = rt;
        this.rw = rw;
        this.jenisKelamin = jenisKelamin;
        this.statusPernikahan = statusPernikahan;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getNik() { return nik; }
    public void setNik(String nik) { this.nik = nik; }

    public String getKabupaten() { return kabupaten; }
    public void setKabupaten(String kabupaten) { this.kabupaten = kabupaten; }

    public String getKecamatan() { return kecamatan; }
    public void setKecamatan(String kecamatan) { this.kecamatan = kecamatan; }

    public String getDesa() { return desa; }
    public void setDesa(String desa) { this.desa = desa; }

    public String getRt() { return rt; }
    public void setRt(String rt) { this.rt = rt; }

    public String getRw() { return rw; }
    public void setRw(String rw) { this.rw = rw; }

    public String getJenisKelamin() { return jenisKelamin; }
    public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public String getStatusPernikahan() { return statusPernikahan; }
    public void setStatusPernikahan(String statusPernikahan) { this.statusPernikahan = statusPernikahan; }
}