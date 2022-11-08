package dispo.train.fcba.cl.flotatrainmovil.interfaces;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.models.Login;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author  PekoHome on 12-02-16.
 */
public interface APIServiceLogin {

    @GET("login")
    Call<List<Login>> getLogin(
            @Query("usuario") String usuario,
            @Query("password") String password);
}
