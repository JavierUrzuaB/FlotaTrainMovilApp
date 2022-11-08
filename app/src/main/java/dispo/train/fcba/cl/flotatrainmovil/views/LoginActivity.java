package dispo.train.fcba.cl.flotatrainmovil.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import dispo.train.fcba.cl.flotatrainmovil.R;
import dispo.train.fcba.cl.flotatrainmovil.helpers.loginAPI;
import dispo.train.fcba.cl.flotatrainmovil.helpers.loginNotifAPI;
import dispo.train.fcba.cl.flotatrainmovil.models.Login;
import dispo.train.fcba.cl.flotatrainmovil.models.LoginNotif;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText mUsuario;
    private EditText mContraseña;
    private Button mLogear;
    private View mProgressDialog;
    private View mLoginFormView;

    private SharedPreferences prefs ;

    private Call<List<Login>> callListLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefs=getSharedPreferences("FcabPreferencias", Context.MODE_PRIVATE);
        boolean isLog =prefs.getBoolean("logueado",false);
        if(isLog){
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
        mUsuario = (EditText) findViewById(R.id.usuario);
        mContraseña = (EditText) findViewById(R.id.contraseña);
        mLogear = (Button) findViewById(R.id.logear);
        mProgressDialog = findViewById(R.id.barraProgreso);
        mLoginFormView = findViewById(R.id.login_form);

        mLogear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View focusView = null;
                boolean cancel = false;
                String sUsuario = mUsuario.getText().toString();
                String sContraseña = mContraseña.getText().toString();

                // Validación de user y pass
                if (TextUtils.isEmpty(sContraseña)){
                    mContraseña.setError(getString(R.string.error_field_required));
                    focusView = mContraseña;
                    cancel = true;
                }
                if (TextUtils.isEmpty(sUsuario)) {
                    mUsuario.setError(getString(R.string.error_field_required));
                    focusView = mUsuario;
                    cancel = true;
                }
                if (cancel) {
                    // There was an error; don't attempt login and focus the first
                    // form field with an error.
                    focusView.requestFocus();
                } else {
                    // Show a progress spinner, and kick off a background task to
                    // perform the user login attempt.
                    callListLogin(sUsuario,sContraseña);
                }
            }
        });
    }
    private void callListLogin(final String mEmail, String mPassword) {
        //final ProgressDialog mProgressDialog

        showProgress(true);
        // VALIDR
        callListLogin = loginAPI.get().getRetrofitService("capacita", "capacita").getLogin(mEmail,mPassword);
        //TREN++;
        //AQUI EL callListTrenInfo NO es null
        callListLogin.enqueue(new Callback<List<Login>>() {

            @Override
            public void onResponse(Call<List<Login>> call, Response<List<Login>> response) {
                Login login = (response.body().get(0));
                if(true){//login.getEstado().equals("OK")){
                    showProgress(false);
                    String tokenNotif =  FirebaseInstanceId.getInstance().getToken();
                    Log.d("LOGIN",tokenNotif);
                    callListLoginNotif(mEmail, tokenNotif, "TRAIN", "ALL", "TRAIN");

                    //Valido si tiene permisos para desmarcar
                    SharedPreferences.Editor editor = prefs.edit();
                    // VALIDAREMOS True cuando el usuario se halla Logueado exitpsamente
                    editor.putBoolean("logueado", true);
                    editor.putString("usuario",mEmail);
                    editor.putString("tokenNotif",tokenNotif);
                    editor.commit();// Commit the edits!

                    //PASAR POR PARAMETRO BUNDLE;
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    showProgress(false);
                    Toast.makeText(LoginActivity.this,"Usario no encontrado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Login>> call, Throwable t) {
                showProgress(false);
                Log.d("LOGIN", "NO ENTRE AL ON RESPONSE DEL LOGIN "+ t);
                Toast.makeText(LoginActivity.this,"Falla en el sistema", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void callListLoginNotif( String usuario, String mobile_key, String categoria, String sub_categoria, String app) {
        //final ProgressDialog mProgressDialog

        showProgress(true);
        // VALIDR
        Call<LoginNotif> callListLoginNotif = loginNotifAPI.get().getRetrofitService("capacita", "capacita").loginNotif(usuario,mobile_key,categoria,sub_categoria,app);
        //TREN++;
        //AQUI EL callListTrenInfo NO es null
        callListLoginNotif.enqueue(new Callback<LoginNotif>() {

            @Override
            public void onResponse(Call<LoginNotif> call, Response<LoginNotif> response) {
                showProgress(false);
                LoginNotif loginNotif = response.body();
                String estado = loginNotif.getESTADO();
                switch (estado){
                    case "0":
                        Log.d("LOGIN","NOTIF ACTIVA");//Notif Activa
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("notifActiva", true);
                        editor.commit();// Commit the edits!
                        break;
                    case "1":
                        //Error
                        break;
                    case "2":
                        //Cambio
                        Log.d("LOGIN","NOTIF Cambio");
                        SharedPreferences.Editor editor2 = prefs.edit();
                        editor2.putBoolean("notifActiva", false);
                        editor2.commit();// Commit the edits!
                        break;
                    case "3":
                        //Notif Inactiva
                        Log.d("LOGIN","NOTIF INACTIVA");
                        SharedPreferences.Editor editor3 = prefs.edit();
                        editor3.putBoolean("notifActiva", false);
                        editor3.commit();// Commit the edits!
                        break;
                }

            }

            @Override
            public void onFailure(Call<LoginNotif> call, Throwable t) {
                showProgress(false);
                Log.d("LOGIN", "NO ENTRE AL ON RESPONSE DEL LOGIN NOTIF"+ t);
                Toast.makeText(LoginActivity.this,"Falla en el sistema", Toast.LENGTH_LONG).show();

            }
        });
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressDialog.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressDialog.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressDialog.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressDialog.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
