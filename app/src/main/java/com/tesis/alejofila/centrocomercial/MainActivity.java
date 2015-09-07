package com.tesis.alejofila.centrocomercial;

import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;
import com.tesis.alejofila.centrocomercial.auth.AccountAuthenticatorActivity;
import com.tesis.alejofila.centrocomercial.http.Constants;
public class MainActivity extends AccountAuthenticatorActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Button btnIniciar, btnGoToRegister,btnRecoverPassword;
    EditText edtEmail, edtPassword;


    public final static String ARG_ACCOUNT_TYPE = "ACCOUNT_TYPE";
    public static final String ARG_AUTH_TYPE = "AUTH_TYPE";
    public static final String ARG_IS_ADDING_NEW_ACCOUNT = "IS_ADDING_NEW_ACCOUNT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (parserVerifyWorkingSession()) {

        }
        else {

            setContentView(R.layout.activity_main);

            edtEmail = (EditText) findViewById(R.id.edtLogin);
            edtPassword = (EditText) findViewById(R.id.edtPassword);
            btnIniciar = (Button) findViewById(R.id.btnLogin);
            btnGoToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
            btnRecoverPassword =(Button) findViewById(R.id.btnRecoverPassword);
            btnRecoverPassword.setOnClickListener(this);
            btnGoToRegister.setOnClickListener(this);
            btnIniciar.setOnClickListener(this);

            // In case that the activity is instantiated by RegisterActivity
            if (getIntent() != null) {
                String email = getIntent().getStringExtra(Constants.EMAIL);
                String password = getIntent().getStringExtra(Constants.PASSWORD);
                edtEmail.setText(email);
                edtPassword.setText(password);
            }
        }


    }

    private boolean parserVerifyWorkingSession() {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            Intent i = new Intent(this, HomeActivity.class);
            i.putExtra(Constants.EMAIL, currentUser.getEmail());
            startActivity(i);
            finish();
            return true;
        } else
            return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            PreferenceManager.getDefaultSharedPreferences(this).edit().
                    putBoolean(Constants.IS_WORKING_SESSION, false).
                    commit();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                if (validateLoginForm()) {
                    String email = edtEmail.getText().toString();
                    String password = edtPassword.getText().toString();
                    parseLoginFunction(email, password);
                }
                //loginFunction();
                else
                    Toast.makeText(this, R.string.login_missing_values, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnLinkToRegisterScreen:
                goToRegisterScreen();
                break;
            case R.id.btnRecoverPassword:
                showRecoverPasswordDialog();
                break;
            default:
                break;

        }

    }

    private void showRecoverPasswordDialog() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Recupera tu contraseña");
        alert.setMessage("Ingresa tu correo electronico");


        final EditText input = new EditText(this);
        alert.setView(input);

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"Button pressed: "+which);
                Toast.makeText(MainActivity.this,"Se esta procesando tu peticion",Toast.LENGTH_SHORT).show();
                ParseUser.requestPasswordResetInBackground(input.getText().toString(), new RequestPasswordResetCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e != null)
                            Toast.makeText(MainActivity.this
                                    ,e.getMessage(),Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this
                                    ,"Fueron enviadas instrucciones a tu correo",Toast.LENGTH_SHORT).show();

                    }
                });
            }
        };
        alert.setPositiveButton("Enviar",listener);
        alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alert.show();
    }

    private void parseLoginFunction(String email, String password) {

        ParseUser.logInInBackground(
                email,
                password,
                new LogInCallback() {
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Intent i = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            showDetailedError(e);
                        }
                    }
                });
    }



    private void showDetailedError(ParseException e) {
        Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    private boolean validateLoginForm() {
        if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty())
            return false;
        else
            return true;
    }

    private void goToRegisterScreen() {
        Intent i = new Intent(this, RegisterActivity.class);
        startActivity(i);
    }
}
