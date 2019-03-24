package ng.emedic.emedic_mobile.networking.models;

public enum RequestStatus {
    PENDING("Pending"),
    ACCEPTED("Accepted"),
    DONE("Done"),
    CANCELED("Canceled");

    private String description;

    RequestStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static String getNumberString(RequestStatus requestStatus) {
        switch (requestStatus) {
            case PENDING:
                return "1";
            case ACCEPTED:
                return "2";
            case CANCELED:
                return "3";
            case DONE:
                return "4";
            default:
                return "0";
        }
    }

    public static RequestStatus getRequestStatus(String numberString) {
        switch (numberString) {
            case "1":
                return PENDING;
            case "2":
                return  ACCEPTED;
            case "3":
                return CANCELED;
            case "4":
                return DONE;
            default:
                return null;
        }
    }

    @Override
    public String toString() {
        return description;
    }
}
