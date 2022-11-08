package dispo.train.fcba.cl.flotatrainmovil.interfaces;

import java.util.ArrayList;
import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.models.Flota;
import dispo.train.fcba.cl.flotatrainmovil.models.Helper;
import dispo.train.fcba.cl.flotatrainmovil.models.Login;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author  PekoHome on 12-02-16.
 */
public interface APIServiceFlota {

    @GET("lisFlotaMovil")
    Call<List<Flota>> lisFlota(
            @Query("tipo") String tipo,
            @Query("marca") String marca);

    @GET("getCantMarcas")
    Call<Flota>getCantMarcas(
            @Query("tipo") String tipo);

    @GET("getHoraServidor")
    Call<List<Helper>>getHoraServidor();

}
