package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtLoginId,edtLoginPassword;
    Button btnLogin;
    TextView txtNoAccount;
    String logInId,logInPass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login!");
        setId();
        setListener();
        progressDialog=new ProgressDialog(this);

    }
    public void setId(){
        edtLoginId=findViewById(R.id.loginId);
        edtLoginPassword=findViewById(R.id.loginPassword);
        btnLogin=findViewById(R.id.login);
        txtNoAccount=findViewById(R.id.noAccount);
    }
    public void setListener(){
        btnLogin.setOnClickListener(this);
        txtNoAccount.setOnClickListener(this);
    }
    public void setStringValues(){
        logInId=edtLoginId.getText().toString();
        logInPass=edtLoginPassword.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                setStringValues();
                if (logInId.equals("")){
                    edtLoginId.setError("Please Enter your Login Id!");
                    edtLoginId.requestFocus();
                }else if (logInPass.equals("")){
                    edtLoginPassword.setError("Please Enter your Password!");
                    edtLoginPassword.requestFocus();
                }else if (logInPass.length()<8){
                    edtLoginPassword.setError("Wrong Password Length!");
                    edtLoginPassword.requestFocus();
                }else {
                    login(logInId,logInPass);
                }
                break;
            case R.id.noAccount:
                goToSignUp();
                break;
        }
    }

    public void login(final String loginId, final String loginPass){
        progressDialog.setTitle("Creating User!");
        progressDialog.setMessage("Please wait while we are adding you to the server!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.0.63:8000/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                        Intent goToMainActivity = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(goToMainActivity);
                        finish();
                        Toast.makeText(LoginActivity.this, "Login Successfull!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Log.e("tag", "onErrorResponse: ",error );
                Toast.makeText(LoginActivity.this,error.toString() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<>();
                parms.put("email",loginId);
                parms.put("password",loginPass);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
    public void goToSignUp(){
        Intent goToSignUp = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivity(goToSignUp);
    }
}
