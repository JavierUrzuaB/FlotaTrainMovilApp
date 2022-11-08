package dispo.train.fcba.cl.flotatrainmovil.interfaces;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import dispo.train.fcba.cl.flotatrainmovil.models.LoginNotif;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author  PekoHome on 12-02-16.
 */
public interface APIServiceLoginNotif {

    @GET("loginApp")
    Call<LoginNotif> loginNotif(
            @Query("usuario") String usuario,
            @Query("mobile_key") String mobile_key,
            @Query("categoria") String categoria,
            @Query("sub_categoria") String sub_categoria,
            @Query("app") String app);
    @GET("setEstadoNotif")
    Call<LoginNotif> setEstadoNotif(
            @Query("usuario") String usuario,
            @Query("mobile_key") String mobile_key,
            @Query("app") String app,
            @Query("estado") String estado
    );
}
