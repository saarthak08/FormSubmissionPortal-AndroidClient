package com.sg.formsubmissionportal_androidclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Form implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("formCode")
    @Expose
    private String formCode;
    @SerializedName("department")
    @Expose
    private String department;
    public final static Parcelable.Creator<Form> CREATOR = new Creator<Form>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Form createFromParcel(Parcel in) {
            return new Form(in);
        }

        public Form[] newArray(int size) {
            return (new Form[size]);
        }

    };

    protected Form(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.formCode = ((String) in.readValue((String.class.getClassLoader())));
        this.department = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Form() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(title);
        dest.writeValue(formCode);
        dest.writeValue(department);
    }

    public int describeContents() {
        return 0;
    }
}