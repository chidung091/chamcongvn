package com.chidung091.chamcongvn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button bt1;
    Button bt2;
    EditText user;
    EditText pass;
    ProgressBar pb;
    FirebaseAuth fAuth;
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        user = findViewById(R.id.emailfield);
        pass = findViewById(R.id.passfield);
        bt1 = findViewById(R.id.login_btn);
        bt2 = findViewById(R.id.register_btn);
        pb = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();
        bt1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(final View view) {
                String em = user.getText().toString().trim();
                String mk = pass.getText().toString().trim();
                xacthucEmail();
                xacthucpw();
                pb.setVisibility(View.VISIBLE);
                //Xac thuc nguoi dung
                if(TextUtils.isEmpty(em)){
                    Toast.makeText(MainActivity.this,"Yêu cầu nhập email",Toast.LENGTH_LONG);
                }
                if(TextUtils.isEmpty(mk)){
                    Toast.makeText(MainActivity.this,"Yêu cầu nhập mật khẩu dùm",Toast.LENGTH_LONG);
                }
                else{
                    Login();
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,MainActivity2.class));
            }
        });
    }
    private boolean xacthucEmail(){
        String emailInput = user.getText().toString().trim();
        if (emailInput.isEmpty()) {
            user.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            user.setError("Please enter a valid email address");
            return false;
        } else {
            user.setError(null);
            return true;
        }
    }
    private boolean xacthucpw(){
        String passwordInput = pass.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            pass.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass.setError("Password too weak");
            return false;
        } else {
            pass.setError(null);
            return true;
        }
    }
    public void Login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(user.getText().toString().trim());
        loginRequest.setPassword(user.getText().toString().trim());
        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    Toast.makeText(MainActivity.this,"Đăng nhập thành công tài khoản",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this,MainActivity3.class));
                }else{
                    Toast.makeText(MainActivity.this,"Sai thông tin đăng nhập.Vui lòng thử lại",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Lỗi "+t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }
        });
    }
}

