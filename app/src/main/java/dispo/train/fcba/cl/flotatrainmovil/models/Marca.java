package dispo.train.fcba.cl.flotatrainmovil.models;

/**
 * Created by capacita on 28-04-2017.
 */

public class Marca {
    private String PK_ID_VEHICULO;
    private String PATENTE;
    private String ANHO;
    private String COD_TIPO;
    private String COD_SUB_TIPO;
    private String FK_ID_SERVICIO;
    private String COD_MARCA;
    private String MODELO;
    private String ULTIMO_EVENTO_MARCA;
    private String ULTIMO_EVENTO_DESMARCA;

    private String FECHA_EVENTO;
    private String OBSERVACION;
    private String USUARIO;

    public String getANHO() {
        return ANHO;
    }

    public String getFECHA_EVENTO() {
        return FECHA_EVENTO;
    }

    public String getOBSERVACION() {
        return OBSERVACION;
    }

    public String getUSUARIO() {
        return USUARIO;
    }

    public String getPK_ID_VEHICULO() {
        return PK_ID_VEHICULO;
    }

    public String getPATENTE() {
        return PATENTE;
    }

    public String getCOD_TIPO() {
        return COD_TIPO;
    }

    public String getCOD_SUB_TIPO() {
        return COD_SUB_TIPO;
    }

    public String getFK_ID_SERVICIO() {
        return FK_ID_SERVICIO;
    }

    public String getCOD_MARCA() {
        return COD_MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public String getULTIMO_EVENTO_MARCA() {
        return ULTIMO_EVENTO_MARCA;
    }

    public String getULTIMO_EVENTO_DESMARCA() {
        return ULTIMO_EVENTO_DESMARCA;
    }
}

