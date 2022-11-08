package dispo.train.fcba.cl.flotatrainmovil.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dispo.train.fcba.cl.flotatrainmovil.R;

public class RetorqueActivity extends AppCompatActivity {
    private ArrayList<Array> listaEquipos;
    private Array equipoRetorque;
    private ListView lvRetorques;
    private TextView tvCantEquipos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retorque);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("SEGUIMIENTO RETORQUES");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }
    public void onDetalle(View view){
        Intent intent = new Intent(RetorqueActivity.this, DetalleRetorqueActivity.class);
        startActivity(intent);
    }
}
