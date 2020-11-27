package com.example.carrentalmobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class AnnoucedCars implements Parcelable {

    @SerializedName("username")
    public String username;

    @SerializedName("brandname")
    public String brandname;

    @SerializedName("carname")
    public String carname;

    @SerializedName("seatcount")
    public String seatcount;

    @SerializedName("typename")
    public String typename;

    @SerializedName("description")
    public String description;

    @SerializedName("title")
    public String title;

    @SerializedName("imgFileName")
    public String filepath;

    public AnnoucedCars(String username, String brandname, String carname, String seatcount, String typename, String description, String title, String filepath) {
        this.username = username;
        this.brandname = brandname;
        this.carname = carname;
        this.seatcount = seatcount;
        this.typename = typename;
        this.description = description;
        this.title = title;
        this.filepath = filepath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCarname() {
        return carname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCarname(String carname) {
        this.carname = carname;
    }

    public String getSeatcount() {
        return seatcount;
    }

    public void setSeatcount(String seatcount) {
        this.seatcount = seatcount;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Creator<AnnoucedCars> getCREATOR() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "Cars{" +
                "username='" + username + '\'' +
                ", brandname='" + brandname + '\'' +
                ", carname='" + carname + '\'' +
                ", seatcount='" + seatcount + '\'' +
                ", typename='" + typename + '\'' +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    protected AnnoucedCars(Parcel in) {
        username = in.readString();
        brandname = in.readString();
        carname = in.readString();
        seatcount = in.readString();
        typename = in.readString();
        description = in.readString();
        title = in.readString();
    }

    public static final Creator<AnnoucedCars> CREATOR = new Creator<AnnoucedCars>() {
        @Override
        public AnnoucedCars createFromParcel(Parcel in) {
            return new AnnoucedCars(in);
        }

        @Override
        public AnnoucedCars[] newArray(int size) {
            return new AnnoucedCars[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(brandname);
        dest.writeString(carname);
        dest.writeString(seatcount);
        dest.writeString(typename);
        dest.writeString(description);
        dest.writeString(title);
    }
}
