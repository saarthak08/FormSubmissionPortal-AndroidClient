package com.sg.formsubmissionportal_androidclient.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

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

    @SerializedName("idNumber")
    @Expose
    private String idNumber;

    @SerializedName("role")
    @Expose
    private Role role;

    @SerializedName("forms")
    @Expose
    private List<Form> forms = null;
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
    private final static long serialVersionUID = 8140769120607132652L;

    protected User(Parcel in) {
        this.id = ((Long) in.readValue((Long.class.getClassLoader())));
        this.email = ((String) in.readValue((String.class.getClassLoader())));
        this.firstName = ((String) in.readValue((String.class.getClassLoader())));
        this.lastName = ((String) in.readValue((String.class.getClassLoader())));
        this.enabled = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.idNumber = ((String) in.readValue((String.class.getClassLoader())));
        this.role = ((Role) in.readValue((Role.class.getClassLoader())));
        in.readList(this.forms, (Form.class.getClassLoader()));
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

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(email);
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(enabled);
        dest.writeValue(idNumber);
        dest.writeValue(role);
        dest.writeList(forms);
    }

    public int describeContents() {
        return 0;
    }
}

