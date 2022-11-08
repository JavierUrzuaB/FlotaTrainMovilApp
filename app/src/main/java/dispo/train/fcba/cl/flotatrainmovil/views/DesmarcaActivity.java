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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaGenAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.marcasAPI;
import dispo.train.fcba.cl.flotatrainmovil.models.Helper;
import dispo.train.fcba.cl.flotatrainmovil.models.Marca;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DesmarcaActivity extends AppCompatActivity {
    private TextView _tvPatente;
    private TextView _tvAnio;
    private TextView _tvModelo;
    private TextView _tvInterno;
    private TextView _tvFechaMarca;
    private TextView _tvHoraMarca;
    private TextView _tvUsuarioMarca;
    private TextView _tvOBSD;
    private TextView _tvUsuarioDesmarca;
    private EditText _etOBSD;
    private EditText _etFechaDesmarca;
    private EditText _etHoraDesmarca;
    private SharedPreferences prefs;
    private String idVehiculo;
    private Calendar myCalendar;
    private String tipo;
    private List<Helper> listHoraServidor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desmarca);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        prefs=getSharedPreferences("FcabPreferencias", Context.MODE_PRIVATE);
        idVehiculo =  getIntent().getExtras().getString("idVehiculo");
        tipo = getIntent().getExtras().getString("tipo");
        if(tipo.equals("TC")){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Desmarcar Tractocamiones");
        }else{
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Desmarcar Semirremolques");
        }
        //Add back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //Layout
        _tvPatente= (TextView)findViewById(R.id.tvPatenteD);
        _tvAnio= (TextView)findViewById(R.id.tvAnioD);
        _tvModelo= (TextView)findViewById(R.id.tvModeloD);
        _tvInterno= (TextView)findViewById(R.id.tvInternoD);
        _tvFechaMarca= (TextView)findViewById(R.id.tvFechaMarcaD);
        _tvHoraMarca= (TextView)findViewById(R.id.tvHoraMarcaD);
        _tvUsuarioMarca= (TextView)findViewById(R.id.tvUsuarioMarcaD);
        _tvOBSD= (TextView)findViewById(R.id.tvOBSD);
        _tvUsuarioDesmarca=(TextView)findViewById(R.id.tvUsuarioDesmarcaD);
        _etFechaDesmarca=(EditText)findViewById(R.id.etFechaDesmarca);
        _etHoraDesmarca=(EditText)findViewById(R.id.etHoraDesmarca);
        _etOBSD=(EditText)findViewById(R.id.etOBSD);
        callListHora();
        callListDesmarcas(idVehiculo);
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

        _etFechaDesmarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(DesmarcaActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        _etHoraDesmarca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(DesmarcaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        _etHoraDesmarca.setText((String.format("%02d:%02d", selectedHour, selectedMinute)));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Seleccione hora");
                mTimePicker.show();

            }
        });

        CheckBox cambioNeumaticosChkbx = ( CheckBox ) findViewById( R.id.checkBoxCambioNeumaticos );
        cambioNeumaticosChkbx.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    findViewById( R.id.layoutCambioNeumaticos ).setVisibility(View.VISIBLE);
                }else {
                    findViewById( R.id.layoutCambioNeumaticos ).setVisibility(View.GONE);
                }

            }
        });
    }

    private void updateLabel(){

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        _etFechaDesmarca.setText(sdf.format(myCalendar.getTime()));
    }

    public void onDesmarcar(View view){
        String fecha= _etFechaDesmarca.getText().toString();
        String hora=_etHoraDesmarca.getText().toString();
        String obs=_etOBSD.getText().toString();
        String usuario = prefs.getString("usuario","");
        onDesmarcar(idVehiculo,fecha,hora,obs,usuario);


    }

    private void callListHora(){
        Call<List<Helper>> callListFlotaTracto = flotaGenAPI.get().getRetrofitService("capacita","capacita").getHoraServidor();
        callListFlotaTracto.enqueue(new Callback<List<Helper>>() {
            @Override
            public void onResponse(Call<List<Helper>> call, Response<List<Helper>> response) {
                if(response.isSuccess()){
                    Helper marca = response.body().get(0);
                    _etFechaDesmarca.setText(marca.getFECHA());
                    _etHoraDesmarca.setText(marca.getHORA());
                    Log.d("DEBUGIN","fecha:"+marca.getFECHA()+" Hora:"+marca.getHORA());
                }
            }

            @Override
            public void onFailure(Call<List<Helper>> call, Throwable t) {
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void callListDesmarcas(String idVehiculo){
        Call<List<Marca>> callListFlotaTracto = marcasAPI.get().getRetrofitService("capacita","capacita").getInfoParaDesmarca(idVehiculo);
        callListFlotaTracto.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                Marca marca = response.body().get(0);
                _tvPatente.setText(marca.getPATENTE());
                _tvAnio.setText(marca.getANHO());
                _tvModelo.setText(marca.getMODELO());
                _tvInterno.setText(marca.getPK_ID_VEHICULO());
                String fechahora=marca.getFECHA_EVENTO();
                String [] spliter = fechahora.split("\\s+");
                String fecha=spliter[0];
                String hora=spliter[1];
                 _tvFechaMarca.setText(fecha);
                 _tvHoraMarca.setText(hora);
                 _tvUsuarioMarca.setText(marca.getUSUARIO());
                 _tvOBSD.setText(marca.getOBSERVACION());
                 _tvUsuarioDesmarca.setText(prefs.getString("usuario","").toUpperCase());
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Toast.makeText(DesmarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    private void onDesmarcar(String idVehiculo,String fecha,String hora,String obs, String usuario){
        final ProgressDialog progressDoalog = new ProgressDialog(DesmarcaActivity.this);
        progressDoalog.setMax(100);
        progressDoalog.setMessage("Procesando....");
        progressDoalog.setTitle("Estamos desmarcando");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.show();
        Call<Helper> callListFlotaTracto = marcasAPI.get().getRetrofitService("capacita","capacita").setDesmarca(idVehiculo,fecha,hora,obs,usuario);
        callListFlotaTracto.enqueue(new Callback<Helper>() {
            @Override
            public void onResponse(Call<Helper> call, Response<Helper> response) {
                progressDoalog.dismiss();
                Helper helper = response.body();
                if(helper.getESTADO().equals("0")){
                    String msj= helper.getMENSAJE();
                    Toast.makeText(DesmarcaActivity.this,"Desmarca realizada exitosamente",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DesmarcaActivity.this,FlotaActivity.class);
                    intent.putExtra("tipo",tipo);
                    startActivity(intent);
                    finish();
                }else{
                    String msj= helper.getMENSAJE();
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DesmarcaActivity.this);
                    // set title
                    alertDialogBuilder.setTitle("Error al desmarcar");
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
                Toast.makeText(DesmarcaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_backArrow = 16908332;
        if(item.getItemId()== id_backArrow){
            Intent intent = new Intent(DesmarcaActivity.this,FlotaActivity.class);
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
        Intent intent = new Intent(DesmarcaActivity.this,FlotaActivity.class);
        intent.putExtra("tipo",tipo);
        startActivity(intent);
        finish();
    }


}
