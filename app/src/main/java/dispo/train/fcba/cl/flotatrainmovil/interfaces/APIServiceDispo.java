package dispo.train.fcba.cl.flotatrainmovil.interfaces;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import dispo.train.fcba.cl.flotatrainmovil.models.Flota;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author  PekoHome on 12-02-16.
 */
public interface APIServiceDispo {

    @GET("getDisponibilidadDia")
    Call<List<Disponibilidad>> getDisponibilidadDia(
            @Query("fecha") String fecha,
            @Query("tipo") String tipo);

    @GET("lisDispoNivel1")
    Call<List<Disponibilidad>> getLisDispoNivel1(
            @Query("fecha") String fecha,
            @Query("tipo") String tipo,
            @Query("pivote1") String pivote1);
    @GET("lisDispoNivel2")
    Call<List<Disponibilidad>> getLisDispoNivel2(
            @Query("fecha") String fecha,
            @Query("tipo") String tipo,
            @Query("pivote1") String pivote1,
            @Query("pivote2") String pivote2,
            @Query("valor_pivote1") String valor_pivote1);
}
