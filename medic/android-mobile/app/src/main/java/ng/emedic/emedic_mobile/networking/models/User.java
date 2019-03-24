package ng.emedic.emedic_mobile.networking.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class User implements Serializable {
    private int id;
    @SerializedName("last_login")
    private String lastLogin;
    @SerializedName("is_superuser")
    private boolean superUser;
    private String username;
    @SerializedName("first_name")
    private String firstName;
    @SerializedName("last_name")
    private String lastName;
    private String email;
    @SerializedName("is_staff")
    private boolean staff;
    @SerializedName("is_active")
    private boolean active;
    @SerializedName("date_joined")
    private Date dateJoined;
    @SerializedName("other_name")
    private String otherNames;
    @SerializedName("tel_no")
    private String phone;
    @SerializedName("profile_type")
    private String profileType;
    private String address;
    private String gender;
    private List<String> groups;

    @SerializedName("picture_url")
    private String pictureUrl;
    @SerializedName("date_of_birth")
    private Date dateOfBirth;
    @SerializedName("blood_group")
    private String bloodGroup;
    private String disability;
    private String genotype;
    @SerializedName("marital_status")
    private String maritalStatus;
    private String weight;
    @SerializedName("medical_related_issues")
    private String medicalIssues;

    private int patientId;

    private boolean loggedIn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public boolean isSuperUser() {
        return superUser;
    }

    public void setSuperUser(boolean superUser) {
        this.superUser = superUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isStaff() {
        return staff;
    }

    public void setStaff(boolean staff) {
        this.staff = staff;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateJoined() {
        return dateJoined;
    }

    public void setDateJoined(Date dateJoined) {
        this.dateJoined = dateJoined;
    }

    public String getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(String otherNames) {
        this.otherNames = otherNames;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getGroups() {
        return groups;
    }

    public void setGroups(List<String> groups) {
        this.groups = groups;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDateOfBirth() {
        if (dateOfBirth == null) {
            return "";
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfBirth);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return String.format(Locale.getDefault(), "%02d-%02d-%04d", day, month, year);
    }

    public void setDateOfBirth(String dateOfBirth) {
        if (dateOfBirth == null || dateOfBirth.isEmpty()) {
            return;
        }
        String[] dateParts = dateOfBirth.split("-");
        int day = Integer.valueOf(dateParts[0].trim());
        int month = Integer.valueOf(dateParts[1].trim());
        int year = Integer.valueOf(dateParts[2].trim());
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day);
        this.dateOfBirth = calendar.getTime();
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMedicalIssues() {
        return medicalIssues;
    }

    public void setMedicalIssues(String medicalIssues) {
        this.medicalIssues = medicalIssues;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }


    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getFullName() {
        String name = "";
        name += firstName == null ? "" : firstName + " ";
        name += otherNames == null ? "" : otherNames + " ";
        name += lastName == null ? "" : lastName;
        return name.trim();
    }

    public void updatePatientDetails(Patient patient) {
        patient.setPatientId(patientId);
        patient.setUserId(id);
        patient.setDateCreated(dateJoined);
        patient.setBloodGroup(bloodGroup);
        patient.setGenotype(genotype);
        patient.setMedicalIssues(medicalIssues);
        patient.setWeight(weight);
        patient.setDisability(disability);
        patient.setDateOfBirth(dateOfBirth);
        patient.setMaritalStatus(maritalStatus);
    }

    public void updateUserDetails(Patient patient) {
        setPatientId(patient.getPatientId());
        setBloodGroup(patient.getBloodGroup());
        setGenotype(patient.getGenotype());
        setMedicalIssues(patient.getMedicalIssues());
        setWeight(patient.getWeight());
        setDisability(patient.getDisability());
        dateOfBirth = patient.getDateOfBirth();
        setMaritalStatus(patient.getMaritalStatus());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", lastLogin='" + lastLogin + '\'' +
                ", superUser=" + superUser +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", staff=" + staff +
                ", active=" + active +
                ", dateJoined='" + dateJoined + '\'' +
                ", otherNames='" + otherNames + '\'' +
                ", phone='" + phone + '\'' +
                ", profileType='" + profileType + '\'' +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                ", groups=" + groups +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", disability='" + disability + '\'' +
                ", genotype='" + genotype + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", weight='" + weight + '\'' +
                ", medicalIssues='" + medicalIssues + '\'' +
                ", loggedIn=" + loggedIn +
                '}';
    }
}
