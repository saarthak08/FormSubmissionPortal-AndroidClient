package com.sg.formsubmissionportal_androidclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable, Parcelable {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("enabled")
    @Expose
    private Boolean enabled;
    @SerializedName("facultyNumber")
    @Expose
    private String facultyNumber;
    @SerializedName("employeeNumber")
    @Expose
    private String employeeNumber;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("forms")
    @Expose
    private Object forms;
    public final static Parcelable.Creator<User> CREATOR = new Creator<User>() {


        @SuppressWarnings({
                "unchecked"
        })
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return (new User[size]);
        }

    };
    private final static long serialVersionUID = -1011084854772765539L;

    protected User(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.enabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.facultyNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.employeeNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.role = ((String) in.readValue((String.class.getClassLoader())));
        this.forms = ((Object) in.readValue((Object.class.getClassLoader())));
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Object getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(String facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public Object getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Object getForms() {
        return forms;
    }

    public void setForms(Object forms) {
        this.forms = forms;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(email);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(enabled);
        dest.writeValue(facultyNumber);
        dest.writeValue(employeeNumber);
        dest.writeValue(role);
        dest.writeValue(forms);
    }

    public int describeContents() {
        return 0;
    }
}

