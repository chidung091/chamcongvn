package com.chidung091.mangxahoi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity2 extends AppCompatActivity {
    EditText email, pass1, pass2, name;
    Button btt1, btt2;
    ProgressBar pb;
    String UserID;
    String TAG;
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
        setContentView(R.layout.activity_main2);
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {
        }
        email = findViewById(R.id.emailfield);
        pass1 = findViewById(R.id.passfield);
        pass2 = findViewById(R.id.pass2field);
        name = findViewById(R.id.namefield);
        btt1 = findViewById(R.id.register2_btn);
        btt2 = findViewById(R.id.login2_btn);
        pb = findViewById(R.id.progressBar2);
        btt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString().trim();
                String pw1 = pass1.getText().toString().trim();
                String pw2 = pass2.getText().toString().trim();
                final String naem = name.getText().toString().trim();
                pb.setVisibility(View.VISIBLE);
                xacthucEmail();
                xacthucpw();
                if (!pw1.equals(pw2)) {
                    pass1.setError("Hai mật khẩu không trùng khớp");
                    return;
                }
                Register();
            }
        });
        btt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, MainActivity.class));
            }
        });
    }

    private boolean xacthucEmail() {
        String emailInput = email.getText().toString().trim();
        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }

    private boolean xacthucpw() {
        String passwordInput = pass1.getText().toString().trim();
        if (passwordInput.isEmpty()) {
            pass1.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            pass1.setError("Password too weak");
            return false;
        } else {
            pass1.setError(null);
            return true;
        }
    }

    public void Register() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setEmail(email.getText().toString().trim());
        registerRequest.setPassword(pass1.getText().toString().trim());
        registerRequest.setName(name.getText().toString().trim());
        Call<RegisterResponse> requestResponseCall = ApiClient.getUserService().userRegister(registerRequest);
        requestResponseCall.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                Log.d("response", response.toString());
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity2.this, "Đăng kí thành công tài khoản", Toast.LENGTH_SHORT).show();
                    RegisterResponse registerResponse = response.body();
                    startActivity(new Intent(MainActivity2.this, MainActivity3.class).putExtra("data", registerResponse.getId()));
                } else {
                    Toast.makeText(MainActivity2.this, "Sai thông tin đăng nhập.Vui lòng thử lại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(MainActivity2.this, "Lỗi " + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}