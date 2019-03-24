package ng.emedic.emedic_mobile.networking.generators;

import android.content.Context;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import ng.emedic.emedic_mobile.BuildConfig;
import ng.emedic.emedic_mobile.networking.Routes;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    public static <S> S createService(Class<S> serviceClass, Context context) {
        // Building retrofit
        GsonBuilder builder = new GsonBuilder();
        builder.serializeNulls();
        builder.setDateFormat("yyyy-MM-dd");
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(builder.create()))
                .baseUrl(Routes.BASE_URL);

        // Building Http Client
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);

        // Adding Logging Capability at Dev/Debug Mode
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(loggingInterceptor);
        }

        retrofitBuilder.client(httpClientBuilder.build());
        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }
}
