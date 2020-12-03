package com.example.carrentalmobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnnoucedCars implements Parcelable {

    @SerializedName("idannounce")
    @Expose
    public String idannounce;

    @SerializedName("brandname")
    @Expose
    public String brandname;

    @SerializedName("carname")
    @Expose
    public String carname;

    @SerializedName("seatcount")
    @Expose
    public String seatcount;

    @SerializedName("typename")
    @Expose
    public String typename;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("imgFileName")
    @Expose
    public String filepath;

    @SerializedName("available")
    @Expose
    public boolean available;

    @SerializedName("price")
    @Expose
    public String price;



    public AnnoucedCars(String title, String brandname, String carname, String seatcount, String typename, String description, String price, String filepath, boolean available) {
        this.brandname = brandname;
        this.carname = carname;
        this.seatcount = seatcount;
        this.typename = typename;
        this.description = description;
        this.title = title;
        this.filepath = filepath;
        this.price = price;
        this.available = available;
    }

    protected AnnoucedCars(Parcel in) {
        idannounce = in.readString();
        brandname = in.readString();
        carname = in.readString();
        seatcount = in.readString();
        typename = in.readString();
        description = in.readString();
        title = in.readString();
        filepath = in.readString();
        available = in.readByte() != 0;
        price = in.readString();
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

    public String getIdannounce() {
        return idannounce;
    }

    public void setIdannounce(String idannounce) {
        this.idannounce = idannounce;
    }

    public String getBrandname() {
        return brandname;
    }

    public String getFilepath() {
        return "Images/" + filepath;
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

    //todo: check if availbe to be displayed in the announce list
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(idannounce);
        dest.writeString(brandname);
        dest.writeString(carname);
        dest.writeString(seatcount);
        dest.writeString(typename);
        dest.writeString(description);
        dest.writeString(title);
        dest.writeString(filepath);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeString(price);
    }
}
