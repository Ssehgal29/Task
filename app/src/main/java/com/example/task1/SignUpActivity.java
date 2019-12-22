package com.example.task1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtFirstName,edtLastName,edtSignUpId,edtSignUpPassword;
    Button btnSignUp;
    String firstName,lastName,signUpID,signUpPass;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Sign Up!");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setId();
        setListener();
        progressDialog=new ProgressDialog(this);
    }
    public void setId(){
        edtFirstName=findViewById(R.id.firstName);
        edtLastName=findViewById(R.id.lastName);
        edtSignUpId=findViewById(R.id.signUpId);
        edtSignUpPassword=findViewById(R.id.signUpPassword);
        btnSignUp=findViewById(R.id.signup);
    }
    public void setListener(){
        btnSignUp.setOnClickListener(this);
    }
    public void setStringValues(){
        firstName=edtFirstName.getText().toString();
        lastName=edtLastName.getText().toString();
        signUpID=edtSignUpId.getText().toString();
        signUpPass=edtSignUpPassword.getText().toString();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signup:
                setStringValues();
                if (firstName.equals("")){
                    edtFirstName.setError("Please Enter your First Name!");
                    edtFirstName.requestFocus();
                }else if (lastName.equals("")){
                    edtLastName.setError("Please Enter your Last Name!");
                    edtLastName.requestFocus();
                }else if (signUpID.equals("")){
                    edtSignUpId.setError("Please Enter your Email Id!");
                    edtSignUpId.requestFocus();
                }else if (signUpPass.equals("")){
                    edtSignUpPassword.setError("Please Enter your Password!");
                    edtSignUpPassword.requestFocus();
                }else if (signUpPass.length()<8){
                    edtSignUpPassword.setError("Password length must be greater than 8!");
                    edtSignUpPassword.requestFocus();
                }else {
                    signUp(firstName,lastName,signUpID,signUpPass);
                }
                break;
        }
    }

    public void signUp(final String firstName , final String lastName , final String signUpID, final String signUpPass){
        progressDialog.setTitle("Creating User!");
        progressDialog.setMessage("Please wait while we are adding you to the server!");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, "http://192.168.0.63:8000/register", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                Intent goToMainActivity = new Intent(SignUpActivity.this,MainActivity.class);
                startActivity(goToMainActivity);
                finish();
                Toast.makeText(SignUpActivity.this, "SignUp Successfull!", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.hide();
                Log.e("tag", "onErrorResponse: ",error );
                Toast.makeText(SignUpActivity.this,error.toString() , Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()  {
                Map<String,String>parms=new HashMap<>();
                parms.put("first_name",firstName);
                parms.put("last_name",lastName);
                parms.put("email",signUpID);
                parms.put("password",signUpPass);
                return parms;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(SignUpActivity.this);
        requestQueue.add(stringRequest);

    }
}
