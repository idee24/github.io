package ng.emedic.emedic_mobile.networking.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Patient implements Serializable {
    @SerializedName("id")
    private int patientId;
    @SerializedName("owner")
    private int userId;
    @SerializedName("created_on")
    private Date dateCreated;
    @SerializedName("modified_on")
    private Date dateModified;
    @SerializedName("blood_group")
    private String bloodGroup;
    private String genotype;
    @SerializedName("medical_related_issues")
    private String medicalIssues;
    private String weight;
    private String disability;
    @SerializedName("date_of_birth")
    private Date dateOfBirth;
    @SerializedName("marital_status")
    private String maritalStatus;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getMedicalIssues() {
        return medicalIssues;
    }

    public void setMedicalIssues(String medicalIssues) {
        this.medicalIssues = medicalIssues;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", userId=" + userId +
                ", dateCreated='" + dateCreated + '\'' +
                ", dateModified='" + dateModified + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", genotype='" + genotype + '\'' +
                ", medicalIssues='" + medicalIssues + '\'' +
                ", weight='" + weight + '\'' +
                ", disability='" + disability + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return patientId == patient.patientId;
    }
}
