package dispo.train.fcba.cl.flotatrainmovil.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.interfaces.HelperInterface;
import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import dispo.train.fcba.cl.flotatrainmovil.views.CategoriasActivity;

/**
 * Created by capacita on 04-05-2017.
 */

public class DispoNivel1Adaptador extends RecyclerView.Adapter<DispoNivel1Adaptador.ItemsViewHolder> {

    private List<Disponibilidad> datos;
    private String tipo;
    private String tab;
    private String pivote2;
    private String pivote1;
    private boolean isNivel2;
    private boolean isPivote;

    private Context context;
    private HelperInterface yourIntrface;


    public DispoNivel1Adaptador(List<Disponibilidad> datos,String tipo,String tab,boolean isNivel2, boolean isPivote) {
        this.datos = datos;
        this.tipo = tipo;
        this.tab = tab;
        this.isNivel2 = isNivel2;
        this.isPivote = isPivote;
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context=parent.getContext();
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.lisitem_nivel1, parent, false);
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
        private Button btnVerMarcas;
        private TextView txTituloCat;


        public ItemsViewHolder(View itemView) {
            super(itemView);
            tvTitulo = (TextView) itemView.findViewById(R.id.txCategoria);
            tvFlota = (TextView) itemView.findViewById(R.id.txFlota);
            tvActual =(TextView)itemView.findViewById(R.id.tvActualR);
            tvParcial =(TextView)itemView.findViewById(R.id.tvParcialR);
            tvMensualTxt =(TextView)itemView.findViewById(R.id.tvMarzo);
            tvMensual =(TextView)itemView.findViewById(R.id.tvMarzoR);
            tvAnualTxt =(TextView)itemView.findViewById(R.id.tvAnual);
            tvAnual =(TextView)itemView.findViewById(R.id.tvAnualR);
            btnVerMarcas = (Button)itemView.findViewById(R.id.btnVerMarcas);
            txTituloCat= (TextView)itemView.findViewById(R.id.txTituloCat);
            //instancias
        }
        public void  bindTitular(final Disponibilidad item){
            //set info (nombre,detalle y porcentaje)
            //setear bg tvsegun porcentaje
            tvTitulo.setText(item.getCATEGORIA_TXT());
            tvFlota.setText("( "+item.getFLOTA_TXT()+" )");
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

            if(isNivel2){
                btnVerMarcas.setVisibility(View.INVISIBLE);
                if(tipo.equals("TC")&&tab.equals("SERVICIOS")){
                    btnVerMarcas.setText("VER MARCAS");
                    pivote1="SERV";
                    pivote2="MARCA";
                    txTituloCat.setText("Marca: ");
                }else{
                    if(tipo.equals("SR")&&tab.equals("SERVICIOS")){
                        btnVerMarcas.setText("VER TIPOS");
                        pivote1="SERV";
                        pivote2="SUBTIPO";
                        txTituloCat.setText("Tipo: ");
                    }else{
                        if (tipo.equals("TC")&&tab.equals("MARCAS")){
                            btnVerMarcas.setText("VER SERVICIOS");
                            pivote1="MARCA";
                            pivote2="SERV";
                            txTituloCat.setText("Servicio: ");
                        }else{
                            if(tipo.equals("SR")&&tab.equals("SUB TIPOS")){
                                btnVerMarcas.setText("VER SERVICIOS");
                                pivote1="SUBTIPO";
                                pivote2="SERV";
                                txTituloCat.setText("Servicio: ");
                            }
                        }
                    }
                }

            }else{
                if(isPivote){
                    btnVerMarcas.setVisibility(View.INVISIBLE);
                }
                if(tipo.equals("TC")&&tab.equals("SERVICIOS")){
                    btnVerMarcas.setText("VER MARCAS");
                    pivote1="SERV";
                    pivote2="MARCA";
                    txTituloCat.setText("Servicio: ");
                }else{
                    if(tipo.equals("SR")&&tab.equals("SERVICIOS")){
                        btnVerMarcas.setText("VER TIPOS");
                        pivote1="SERV";
                        pivote2="SUBTIPO";
                        txTituloCat.setText("Servicio: ");
                    }else{
                        if (tipo.equals("TC")&&tab.equals("MARCAS")){
                            btnVerMarcas.setText("VER SERVICIOS");
                            pivote1="MARCA";
                            pivote2="SERV";
                            txTituloCat.setText("Marca: ");
                        }else{
                            if(tipo.equals("SR")&&tab.equals("SUB TIPOS")){
                                btnVerMarcas.setText("VER SERVICIOS");
                                pivote1="SUBTIPO";
                                pivote2="SERV";
                                txTituloCat.setText("Tipo: ");
                            }
                        }
                    }
                }
            }
            Log.d("DEBUG$",""+tipo+" "+tab);

            btnVerMarcas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String valorPivote1 = item.getCOD_CATEGORIA_N1();
                    isNivel2=true;
                    yourIntrface.onItemSelected(tipo,pivote1,pivote2,valorPivote1,item);
                }
            });
        }
    }
    public void setOnItemSelected(HelperInterface yourIntrface)
    {
        this.yourIntrface = yourIntrface;
    }
}
