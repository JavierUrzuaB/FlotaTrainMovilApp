package dispo.train.fcba.cl.flotatrainmovil.models;

/**
 * Created by capacita on 21-04-2017.
 */

public class Flota {

    private String PK_ID_VEHICULO;
    private String PATENTE;
    private String MARCA_ACTUAL;
    private String ESTADO;
    private String DIAS_DURACION;
    private String HRS_DURACION;
    private String MIN_DURACION;
    private String CANT_MANTE;
    private String CANT_DISPO;
    private String LUGAR_REPARACION;


    public String getLUGAR_REPARACION() {
        return LUGAR_REPARACION;
    }

    public String getDIAS_DURACION() {
        return DIAS_DURACION;
    }

    public String getCANT_MANTE() {
        return CANT_MANTE;
    }

    public String getCANT_DISPO() {
        return CANT_DISPO;
    }

    public String getPK_ID_VEHICULO() {
        return PK_ID_VEHICULO;
    }

    public String getPATENTE() {
        return PATENTE;
    }

    public String getMARCA_ACTUAL() {
        return MARCA_ACTUAL;
    }

    public String getESTADO() {
        return ESTADO;
    }

    public String getHRS_DURACION() {
        return HRS_DURACION;
    }

    public String getMIN_DURACION() {
        return MIN_DURACION;
    }
}
