package dispo.train.fcba.cl.flotatrainmovil.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import dispo.train.fcba.cl.flotatrainmovil.models.Flota;

/**
 * Created by capacita on 04-05-2017.
 */

public class DispoAdaptador extends RecyclerView.Adapter<DispoAdaptador.ItemsViewHolder> {

    private List<Disponibilidad> datos;

    public DispoAdaptador(List<Disponibilidad> datos) {
        this.datos = datos;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.lisitem_dispo, parent, false);
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
        private TextView tvNombre;
        private TextView tvDetalle;
        private TextView tvPorcentaje;
        public ItemsViewHolder(View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.tvNombre);
            tvDetalle = (TextView) itemView.findViewById(R.id.tvDetalle);
            tvPorcentaje =(TextView)itemView.findViewById(R.id.tvPorcentaje);
            //instancias
        }
        public void  bindTitular(Disponibilidad item){
            //set info (nombre,detalle y porcentaje)
            //setear bg tvsegun porcentaje
            tvNombre.setText(item.getPERIODO());
            tvDetalle.setText(item.getTEXTO());
            tvPorcentaje.setText(item.getPORC_DISPO()+" %");
            tvPorcentaje.setBackgroundColor(Color.parseColor("#" + item.getCOLOR()));

        }
    }
    public void swap(List<Disponibilidad> datas){
        datos.clear();
        datos.addAll(datas);
        notifyDataSetChanged();
    }

}
