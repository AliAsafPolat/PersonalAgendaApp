package com.example.ders1;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Not implements Parcelable, Serializable {
    private String icerik;
    private String olusturulmaTarihi;
    private String olusturanKisi;
    private String baslik;


    public Not(String icerik,String kisi,String baslik,String zaman){
        this.baslik = baslik;
        this.icerik=icerik;
        this.olusturanKisi = kisi;
        this.olusturulmaTarihi = zaman;
    }


    protected Not(Parcel in) {
        icerik = in.readString();
        olusturanKisi = in.readString();
        baslik = in.readString();
        olusturulmaTarihi = in.readString();
    }

    public static final Creator<Not> CREATOR = new Creator<Not>() {
        @Override
        public Not createFromParcel(Parcel in) {
            return new Not(in);
        }

        @Override
        public Not[] newArray(int size) {
            return new Not[size];
        }
    };

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }

    public String getOlusturulmaTarihi() {
        return olusturulmaTarihi;
    }

    public void setOlusturulmaTarihi(String olusturulmaTarihi) {
        this.olusturulmaTarihi = olusturulmaTarihi;
    }

    public String getOlusturanKisi() {
        return olusturanKisi;
    }

    public void setOlusturanKisi(String olusturanKisi) {
        this.olusturanKisi = olusturanKisi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(icerik);
        dest.writeString(olusturanKisi);
        dest.writeString(baslik);
        dest.writeString(olusturulmaTarihi);
    }
}
