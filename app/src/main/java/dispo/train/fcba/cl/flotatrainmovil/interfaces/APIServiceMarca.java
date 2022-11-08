package dispo.train.fcba.cl.flotatrainmovil.interfaces;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.models.Flota;
import dispo.train.fcba.cl.flotatrainmovil.models.Helper;
import dispo.train.fcba.cl.flotatrainmovil.models.LugarReparacion;
import dispo.train.fcba.cl.flotatrainmovil.models.Marca;
import dispo.train.fcba.cl.flotatrainmovil.models.Tipo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * @author  PekoHome on 12-02-16.
 */
public interface APIServiceMarca {

    @GET("getInfoParaMarca")
    Call<List<Marca>> getInfoParaMarca(
            @Query("id_vehiculo") String id_vehiculo);
    @GET("setMarca")
    Call<Helper> setMarca(
            @Query("id_vehiculo") String id_vehiculo,
            @Query("fecha") String fecha,
            @Query("hora") String hora,
            @Query("observacion") String observacion,
            @Query("usuario") String usuario,
            @Query("lugar_rep") String lugar_rep,
            @Query("tipo_rep") String tipo_rep);
    @GET("getInfoParaDesmarca")
    Call<List<Marca>> getInfoParaDesmarca(
            @Query("id_vehiculo") String id_vehiculo);
    @GET("setDesmarca")
    Call<Helper> setDesmarca(
            @Query("id_vehiculo") String id_vehiculo,
            @Query("fecha") String fecha,
            @Query("hora") String hora,
            @Query("observacion") String observacion,
            @Query("usuario") String usuario);
    @GET("getLugaresReparacion")
    Call<List<LugarReparacion>> getLugaresReparacion();
    @GET("getTiposReparacion")
    Call<List<Tipo>> getTiposReparacion();
}
