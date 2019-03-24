package ng.emedic.emedic_mobile.networking.response;

import java.io.Serializable;

import ng.emedic.emedic_mobile.networking.models.Patient;
import ng.emedic.emedic_mobile.networking.models.User;

public class AuthResponse implements Serializable {
    private String message;
    private String token;
    private User user;
    private Patient patient;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "message='" + message + '\'' +
                ", token='" + token + '\'' +
                ", user=" + user +
                //", patient=" + patient +
                '}';
    }
}
