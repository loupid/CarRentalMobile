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

    @SerializedName("location")
    @Expose
    public String location;

    @SerializedName("carname")
    @Expose
    public String carname;

    @SerializedName("seatcount")
    @Expose
    public String seatcount;

    @SerializedName("category")
    @Expose
    public String category;

    @SerializedName("description")
    @Expose
    public String description;

    @SerializedName("title")
    @Expose
    public String title;

    @SerializedName("imgfilepath")
    @Expose
    public String filepath;

    @SerializedName("available")
    @Expose
    public boolean available;

    @SerializedName("price")
    @Expose
    public String price;

    public AnnoucedCars(String title, String brandname, String location, String carname, String seatcount, String category, String description, String filepath, boolean available, String price) {
        this.brandname = brandname;
        this.location = location;
        this.carname = carname;
        this.seatcount = seatcount;
        this.category = category;
        this.description = description;
        this.title = title;
        this.filepath = filepath;
        this.available = available;
        this.price = price;
    }

    protected AnnoucedCars(Parcel in) {
        idannounce = in.readString();
        brandname = in.readString();
        location = in.readString();
        carname = in.readString();
        seatcount = in.readString();
        category = in.readString();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price + " $/jour";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    //todo: check if availbe to be displayed in the announce list
    //TODO$WH yo la query filtre deja les voiture pour faire afficher que les voitures available parf
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idannounce);
        parcel.writeString(brandname);
        parcel.writeString(location);
        parcel.writeString(carname);
        parcel.writeString(seatcount);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(title);
        parcel.writeString(filepath);
        parcel.writeByte((byte) (available ? 1 : 0));
        parcel.writeString(price);
    }
}
