package dispo.train.fcba.cl.flotatrainmovil.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;

/**
 * Created by capacita on 04-05-2017.
 */

public class DispoNivelesAdaptador extends RecyclerView.Adapter<DispoNivelesAdaptador.ItemsViewHolder> {

    private List<Disponibilidad> datos;

    public DispoNivelesAdaptador(List<Disponibilidad> datos) {
        this.datos = datos;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.lisitem_categoria, parent, false);
        ItemsViewHolder tvh= new ItemsViewHolder(itemView);
        return tvh;
    }

    @Override
    public void onBindViewHolder(ItemsViewHolder holder, int position) {
        Disponibilidad item = datos.get(position);
        holder.bindTitular(item);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }



    public class ItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitulo;
        private TextView tvFlota;
        private TextView tvActual;
        private TextView tvParcial;
        private TextView tvMensualTxt;
        private TextView tvMensual;
        private TextView tvAnualTxt;
        private TextView tvAnual;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.txFechaConsulta);
            tvFlota = (TextView) itemView.findViewById(R.id.txFlota);
            tvActual =(TextView)itemView.findViewById(R.id.tvActualR);
            tvParcial =(TextView)itemView.findViewById(R.id.tvParcialR);
            tvMensualTxt =(TextView)itemView.findViewById(R.id.tvMarzo);
            tvMensual =(TextView)itemView.findViewById(R.id.tvMarzoR);
            tvAnualTxt =(TextView)itemView.findViewById(R.id.tvAnual);
            tvAnual =(TextView)itemView.findViewById(R.id.tvAnualR);

            //instancias
        }
        public void  bindTitular(Disponibilidad item){
            //set info (nombre,detalle y porcentaje)
            //setear bg tvsegun porcentaje
            tvTitulo.setText("FECHA CONSULTA: "+item.getFECHA());
            tvFlota.setText("FLOTA: "+item.getFLOTA_TXT());
            tvActual.setText(item.getDISPO_ACTUAL());
            tvActual.setTextColor(Color.parseColor("#" + item.getDISPO_ACTUAL_COLOR()));

            tvParcial.setText(item.getDISPO_PARCIAL());
            tvParcial.setTextColor(Color.parseColor("#" + item.getDISPO_PARCIAL_COLOR()));

            tvMensualTxt.setText(item.getDISPO_MES_TXT()+": ");
            tvMensual.setText(item.getDISPO_MES());
            tvMensual.setTextColor(Color.parseColor("#" + item.getDISPO_MES_COLOR()));

            tvAnualTxt.setText(item.getDISPO_ANHO_TXT()+": ");
            tvAnual.setText(item.getDISPO_ANHO());
            tvAnual.setTextColor(Color.parseColor("#" + item.getDISPO_ANHO_COLOR()));

        }
    }
}
