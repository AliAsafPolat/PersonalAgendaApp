package com.example.ders1;

import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;

public class Person {
    private String userName;
    private String password;
    private int resim;
    private String password_temp;

    public Person(String isim, String sifre, int res){
        this.userName = isim;
        this.password = sifre;
        this.resim = res;
        this.password_temp = sifre;     // Tıklayınca şifreyi * karakterleriyle göstermek lazım. History bilgisi tutulur.
    }

    public String getPassword_temp(){
        return this.password_temp;
    }

    public void setPassword_temp(String password_temp) {
        this.password_temp = password_temp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getResim() {
        return resim;
    }

    public void setResim(int resim) {
        this.resim = resim;
    }

    public static ArrayList<Person> getData(){
        ArrayList<Person> kullanicilar = new ArrayList<Person>(10);

            Person person1 = new Person("Ahmet","1234",R.drawable.erkek1);
            Person person2 = new Person("Asım","15060",R.drawable.erkek2);
            Person person3 = new Person("Mustafa","1234",R.drawable.erkek3);
            Person person4 = new Person("Mehmet","1234",R.drawable.erkek4);
            Person person5 = new Person("Asaf","1234",R.drawable.erkek5);
            Person person6 = new Person("Melisa","1234",R.drawable.kiz1);
            Person person7 = new Person("Dilara","1234",R.drawable.kiz2);
            Person person8 = new Person("Elif","1234",R.drawable.kiz3);
            Person person9 = new Person("Ezgi","1234",R.drawable.kiz4);
            Person person10 = new Person("Tuana","1234",R.drawable.kiz5);

            kullanicilar.add(person1);
            kullanicilar.add(person2);
            kullanicilar.add(person3);
            kullanicilar.add(person4);
            kullanicilar.add(person5);
            kullanicilar.add(person6);
            kullanicilar.add(person7);
            kullanicilar.add(person8);
            kullanicilar.add(person9);
            kullanicilar.add(person10);

        return kullanicilar;
    }
}
