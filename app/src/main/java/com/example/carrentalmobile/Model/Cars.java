package com.example.carrentalmobile.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Cars implements Parcelable {

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

    public Cars(String username, String brandname, String carname, String seatcount, String typename, String description) {
        this.username = username;
        this.brandname = brandname;
        this.carname = carname;
        this.seatcount = seatcount;
        this.typename = typename;
        this.description = description;
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

    public void setBrandname(String brandname) {
        this.brandname = brandname;
    }

    public String getCarname() {
        return carname;
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

    public static Creator<Cars> getCREATOR() {
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
                '}';
    }

    protected Cars(Parcel in) {
        username = in.readString();
        brandname = in.readString();
        carname = in.readString();
        seatcount = in.readString();
        typename = in.readString();
        description = in.readString();
    }

    public static final Creator<Cars> CREATOR = new Creator<Cars>() {
        @Override
        public Cars createFromParcel(Parcel in) {
            return new Cars(in);
        }

        @Override
        public Cars[] newArray(int size) {
            return new Cars[size];
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
    }
}
