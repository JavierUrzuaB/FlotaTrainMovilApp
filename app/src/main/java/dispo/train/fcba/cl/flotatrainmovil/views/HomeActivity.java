package dispo.train.fcba.cl.flotatrainmovil.views;

import android.app.Dialog;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import dispo.train.fcba.cl.flotatrainmovil.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.d("IDS","-> "+ FirebaseInstanceId.getInstance().getToken());
    }

    public void trenesEnViaje(View view){
        Intent intent = new Intent();
        intent.putExtra("tipo","TC");
        intent.setClass(HomeActivity.this,FlotaActivity.class);
        startActivity(intent);
    }
    public void trenesEnViajeSM(View view){
        Intent intent = new Intent();
        intent.putExtra("tipo","SR");
        intent.setClass(HomeActivity.this,FlotaActivity.class);
        startActivity(intent);
    }

    public void onDispoTC(View view){
        Intent intent = new Intent();
        intent.putExtra("tipo","TC");
        intent.setClass(HomeActivity.this,DispoActivity.class);
        startActivity(intent);
       // Toast.makeText(this, "En construcción", Toast.LENGTH_SHORT).show();
    }
    public void onDispoSR(View view){
        Intent intent = new Intent();
        intent.putExtra("tipo","SR");
        intent.setClass(HomeActivity.this,DispoActivity.class);
        startActivity(intent);
    }
    public void onSegRetorque(View view){
        Intent intent = new Intent();
        intent.putExtra("tipo","RT");
        intent.setClass(HomeActivity.this,RetorqueActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //noinspection SimplifiableIfStatement
        if (item.getTitle().equals("Cerrar Sesión")) {
            AlertDialog alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogCustom).create();
            // Setting Dialog Title
            alertDialog.setTitle(" ATENCIÓN :");
            // Setting Dialog Message
            alertDialog.setMessage(" ¿ Está seguro que desea cerrar su sesión ? ");
            // Setting Icon to Dialog
            // alertDialog.setIcon();
            // Setting OK Button
            alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences prefs = getSharedPreferences("FcabPreferencias", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean("logueado", false);
                    editor.commit();// Commit the edits!
                    Intent intent = new Intent();
                    intent.setClass(HomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
            // Setting No Button
            alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alertDialog.show();
        }
        if (item.getTitle().equals("Ajustes")) {
            Intent intent = new Intent();
            intent.setClass(HomeActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
