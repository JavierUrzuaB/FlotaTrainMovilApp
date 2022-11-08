package dispo.train.fcba.cl.flotatrainmovil.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.adapters.DispoAdaptador;
import dispo.train.fcba.cl.flotatrainmovil.adapters.DispoNivel1Adaptador;
import dispo.train.fcba.cl.flotatrainmovil.adapters.DispoNivelesAdaptador;
import dispo.train.fcba.cl.flotatrainmovil.helpers.dispoAPI;
import dispo.train.fcba.cl.flotatrainmovil.interfaces.HelperInterface;
import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by capacita on 21-05-2017.
 */

public class TabFragmentSer extends Fragment implements HelperInterface {
    private List<Disponibilidad> datos;
    private List<Disponibilidad> datosPivot= new ArrayList<>();
    //
    private RecyclerView viewPivot;
    private Button btnCancelar;
    private RecyclerView rvDispoPivot;
    private String tipo;
    private String tab;
    private String fecha;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_fragment_ser, container, false);
        btnCancelar = (Button) view.findViewById(R.id.btnCancelar);
        viewPivot= (RecyclerView) view.findViewById(R.id.viewPivot);
        rvDispoPivot= (RecyclerView)view.findViewById(R.id.rvDispoPivot);
         fecha= getArguments().getString("fecha");
         tipo = getArguments().getString("tipo");
        if(tipo.equals("TC")){
           tab="SERVICIOS";
        }else{
            tab="SERVICIOS";
        }
        callListDispoNivel1(fecha,tipo,"SERV");
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCancelar.setVisibility(View.GONE);
                viewPivot.setVisibility(View.GONE);
                callListDispoNivel1(fecha,tipo,"SERV");
            }
        });
        return view;

    }

    private void callListDispoNivel1(String fecha, String tipo, String pivote1){
        Call<List<Disponibilidad>> callListDispoFlota = dispoAPI.get().getRetrofitService("capacita","capacita").getLisDispoNivel1(fecha,tipo,pivote1);
        callListDispoFlota.enqueue(new Callback<List<Disponibilidad>>() {
            @Override
            public void onResponse(Call<List<Disponibilidad>> call, Response<List<Disponibilidad>> response) {
                datos=response.body();
                setUpRecyclerView(false);
            }

            @Override
            public void onFailure(Call<List<Disponibilidad>> call, Throwable t) {
                Log.d("ERROR","CANT: "+t.getMessage());
            }
        });
    }

    private void setUpRecyclerView(boolean isNivel2){
        rvDispoPivot.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDispoPivot.setHasFixedSize(true);
        //Adaptador aqui
        DispoNivel1Adaptador adaptador = new DispoNivel1Adaptador(datos,tipo,tab,isNivel2,false);
        adaptador.setOnItemSelected(this);
        rvDispoPivot.setAdapter(adaptador);
        //configuracion rv
        rvDispoPivot.setLayoutManager( new GridLayoutManager(getActivity(),1));
        rvDispoPivot.addItemDecoration( new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        rvDispoPivot.setItemAnimator(new DefaultItemAnimator());
    }

    private void setUpRecyclerViewBus(){

        viewPivot.setLayoutManager(new LinearLayoutManager(getActivity()));
        viewPivot.setHasFixedSize(true);
        //Adaptador aqui
        Toast.makeText(getActivity(),datosPivot.get(0).getCATEGORIA_TXT(),Toast.LENGTH_SHORT).show();
        DispoNivel1Adaptador adaptador = new DispoNivel1Adaptador(datosPivot,tipo,tab,false,true);
        viewPivot.setAdapter(adaptador);
        //configuracion rv
        viewPivot.setLayoutManager( new GridLayoutManager(getActivity(),1));
        viewPivot.addItemDecoration( new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        viewPivot.setItemAnimator(new DefaultItemAnimator());

    }
    private void callListDispoNivel2(String fecha, String tipo, String pivote1,String pivote2, String valor_pivote1){
        Call<List<Disponibilidad>> callListDispoFlota = dispoAPI.get().getRetrofitService("capacita","capacita").getLisDispoNivel2(fecha,tipo,pivote1,pivote2,valor_pivote1);
        callListDispoFlota.enqueue(new Callback<List<Disponibilidad>>() {
            @Override
            public void onResponse(Call<List<Disponibilidad>> call, Response<List<Disponibilidad>> response) {
                datos=response.body();
                setUpRecyclerView(true);
            }

            @Override
            public void onFailure(Call<List<Disponibilidad>> call, Throwable t) {
                Log.d("ERROR","CANT: "+t.getMessage());
            }
        });
    }

    @Override
    public void onItemSelected(String tipo,String pivote1,String pivote2,String valorPivote1,Disponibilidad curDispo) {
        viewPivot.setVisibility(View.VISIBLE);
        btnCancelar.setVisibility(View.VISIBLE);
        if (datosPivot.size()==0){
            datosPivot.add(curDispo);
        }else{
            datosPivot.set(0,curDispo);
        }
        setUpRecyclerViewBus();
        callListDispoNivel2(fecha,tipo,pivote1,pivote2,valorPivote1);
        //Toast.makeText(getContext(),"MIRAR:"+tipo+" "+pivote1+" "+pivote2+" "+valorPivote1,Toast.LENGTH_SHORT).show();
    }
}
