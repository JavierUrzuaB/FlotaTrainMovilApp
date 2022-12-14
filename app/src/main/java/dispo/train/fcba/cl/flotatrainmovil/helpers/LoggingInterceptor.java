package dispo.train.fcba.cl.flotatrainmovil.helpers;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by PekoHome on 12-02-16.
 */
public class LoggingInterceptor implements Interceptor { //agregue inteceptor apretando la lucecita roja y se agrego en helpers

    private static final String LOG_TAG = "OkHttp";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.d(LOG_TAG, String.format("--> Enviar consulta %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        Buffer requestBuffer = new Buffer();
        if (request.body() != null) {
            request.body().writeTo(requestBuffer);
            Log.d(LOG_TAG, requestBuffer.readUtf8());
        }

        Response response = chain.proceed(request);



        long t2 = System.nanoTime();
        Log.d(LOG_TAG, String.format("<-- Recibir consulta for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        MediaType contentType = response.body().contentType();
        String content = response.body().string();
        Log.d("Retrofit@Response", content);

        ResponseBody wrappedBody = ResponseBody.create(contentType, content);
        return response.newBuilder().body(wrappedBody).build();
    }
}
