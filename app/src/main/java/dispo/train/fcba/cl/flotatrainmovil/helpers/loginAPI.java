package dispo.train.fcba.cl.flotatrainmovil.helpers;

import android.util.Base64;

import java.io.IOException;

import dispo.train.fcba.cl.flotatrainmovil.interfaces.APIServiceLogin;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by PekoHome on 12-02-16.
 */
public class loginAPI {

    public static final String ENDPOINT_URL = "http://200.11.83.188/traccionmovil/inicio/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl(ENDPOINT_URL)
                    .addConverterFactory(GsonConverterFactory.create());


    private static loginAPI instance;

    private APIServiceLogin service;

    private loginAPI() {
        /* IGNORED */
    }

    public static loginAPI get() {
        if (instance == null) {
            instance = new loginAPI();
        }
        return instance;
    }

    public APIServiceLogin getRetrofitService(String user, String pass) {
        if (service == null) {
            if(user != null && pass != null) {
                String credencial = user + ":" + pass;
                final String basic = "Basic " + Base64.encodeToString(credencial.getBytes(), Base64.NO_WRAP);
                httpClient.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        Request.Builder requestBuilder = original.newBuilder()
                                .header("Authorization", basic)
                                .header("Accept", "application/json")
                                .method(original.method(), original.body());


                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                });

                httpClient.interceptors().add(new LoggingInterceptor());
                OkHttpClient client = httpClient.build();

                Retrofit retrofit = builder.client(client).build();
                service = retrofit.create(APIServiceLogin.class);

            }
        }
        return service;
    }

}
