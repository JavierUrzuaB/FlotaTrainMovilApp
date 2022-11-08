package dispo.train.fcba.cl.flotatrainmovil.views;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaGenAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.marcasAPI;
import dispo.train.fcba.cl.flotatrainmovil.models.Helper;
import dispo.train.fcba.cl.flotatrainmovil.models.LugarReparacion;
import dispo.train.fcba.cl.flotatrainmovil.models.Marca;
import dispo.train.fcba.cl.flotatrainmovil.models.Tipo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarcaActivity extends AppCompatActivity {

    private TextView _tvPatente;
    private TextView _tvAnio;
    private TextView _tvModelo;
    private TextView _tvInterno;
    private TextView _tvUsuarioMarca;
    private TextView _tvUltMante;
    private TextView _tvAltMante;
    private EditText _etOBS;
    private EditText _etFechaMarca;
    private EditText _etHoraMarca;
    private Spinner _sLugarReparac;
    private Spinner _sTipo;
    private String[] datosCodLugar;
    private String[] datosCodTipo;
    private SharedPreferences prefs;
    private String idVehiculo;
    private Calendar myCalendar;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        prefs=getSharedPreferences("FcabPreferencias", Context.MODE_PRIVATE);
        idVehiculo =  getIntent().getExtras().getString("idVehiculo");
        tipo = getIntent().getExtras().getString("tipo");
        if(tipo.equals("TC")){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Marcar Tractocamiones");
        }else{
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Marcar Semirremolques");
        }

        _tvPatente= (TextView)findViewById(R.id.tvPatente);
        _tvAnio= (TextView)findViewById(R.id.tvAnio);
        _tvModelo= (TextView)findViewById(R.id.tvModelo);
        _tvInterno= (TextView)findViewById(R.id.tvInterno);
        _tvUsuarioMarca= (TextView)findViewById(R.id.tvUsuarioMarca);
        _tvUltMante= (TextView)findViewById(R.id.tvUltMante);
        _tvAltMante= (TextView)findViewById(R.id.tvAltMante);
        _etFechaMarca=(EditText)findViewById(R.id.etFechaMarca);
        _etHoraMarca=(EditText)findViewById(R.id.etHoraMarca);
        _etOBS=(EditText)findViewById(R.id.etOBS);
        _sLugarReparac = (Spinner) findViewById(R.id.sLugarReparac);
        _sTipo = (Spinner) findViewById(R.id.sTipo);

        callListTipo();
        callListLugarRepac();
        callListHora();
        callListMarcas(idVehiculo);
        //Add back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        _etFechaMarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(MarcaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        _etHoraMarca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MarcaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        _etHoraMarca.setText((String.format("%02d:%02d", selectedHour, selectedMinute)));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione hora");
                mTimePicker.show();

            }
        });
    }
    private void updateLabel(){

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        _etFechaMarca.setText(sdf.format(myCalendar.getTime()));
    }

    public void onMarcar(View view){
        //String idVehiculo,String fecha,String hora,String obs, String usuario
        String fecha= _etFechaMarca.getText().toString();
        String hora=_etHoraMarca.getText().toString();
        String obs=_etOBS.getText().toString();
        String usuario = prefs.getString("usuario","");
        String codLugarRep = datosCodLugar[_sLugarReparac.getSelectedItemPosition()];
        String codTipo= _sTipo.getSelectedItem().toString();
        Log.d("Log=",codTipo);
        callMarcar(idVehiculo,fecha,hora,obs,usuario,codLugarRep,codTipo);
    }

    private void callListHora(){
        Call<List<Helper>> callListFlotaTracto = flotaGenAPI.get().getRetrofitService("capacita","capacita").getHoraServidor();
        callListFlotaTracto.enqueue(new Callback<List<Helper>>() {
            @Override
            public void onResponse(Call<List<Helper>> call, Response<List<Helper>> response) {
                if(response.isSuccess()){
                    Helper marca = response.body().get(0);
                    _etFechaMarca.setText(marca.getFECHA());
                    _etHoraMarca.setText(marca.getHORA());
                    Log.d("DEBUGIN","fecha:"+marca.getFECHA()+" Hora:"+marca.getHORA());
                }
            }

            @Override
            public void onFailure(Call<List<Helper>> call, Throwable t) {
                Toast.makeText(MarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void callListLugarRepac(){
        Call<List<LugarReparacion>> callListLugarReparacion = marcasAPI.get().getRetrofitService("capacita","capacita").getLugaresReparacion();
        callListLugarReparacion.enqueue(new Callback<List<LugarReparacion>>() {
            @Override
            public void onResponse(Call<List<LugarReparacion>> call, Response<List<LugarReparacion>> response) {
                if(response.isSuccess()){
                    int tamañoRecord = response.body().size();
                    final String[] datos = new String[tamañoRecord];
                    datosCodLugar = new String[tamañoRecord];
                    //Poblar Spinner
                    for(int i =0; i< tamañoRecord; i++ ){
                        LugarReparacion item = response.body().get(i);
                        String descripcionLugar = item.getDESC_LUGAR();
                        String codigoLugar = item.getCOD_LUGAR();
                        datos[i] = descripcionLugar;
                        datosCodLugar[i] = codigoLugar;
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(MarcaActivity.this, android.R.layout.simple_spinner_item, datos);
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    _sLugarReparac.setAdapter(adaptador);
                }
            }

            @Override
            public void onFailure(Call<List<LugarReparacion>> call, Throwable t) {
                Toast.makeText(MarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void callListTipo(){
        Call<List<Tipo>> callListTipo = marcasAPI.get().getRetrofitService("capacita","capacita").getTiposReparacion();
        callListTipo.enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if(response.isSuccess()){
                    int tamañoRecord = response.body().size();
                    datosCodTipo = new String[tamañoRecord];
                    //Poblar Spinner
                    for(int i =0; i< tamañoRecord; i++ ){
                        Tipo item = response.body().get(i);
                        String codigoLugar = item.getTIPO_REP();
                        datosCodTipo[i] = codigoLugar;
                    }
                    ArrayAdapter<String> adaptador = new ArrayAdapter<>(MarcaActivity.this, android.R.layout.simple_spinner_item, datosCodTipo);
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    _sTipo.setAdapter(adaptador);
                }
            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {
                Toast.makeText(MarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void callListMarcas(String idVehiculo){
        Call<List<Marca>> callListFlotaTracto = marcasAPI.get().getRetrofitService("capacita","capacita").getInfoParaMarca(idVehiculo);
        callListFlotaTracto.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                Marca marca = response.body().get(0);
                _tvPatente.setText(marca.getPATENTE());
                _tvAnio.setText(marca.getANHO());
                _tvModelo.setText(marca.getMODELO());
                _tvInterno.setText(marca.getPK_ID_VEHICULO());
                _tvUsuarioMarca.setText(prefs.getString("usuario","").toUpperCase());
                _tvUltMante.setText(marca.getULTIMO_EVENTO_MARCA());
                _tvAltMante.setText(marca.getULTIMO_EVENTO_DESMARCA());

            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Toast.makeText(MarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void callMarcar(String idVehiculo,String fecha,String hora,String obs, String usuario, String codLugarRep, String tipoRep){
        final ProgressDialog progressDoalog = new ProgressDialog(MarcaActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Procesando....");
        progressDoalog.setTitle("Estamos marcando");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Call<Helper> callListFlotaTracto = marcasAPI.get().getRetrofitService("capacita","capacita").setMarca(idVehiculo,fecha,hora,obs,usuario,codLugarRep,tipoRep);
        callListFlotaTracto.enqueue(new Callback<Helper>() {
            @Override
            public void onResponse(Call<Helper> call, Response<Helper> response) {
                progressDoalog.dismiss();
                Helper helper = response.body();
                    if(helper.getESTADO().equals("0")){
                        Toast.makeText(MarcaActivity.this,"Marca realizada exitosamente",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MarcaActivity.this,FlotaActivity.class);
                        intent.putExtra("tipo",tipo);
                        startActivity(intent);
                        finish();
                    }else{
                        String msj= helper.getMENSAJE();
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MarcaActivity.this);
                        // set title
                        alertDialogBuilder.setTitle("Error al Marcar");
                        // set dialog message
                        alertDialogBuilder
                                .setMessage(msj)
                                .setCancelable(false)
                                .setPositiveButton("Entendido",new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,int id) {
                                        dialog.cancel();
                                    }
                                });

                        // create alert dialog
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        // show it
                        alertDialog.show();
                    }
                }
            @Override
            public void onFailure(Call<Helper> call, Throwable t) {
                progressDoalog.dismiss();
                Toast.makeText(MarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_backArrow = 16908332;
        if(item.getItemId()== id_backArrow){
            Intent intent = new Intent(MarcaActivity.this,FlotaActivity.class);
            intent.putExtra("tipo",tipo);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Do something
        Intent intent = new Intent(MarcaActivity.this,FlotaActivity.class);
        intent.putExtra("tipo",tipo);
        startActivity(intent);
        finish();
    }
}
