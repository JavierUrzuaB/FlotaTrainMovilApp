package dispo.train.fcba.cl.flotatrainmovil.interfaces;

import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;

/**
 * Created by capacita on 23-05-2017.
 */

public interface HelperInterface {
    void onItemSelected(String tipo, String pivote1, String pivote2, String valorPivote1, Disponibilidad disponibilidad);
}
