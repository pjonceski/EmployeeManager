package mk.pjonceski.empleyeemanager.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Contains details about employee.
 * Used for local and remote retrieving employee data.
 */

public class Employee implements Parcelable {
    @SerializedName("id")
    @Expose(serialize = false)
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String biography;

    @SerializedName("company")
    private String companyName;

    @SerializedName("title")
    private String jobTitle;

    @SerializedName("avatar")
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Employee() {
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.id);
        out.writeString(this.name);
        out.writeString(this.biography);
        out.writeString(this.companyName);
        out.writeString(this.jobTitle);
        out.writeString(this.avatar);
    }

    public Employee(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.biography = in.readString();
        this.companyName = in.readString();
        this.jobTitle = in.readString();
        this.avatar = in.readString();
    }

    public final static Parcelable.Creator CREATOR = new Parcelable.Creator<Employee>() {
        @Override
        public Employee createFromParcel(Parcel parcelIn) {
            return new Employee(parcelIn);
        }

        @Override
        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };
}
