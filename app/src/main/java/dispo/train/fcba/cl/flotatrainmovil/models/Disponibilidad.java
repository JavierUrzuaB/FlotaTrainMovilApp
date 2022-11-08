package dispo.train.fcba.cl.flotatrainmovil.models;

import java.io.Serializable;

/**
 * Created by capacita on 12-05-2017.
 */

public class Disponibilidad implements Serializable{
    public String ORDEN;
    public String FECHA;
    public String PERIODO;
    public String PORC_DISPO;
    public String TEXTO;
    public String COLOR;


    //nivel1
    // {"FECHA":"12\/05\/2017","CATEGORIA_N1":"SIN_SERVICIO","HORA":"23:59","FLOTA_TXT":"79 de 86","DISPO_ACTUAL":"91.86","DISPO_ACTUAL_COLOR":"66FF00","DISPO_PARCIAL":"100","DISPO_PARCIAL_COLOR":"66FF00",
    // "DIPO_MES":"100","DISPO_MES_TXT":"MAYO","DISPO_MES_COLOR":"66FF00","DIPO_ANHO":"100","DISPO_ANHO_TXT":"2017","DISPO_ANHO_COLOR":"66FF00","ERROR":null
    public String COD_CATEGORIA_N1;
    public String CATEGORIA_N1;
    public String CATEGORIA_TXT;
    public String HORA;
    public String FLOTA_TXT;
    public String DISPO_ACTUAL;
    public String DISPO_ACTUAL_COLOR;
    public String DISPO_PARCIAL;
    public String DISPO_PARCIAL_COLOR;
    public String DISPO_MES;
    public String DISPO_MES_TXT;
    public String DISPO_MES_COLOR;
    public String DISPO_ANHO;
    public String DISPO_ANHO_TXT;
    public String DISPO_ANHO_COLOR;

    public Disponibilidad(String FECHA, String FLOTA_TXT, String DISPO_ACTUAL, String DISPO_ACTUAL_COLOR, String DISPO_PARCIAL, String DISPO_PARCIAL_COLOR, String DISPO_MES, String DISPO_MES_TXT, String DISPO_MES_COLOR, String DISPO_ANHO, String DISPO_ANHO_TXT, String DISPO_ANHO_COLOR) {
        this.FECHA = FECHA;
        this.CATEGORIA_N1 = CATEGORIA_N1;
        this.FLOTA_TXT = FLOTA_TXT;
        this.DISPO_ACTUAL = DISPO_ACTUAL;
        this.DISPO_ACTUAL_COLOR = DISPO_ACTUAL_COLOR;
        this.DISPO_PARCIAL = DISPO_PARCIAL;
        this.DISPO_PARCIAL_COLOR = DISPO_PARCIAL_COLOR;
        this.DISPO_MES = DISPO_MES;
        this.DISPO_MES_TXT = DISPO_MES_TXT;
        this.DISPO_MES_COLOR = DISPO_MES_COLOR;
        this.DISPO_ANHO = DISPO_ANHO;
        this.DISPO_ANHO_TXT = DISPO_ANHO_TXT;
        this.DISPO_ANHO_COLOR = DISPO_ANHO_COLOR;
    }

    public String getCATEGORIA_TXT() {
        return CATEGORIA_TXT;
    }

    public String getCOD_CATEGORIA_N1() {
        return COD_CATEGORIA_N1;
    }

    public String getORDEN() {
        return ORDEN;
    }

    public String getFECHA() {
        return FECHA;
    }

    public String getPERIODO() {
        return PERIODO;
    }

    public String getPORC_DISPO() {
        return PORC_DISPO;
    }

    public String getTEXTO() {
        return TEXTO;
    }

    public String getCOLOR() {
        return COLOR;
    }

    public String getCATEGORIA_N1() {
        return CATEGORIA_N1;
    }

    public String getHORA() {
        return HORA;
    }

    public String getFLOTA_TXT() {
        return FLOTA_TXT;
    }

    public String getDISPO_ACTUAL() {
        return DISPO_ACTUAL;
    }

    public String getDISPO_ACTUAL_COLOR() {
        return DISPO_ACTUAL_COLOR;
    }

    public String getDISPO_PARCIAL() {
        return DISPO_PARCIAL;
    }

    public String getDISPO_PARCIAL_COLOR() {
        return DISPO_PARCIAL_COLOR;
    }

    public String getDISPO_MES() {
        return DISPO_MES;
    }

    public String getDISPO_MES_TXT() {
        return DISPO_MES_TXT;
    }

    public String getDISPO_MES_COLOR() {
        return DISPO_MES_COLOR;
    }

    public String getDISPO_ANHO() {
        return DISPO_ANHO;
    }

    public String getDISPO_ANHO_TXT() {
        return DISPO_ANHO_TXT;
    }

    public String getDISPO_ANHO_COLOR() {
        return DISPO_ANHO_COLOR;
    }

    public void setFECHA(String FECHA) {
        this.FECHA = FECHA;
    }

    public void setFLOTA_TXT(String FLOTA_TXT) {
        this.FLOTA_TXT = FLOTA_TXT;
    }

    public void setDISPO_ACTUAL(String DISPO_ACTUAL) {
        this.DISPO_ACTUAL = DISPO_ACTUAL;
    }

    public void setDISPO_ACTUAL_COLOR(String DISPO_ACTUAL_COLOR) {
        this.DISPO_ACTUAL_COLOR = DISPO_ACTUAL_COLOR;
    }

    public void setDISPO_PARCIAL(String DISPO_PARCIAL) {
        this.DISPO_PARCIAL = DISPO_PARCIAL;
    }

    public void setDISPO_PARCIAL_COLOR(String DISPO_PARCIAL_COLOR) {
        this.DISPO_PARCIAL_COLOR = DISPO_PARCIAL_COLOR;
    }

    public void setDISPO_MES(String DISPO_MES) {
        this.DISPO_MES = DISPO_MES;
    }

    public void setDISPO_MES_TXT(String DISPO_MES_TXT) {
        this.DISPO_MES_TXT = DISPO_MES_TXT;
    }

    public void setDISPO_MES_COLOR(String DISPO_MES_COLOR) {
        this.DISPO_MES_COLOR = DISPO_MES_COLOR;
    }

    public void setDISPO_ANHO(String DISPO_ANHO) {
        this.DISPO_ANHO = DISPO_ANHO;
    }

    public void setDISPO_ANHO_TXT(String DISPO_ANHO_TXT) {
        this.DISPO_ANHO_TXT = DISPO_ANHO_TXT;
    }

    public void setDISPO_ANHO_COLOR(String DISPO_ANHO_COLOR) {
        this.DISPO_ANHO_COLOR = DISPO_ANHO_COLOR;
    }
}
