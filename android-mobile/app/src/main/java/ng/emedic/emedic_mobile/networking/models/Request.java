package ng.emedic.emedic_mobile.networking.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {
    @SerializedName("modified_on")
    private Date dateModified;
    @SerializedName("additional_information")
    private String additionalInformation;
    @SerializedName("optional_address")
    private String optionalAddress;
    @SerializedName("is_appointment")
    private boolean appointment;
    @SerializedName("is_specialist")
    private boolean specialist;
    @SerializedName("appointment_date")
    private Date appointmentDate;
    @SerializedName("appointment_time")
    private String appointmentTime;
    private String status;
    @SerializedName("patient")
    private int patientId;
    @SerializedName("service")
    private int serviceId;

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getOptionalAddress() {
        return optionalAddress;
    }

    public void setOptionalAddress(String optionalAddress) {
        this.optionalAddress = optionalAddress;
    }

    public boolean isAppointment() {
        return appointment;
    }

    public void setAppointment(boolean appointment) {
        this.appointment = appointment;
    }

    public boolean isSpecialist() {
        return specialist;
    }

    public void setSpecialist(boolean specialist) {
        this.specialist = specialist;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "Request{" +
                "dateModified='" + dateModified + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                ", optionalAddress='" + optionalAddress + '\'' +
                ", appointment=" + appointment +
                ", specialist=" + specialist +
                ", appointmentDate='" + appointmentDate + '\'' +
                ", appointmentTime='" + appointmentTime + '\'' +
                ", status='" + status + '\'' +
                ", patientId=" + patientId +
                ", serviceId=" + serviceId +
                '}';
    }
}
