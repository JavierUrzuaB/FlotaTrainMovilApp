package dispo.train.fcba.cl.flotatrainmovil.helpers;

import android.util.Base64;

import java.io.IOException;

import dispo.train.fcba.cl.flotatrainmovil.interfaces.APIServiceDispo;
import dispo.train.fcba.cl.flotatrainmovil.interfaces.APIServiceLoginNotif;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Creado por peko el 25-08-17.
 */

public class loginNotifAPI {

    public static final String ENDPOINT_URL = "http://sistemas.fcab.cl/MovilNotif/index.php/Notificacion/";

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(ENDPOINT_URL)
            .addConverterFactory(GsonConverterFactory.create());


    private static loginNotifAPI instance;

    private APIServiceLoginNotif service;

    private loginNotifAPI() {
        /* IGNORED */
    }

    public static loginNotifAPI get() {
        if (instance == null) {
            instance = new loginNotifAPI();
        }
        return instance;
    }

    public APIServiceLoginNotif getRetrofitService(String user, String pass) {
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
                service = retrofit.create(APIServiceLoginNotif.class);

            }
        }
        return service;
    }

}
