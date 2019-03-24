package ng.emedic.emedic_mobile.networking;

import java.util.Locale;

public interface Routes {
    String BASE_URL = "https://emedic-dev.herokuapp.com/";
    String LOGIN_END_POINT = "v1/auth/login/";
    String REGISTRATION_END_POINT = "v1/auth/register/";
    String SERVICE_END_POINT = "v1/medic/services/";
    String CREATE_REQUEST_END_POINT = "v1/medic/service-requests/";
    String ID_PATH = "id";
    String UPDATE_PROFILE_END_POINT = "v1/subscriber/patients/{" + ID_PATH + "}/";
    String PROVIDER_END_POINT = "v1/medic/providers/";
}
