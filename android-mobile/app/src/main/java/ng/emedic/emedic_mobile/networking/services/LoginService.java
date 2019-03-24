package ng.emedic.emedic_mobile.networking.services;

import ng.emedic.emedic_mobile.networking.Routes;
import ng.emedic.emedic_mobile.networking.models.Login;
import ng.emedic.emedic_mobile.networking.response.AuthResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    @POST(Routes.LOGIN_END_POINT)
    Call<AuthResponse> login(@Body Login login);
}
