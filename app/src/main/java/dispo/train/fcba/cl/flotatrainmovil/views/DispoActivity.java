package dispo.train.fcba.cl.flotatrainmovil.views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.adapters.DispoAdaptador;
import dispo.train.fcba.cl.flotatrainmovil.helpers.dispoAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaGenAPI;
import dispo.train.fcba.cl.flotatrainmovil.models.Disponibilidad;
import dispo.train.fcba.cl.flotatrainmovil.models.Flota;
import dispo.train.fcba.cl.flotatrainmovil.models.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DispoActivity extends AppCompatActivity {
    private RecyclerView rvDispo;
    private List<Disponibilidad> datos;
    private EditText etFechaCategoria;
    private Calendar myCalendar;
    private String tipo;
    private String horaServer=" ";
    private List<Disponibilidad> datosBusqueda= new ArrayList<Disponibilidad>();
    private DispoAdaptador adaptador;
    private  DividerItemDecoration dividerItemDecoration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Intent
        Bundle extras = getIntent().getExtras();
        tipo= extras.getString("tipo");
        if(tipo.equals("TC")){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Dispo Tractocamiones");
        }else{
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Dispo Semirremolques");
        }
        //Add back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        rvDispo= (RecyclerView)findViewById(R.id.rvDispo);
        etFechaCategoria = (EditText)findViewById(R.id.etFechaCategoria);
        Button btnCategoria= (Button) findViewById(R.id.button);

        //calendar implementation
        myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        etFechaCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DispoActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DispoActivity.this, CategoriasActivity.class);
                intent.putExtra("tipo",tipo);
                intent.putExtra("fecha", etFechaCategoria.getText().toString());
                intent.putExtra("datos", (Serializable) datosBusqueda);
                startActivity(intent);
            }
        });
        callListHora();
    }

    public void onConsultar(View view){
        if(etFechaCategoria.getText().toString().matches("")){
            Toast.makeText(this, "Debe ingresar una fecha", Toast.LENGTH_SHORT).show();
        }else{
            if(adaptador.getItemCount()>0){
                rvDispo.removeItemDecoration(dividerItemDecoration);
            }
            callListDispoFlota(etFechaCategoria.getText().toString(),tipo);

        }
    }

    private void callListDispoFlota(String fecha, String tipo){
        Call<List<Disponibilidad>> callListDispoFlota = dispoAPI.get().getRetrofitService("capacita","capacita").getDisponibilidadDia(fecha,tipo);
        callListDispoFlota.enqueue(new Callback<List<Disponibilidad>>() {
            @Override
            public void onResponse(Call<List<Disponibilidad>> call, Response<List<Disponibilidad>> response) {
                datos=response.body();
                Disponibilidad dispo= new Disponibilidad("","","","","","","","","","","","");
                dispo.setFECHA(datos.get(0).getFECHA());
                dispo.setFLOTA_TXT(datos.get(0).getTEXTO());
                for(int i=0; i<datos.size() ;i++){
                    if(i==0){
                        dispo.setDISPO_ACTUAL(datos.get(i).getPORC_DISPO());
                        dispo.setDISPO_ACTUAL_COLOR(datos.get(i).getCOLOR());
                    }else{
                        if(i==1){
                            dispo.setDISPO_PARCIAL(datos.get(i).getPORC_DISPO());
                            dispo.setDISPO_PARCIAL_COLOR(datos.get(i).getCOLOR());
                        }else{
                            if(i==2){
                                dispo.setDISPO_MES(datos.get(i).getPORC_DISPO());
                                dispo.setDISPO_MES_TXT(datos.get(i).getPERIODO());
                                dispo.setDISPO_MES_COLOR(datos.get(i).getCOLOR());
                                Log.d("DEBUGIN2",""+dispo.getDISPO_MES_TXT());

                            }else{
                                if(i==3){
                                    dispo.setDISPO_ANHO(datos.get(i).getPORC_DISPO());
                                    dispo.setDISPO_ANHO_TXT(datos.get(i).getPERIODO());
                                    dispo.setDISPO_ANHO_COLOR(datos.get(i).getCOLOR());
                                    Log.d("DEBUGIN2",""+dispo.getDISPO_ANHO_TXT());
                                }

                            }
                        }
                    }
                }
                if (datosBusqueda.size()==0){
                    datosBusqueda.add(dispo);
                }else{
                    datosBusqueda.set(0,dispo);
                }
                setUpRecyclerView();
            }

            @Override
            public void onFailure(Call<List<Disponibilidad>> call, Throwable t) {
                Log.d("ERROR","CANT: "+t.getMessage());
            }
        });
    }

    private void callListHora(){
        Call<List<Helper>> callListFlotaTracto = flotaGenAPI.get().getRetrofitService("capacita","capacita").getHoraServidor();
        callListFlotaTracto.enqueue(new Callback<List<Helper>>() {
            @Override
            public void onResponse(Call<List<Helper>> call, Response<List<Helper>> response) {
                if(response.isSuccess()){
                    Helper marca = response.body().get(0);
                    horaServer= marca.getFECHA();
                    etFechaCategoria.setText(horaServer);
                    callListDispoFlota(horaServer,tipo);
                    Log.d("DEBUGIN","fecha:"+marca.getFECHA()+" Hora:"+marca.getHORA());
                }
            }

            @Override
            public void onFailure(Call<List<Helper>> call, Throwable t) {
                Toast.makeText(DispoActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void updateLabel(){
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        etFechaCategoria.setText(sdf.format(myCalendar.getTime()));
    }

    private void setUpRecyclerView(){

        rvDispo.setLayoutManager(new LinearLayoutManager(this));
        rvDispo.setHasFixedSize(true);
        //Adaptador aqui
        adaptador = new DispoAdaptador(datos);
        //adaptador.swap(datos);
        rvDispo.setAdapter(adaptador);
        //configuracion rv
        rvDispo.setLayoutManager( new GridLayoutManager(this,1));
        dividerItemDecoration =new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rvDispo.addItemDecoration(dividerItemDecoration);
        rvDispo.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_backArrow = 16908332;
        if(item.getItemId()== id_backArrow){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
