package dispo.train.fcba.cl.flotatrainmovil.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.models.Flota;

/**
 * Created by capacita on 21-04-2017.
 */

public class lvFlotaAdapter extends BaseAdapter{

    //variables
    private List<Flota> listFlota;
    private LayoutInflater inflater;
    private Context context;

    public lvFlotaAdapter(Context context, List<Flota> listFlota) {
        this.listFlota = listFlota;
        this.context= context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return getListFlota().size();
    }

    @Override
    public Flota getItem(int position) {
        return getListFlota().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        final Flota item = getItem(position);
        if(view == null || view.getTag() == null){
            holder = new ViewHolder();
            if(item.getPK_ID_VEHICULO().isEmpty()){
                view = inflater.inflate(R.layout.listitem_vacio, parent, false);
            }else{
                view = inflater.inflate(R.layout.listitem_cuatro_columnas, parent, false);
                //instancia
                holder.item1 = (TextView) view.findViewById(R.id.tvItem1);
                holder.item2 = (TextView) view.findViewById(R.id.tvItem2);
                holder.item3 = (TextView) view.findViewById(R.id.tvItem3);
                holder.item4 = (TextView) view.findViewById(R.id.tvItem4);
                view.setTag(holder);
                holder.item1.setText(item.getPK_ID_VEHICULO());
                holder.item2.setText(item.getPATENTE());
                holder.item3.setText(item.getLUGAR_REPARACION());
                    Log.d("PRUEBA",item.getLUGAR_REPARACION());
                    Toast.makeText(context,item.getLUGAR_REPARACION(),Toast.LENGTH_SHORT);

                String duracion="";
                if(!item.getDIAS_DURACION().equals("0")){
                    duracion+=item.getDIAS_DURACION()+" dias";
                }
                if(!item.getHRS_DURACION().equals("0")){
                    duracion+=" "+item.getHRS_DURACION();
                }
                if(!item.getMIN_DURACION().equals("0")){
                    duracion+=":"+item.getMIN_DURACION();
                }
                holder.item4.setText(duracion);
            }
        } else {
            holder = (ViewHolder) view.getTag();
            if(!item.getPK_ID_VEHICULO().isEmpty()){
                holder.item1.setText(item.getPK_ID_VEHICULO());
                holder.item2.setText(item.getPATENTE());
                holder.item3.setText(item.getLUGAR_REPARACION());
                String duracion="";
                if(!item.getDIAS_DURACION().equals("0")){
                    duracion+=item.getDIAS_DURACION()+" dias";
                }
                if(!item.getHRS_DURACION().equals("0")){
                    duracion+=" "+item.getHRS_DURACION();
                }
                if(!item.getMIN_DURACION().equals("0")){
                    duracion+=":"+item.getMIN_DURACION();
                }
                holder.item4.setText(duracion);
            }
        }
        return view;
    }

    public List<Flota> getListFlota() {
        return listFlota;
    }

    public void setListFlota(List<Flota> listFlota) {
        this.listFlota = listFlota;
        this.notifyDataSetChanged();
    }

    private static class ViewHolder {
        public TextView item1;
        public TextView item2;
        public TextView item3;
        public TextView item4;
    }
}
