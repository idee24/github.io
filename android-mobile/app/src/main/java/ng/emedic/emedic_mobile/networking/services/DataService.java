package ng.emedic.emedic_mobile.networking.services;

import java.util.List;

import ng.emedic.emedic_mobile.networking.Routes;
import ng.emedic.emedic_mobile.networking.models.Provider;
import ng.emedic.emedic_mobile.networking.models.Request;
import ng.emedic.emedic_mobile.networking.models.Service;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService {
    @GET(Routes.SERVICE_END_POINT)
    Call<List<Service>> getServices();

    @POST(Routes.CREATE_REQUEST_END_POINT)
    Call<ResponseBody> makeRequest(@Body Request request);

    @GET(Routes.PROVIDER_END_POINT)
    Call<List<Provider>> getProviders();
}
