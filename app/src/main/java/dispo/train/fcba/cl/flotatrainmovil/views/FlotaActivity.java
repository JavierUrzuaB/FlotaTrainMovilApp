
package dispo.train.fcba.cl.flotatrainmovil.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.adapters.lvFlotaAdapter;
import dispo.train.fcba.cl.flotatrainmovil.helpers.flotaAPI;
import dispo.train.fcba.cl.flotatrainmovil.models.Flota;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlotaActivity extends AppCompatActivity {

    private List<Flota> listaFlota;
    private ListView lvFlota;
    private TextView tvCantMan;
    private TextView tvCantDispo;
    private lvFlotaAdapter lvFlotaAdapter;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flota);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Bundle extras = getIntent().getExtras();
        tipo= extras.getString("tipo");
        if(tipo.equals("TC")){
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Flota Tractocamiones");
        }else{
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Flota Semirremolques");
        }
        //Add back arrow
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvCantMan = (TextView) findViewById(R.id.tvCantMan);
        tvCantDispo = (TextView) findViewById(R.id.tvCantDispo);
        lvFlota = (ListView) findViewById(R.id.lvFlotaTracto);
        lvFlota.setAdapter(null);
        View header = getLayoutInflater().inflate(R.layout.listitem_cuatro_head,null);
        ((TextView) header.findViewById(R.id.head1)).setText("ID");
        ((TextView) header.findViewById(R.id.head2)).setText("PATENTE");
        ((TextView) header.findViewById(R.id.head3)).setText("L.REP.");
        ((TextView) header.findViewById(R.id.head4)).setText("DURACIÓN");
        lvFlota.addHeaderView(header,null,false);
        lvFlotaAdapter = new lvFlotaAdapter(this,new ArrayList<Flota>());
        lvFlota.setAdapter(lvFlotaAdapter);
        callListNumFlota(tipo);

        callListFlotaTracto(tipo,"");
    }

    @Override
    protected void onStart() {
        lvFlota.setAdapter(lvFlotaAdapter);
        lvFlotaAdapter.notifyDataSetChanged();
        super.onStart();
    }

    public void onManten(View view){
        callListFlotaTracto(tipo,"M");
    }

    public void onDisp(View view){
        callListFlotaTracto(tipo,"D");
    }

    private void callListNumFlota(String tipo){
        Call<Flota> callListFlotaTracto = flotaAPI.get().getRetrofitService("capacita","capacita").getCantMarcas(tipo);
        callListFlotaTracto.enqueue(new Callback<Flota>() {
            @Override
            public void onResponse(Call<Flota> call, Response<Flota> response) {
                tvCantMan.setText(response.body().getCANT_MANTE());
                tvCantDispo.setText(response.body().getCANT_DISPO());
            }

            @Override
            public void onFailure(Call<Flota> call, Throwable t) {
                Toast.makeText(FlotaActivity.this,"",Toast.LENGTH_SHORT).show();
                Log.d("ERROR","CANT: "+t.getMessage());
            }
        });
    }

    private void callListFlotaTracto(final String tipo, String marca){
        Call<List<Flota>> callListFlotaTracto = flotaAPI.get().getRetrofitService("capacita","capacita").lisFlota(tipo,marca);
        callListFlotaTracto.enqueue(new Callback<List<Flota>>() {
            @Override
            public void onResponse(Call<List<Flota>> call, Response<List<Flota>> response) {
                Log.d("ESTADO","ITEM: "+response.body().get(0).getPK_ID_VEHICULO());

                listaFlota = response.body();
                lvFlotaAdapter.setListFlota(listaFlota);
                lvFlota.setAdapter(lvFlotaAdapter);
                if(listaFlota.get(0).getPK_ID_VEHICULO().isEmpty()){
                    lvFlota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });
                }else{
                    lvFlota.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Flota itemClick= (Flota)parent.getAdapter().getItem(position);
                            //VALIDAR ITEMCLICk
                            if(itemClick.getESTADO().equals("DISPO")){
                                //ir a marcar
                                Intent intent = new Intent(FlotaActivity.this, MarcaActivity.class);
                                intent.putExtra("tipo",tipo);
                                intent.putExtra("idVehiculo", itemClick.getPK_ID_VEHICULO());
                                startActivity(intent);
                                finish();
                            }else{
                                //ir a desmarcar
                                Intent intent = new Intent(FlotaActivity.this, DesmarcaActivity.class);
                                intent.putExtra("tipo",tipo);
                                intent.putExtra("idVehiculo", itemClick.getPK_ID_VEHICULO());
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<List<Flota>> call, Throwable t) {
                Log.d("ESTADO","ERROR: "+t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id_backArrow = 16908332;
        if(item.getItemId()== id_backArrow){
            finish();
            return true;
        }
        if(item.getTitle().equals("Refresacar")){
            callListFlotaTracto(tipo,"");
        }
        return super.onOptionsItemSelected(item);
    }
}
