package com.example.fabrice.gestionlivraison.activities;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fabrice.gestionlivraison.R;
import com.example.fabrice.gestionlivraison.util.LoginStrategy;
import com.example.fabrice.gestionlivraison.util.PasswordHelper;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity implements LoginStrategy.AsyncResponse{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @InjectView(R.id.input_email)
    EditText _txtLogin;

    @InjectView(R.id.input_password)
    EditText _txtPassword;

    @InjectView(R.id.btn_login)
    Button _loginButton;

    @InjectView(R.id.lbl_badConnexionInfo)
    TextView _lblBadInfo;

    private String login;
    private String password;

    ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

    }

    public void onLoginToApp(View view){
        if(validate())
        {
            loginToApp();
        }
    }

    public void loginToApp() {
        Log.d(TAG, "Login");

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.AppTheme);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        // TODO: Implement your own authentication logic here.

        if(isOnline())
        {
            //we need to hash the password in md5
            password = PasswordHelper.md5(password);

            LoginStrategy strategy = new LoginStrategy(this);
            strategy.execute(login, password);
        }
    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_SIGNUP)
//        {
//            if (resultCode == RESULT_OK)
//            {
//
//                // TODO: Implement successful signup logic here
//                // By default we just finish the Activity and log them in automatically
//                this.finish();
//            }
//        }
//    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(intent, REQUEST_SIGNUP);
    }

    public void onLoginFailed() {
        _lblBadInfo.setVisibility(View.VISIBLE);
    }

    public boolean validate() {
        boolean valid = false;
        login = _txtLogin.getText().toString();
        password = _txtPassword.getText().toString();

        if(login.isEmpty())
        {
            _txtLogin.setError("Champs vide");
        }
        if(password.isEmpty())
        {
            _txtPassword.setError("Champs vide");
        }

        if(!login.isEmpty() && !password.isEmpty()) {
            valid = true;
        }

        return valid;
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void processFinish(String status) {
        progressDialog.dismiss();

        if(LoginStrategy.AUTHORIZED.equals(status))
        {
            onLoginSuccess();
        }
        else
        {
            onLoginFailed();
        }
    }
}