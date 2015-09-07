package com.tesis.alejofila.centrocomercial;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tesis.alejofila.centrocomercial.http.Callback;
import com.tesis.alejofila.centrocomercial.http.Constants;
import com.tesis.alejofila.centrocomercial.http.ResultCallback;
import com.tesis.alejofila.centrocomercial.http.ServiceMannager;

/**
 * Created by tales on 4/09/15.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Constants
     */
    private static final String TAG = RegisterActivity.class.getSimpleName();


    EditText edtName, edtEmail, edtPassword, edtPassword2;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtName = (EditText) findViewById(R.id.edt_register_name);
        edtEmail = (EditText) findViewById(R.id.edt_register_email);
        edtPassword = (EditText) findViewById(R.id.edt_register_password);
        edtPassword2 = (EditText) findViewById(R.id.edt_register_password_2);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);



    }

    @Override

    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    /*
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        GCMConstants.PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
    */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                if (validateRegistrationForm()) {
                    final ProgressDialog pd =  ProgressDialog.show(this,"Registrando usuario","Espere Por favor",false);
                    /**
                    if (checkPlayServices()) {
                        Intent i = new Intent(this, RegistrationIntentService.class);
                        startService(i);
                    }
                     **/
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(edtEmail.getText().toString());
                    parseUser.setEmail(edtEmail.getText().toString());
                    parseUser.setPassword(edtPassword.getText().toString());
                    parseUser.put(Constants.NAME,edtName.getText().toString());

                    parseUser.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {

                            if (e != null) {
                                showDetailedError(e);
                                pd.dismiss();
                            }
                            else {
                                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                                i.putExtra(Constants.EMAIL, edtEmail.getText().toString());
                                i.putExtra(Constants.PASSWORD, edtPassword.getText().toString());
                                pd.dismiss();
                                startActivity(i);
                                finish();
                            }

                        }
                    });
                }
                break;
        }


    }

    private void showDetailedError(ParseException e) {
        Toast.makeText(this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
    }

    private void registerFunction(String token) {
        ServiceMannager.registerFunction(edtName.getText().toString(),
                edtEmail.getText().toString(),
                edtPassword.getText().toString(),
                token,
                registerCallback
        );
    }

    private boolean validateRegistrationForm() {
        if (edtName.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty() || edtPassword2.getText().toString().isEmpty()) {
            Toast.makeText(this, R.string.login_missing_values, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!edtPassword.getText().toString().equals(edtPassword2.getText().toString())) {
                Toast.makeText(this, "Los passwords no concuerdan", Toast.LENGTH_SHORT).show();
                return false;
            }
        }

        return true;
    }

    private Callback registerCallback = new Callback() {
        @Override
        public void receiveResult(ResultCallback result) {

            if(result.isValido()) {
                Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                i.putExtra(Constants.EMAIL,edtEmail.getText().toString());
                i.putExtra(Constants.PASSWORD,edtPassword.getText().toString());
                startActivity(i);
            }
            else{
                Toast.makeText(RegisterActivity.this,"Hubo un problema al intentar registrarte, intentalo luego",Toast.LENGTH_SHORT).show();
            }
        }
    };
}
