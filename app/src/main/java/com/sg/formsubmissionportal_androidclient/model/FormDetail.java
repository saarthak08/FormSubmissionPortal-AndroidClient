package com.sg.formsubmissionportal_androidclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FormDetail implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("facultyNumber")
    @Expose
    private String facultyNumber;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("enrollmentNumber")
    @Expose
    private String enrollmentNumber;
    @SerializedName("form")
    @Expose
    private Integer form;
    public final static Parcelable.Creator<FormDetail> CREATOR = new Creator<FormDetail>() {


        @SuppressWarnings({
                "unchecked"
        })
        public FormDetail createFromParcel(Parcel in) {
            return new FormDetail(in);
        }

        public FormDetail[] newArray(int size) {
            return (new FormDetail[size]);
        }

    }
            ;
    private final static long serialVersionUID = -294036954477263369L;

    protected FormDetail(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.facultyNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.phoneNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.enrollmentNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.form = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public FormDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public Integer getForm() {
        return form;
    }

    public void setForm(Integer form) {
        this.form = form;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(facultyNumber);
        dest.writeValue(email);
        dest.writeValue(phoneNumber);
        dest.writeValue(enrollmentNumber);
        dest.writeValue(form);
    }

    public int describeContents() {
        return 0;
    }

}