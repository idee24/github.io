package ng.emedic.emedic_mobile.networking.services;

import ng.emedic.emedic_mobile.networking.Routes;
import ng.emedic.emedic_mobile.networking.models.Signup;
import ng.emedic.emedic_mobile.networking.models.User;
import ng.emedic.emedic_mobile.networking.response.AuthResponse;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RegistrationService {
    @POST(Routes.REGISTRATION_END_POINT)
    Call<AuthResponse> register(@Body Signup signup);
    @PUT(Routes.UPDATE_PROFILE_END_POINT)
    Call<ResponseBody> updateProfile(@Path(Routes.ID_PATH) int id, @Body User user);
}
