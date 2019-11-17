package com.sg.formsubmissionportal_androidclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Role implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("userType")
    @Expose
    private String userType;
    public final static Parcelable.Creator<Role> CREATOR = new Creator<Role>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Role createFromParcel(Parcel in) {
            return new Role(in);
        }

        public Role[] newArray(int size) {
            return (new Role[size]);
        }

    };
    private final static long serialVersionUID = -5621809142674827593L;

    protected Role(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.userType = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userType);
    }

    public int describeContents() {
        return 0;
    }
}
