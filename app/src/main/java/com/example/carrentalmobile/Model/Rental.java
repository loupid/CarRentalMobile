package com.example.carrentalmobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rental implements Parcelable {
    @SerializedName("idrent")
    @Expose
    public String idRent;
    @SerializedName("idannounce")
    @Expose
    public String idAnnounce;
    @SerializedName("iduserclient")
    @Expose
    public String idUser;

    protected Rental(Parcel in) {
        idRent = in.readString();
        idAnnounce = in.readString();
        idUser = in.readString();
    }

    public static final Creator<Rental> CREATOR = new Creator<Rental>() {
        @Override
        public Rental createFromParcel(Parcel in) {
            return new Rental(in);
        }

        @Override
        public Rental[] newArray(int size) {
            return new Rental[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idRent);
        dest.writeString(idAnnounce);
        dest.writeString(idUser);
    }

    public Rental(String idRent, String idAnnounce, String idUser) {
        this.idRent = idRent;
        this.idAnnounce = idAnnounce;
        this.idUser = idUser;
    }

    public String getIdRent() {
        return idRent;
    }

    public void setIdRent(String idRent) {
        this.idRent = idRent;
    }

    public String getIdAnnounce() {
        return idAnnounce;
    }

    public void setIdAnnounce(String idAnnounce) {
        this.idAnnounce = idAnnounce;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

}
