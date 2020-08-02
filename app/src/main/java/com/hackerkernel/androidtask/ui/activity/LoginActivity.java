package com.hackerkernel.androidtask.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hackerkernel.androidtask.R;
import com.hackerkernel.androidtask.api.RetrofitConfig;
import com.hackerkernel.androidtask.model.LoginModel;
import com.hackerkernel.androidtask.session.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();

    private ProgressBar pbLogin;
    private Button btnLogin;
    private EditText etEmail, etPassword;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupViews();
    }

    private void setupViews(){

        sessionManager = SessionManager.getInstance(this);

        if(sessionManager.isLoggedIn()){
            sessionManager.destroySession(this);
        }


        pbLogin = findViewById(R.id.pb_loading);
        btnLogin = findViewById(R.id.btn_login);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etEmail.getText().toString().isEmpty()){
                    etEmail.setError(getString(R.string.cannot_be_empty));
                }else if(etPassword.getText().toString().isEmpty()){
                    etPassword.setError(getString(R.string.cannot_be_empty));
                }else if(etPassword.getText().toString().length() < 6){
                    etPassword.setError(getString(R.string.must_be_at_least_6_characters));
                }else{
                    btnLogin.setVisibility(View.INVISIBLE);
                    pbLogin.setVisibility(View.VISIBLE);
                    Call<LoginModel> call = RetrofitConfig.config().login(etEmail.getText().toString(), etPassword.getText().toString(), "a");
                    call.enqueue(new Callback<LoginModel>() {
                        @Override
                        public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                            if(response.code() == 200){
                                sessionManager.createSession(response.body().getData().getName(), response.body().getData().getEmail());
                                Intent intent = new Intent(LoginActivity.this,  NavigationActivity.class);
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, getString(R.string.login_failed), Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<LoginModel> call, Throwable t) {
                            pbLogin.setVisibility(View.GONE);
                            btnLogin.setVisibility(View.INVISIBLE);
                            Log.e(TAG, "onFailure: ");
                            t.printStackTrace();
                            Toast.makeText(LoginActivity.this, getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}