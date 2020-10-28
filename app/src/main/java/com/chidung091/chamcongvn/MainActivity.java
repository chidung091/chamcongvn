package com.chidung091.chamcongvn;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
                fAuth.signInWithEmailAndPassword(em,mk).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Đăng nhập thành công tài khoản",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,MainActivity3.class));

                        }else{
                            Toast.makeText(MainActivity.this,"Lỗi" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.INVISIBLE);
                            AlertDialog.Builder dki = new AlertDialog.Builder(view.getContext());
                            dki.setTitle("Lỗi đăng nhập!");
                            dki.setMessage("Bạn có muốn đăng kí tài khoản không?");
                            dki.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    startActivity(new Intent(MainActivity.this,MainActivity2.class));
                                }
                            });
                            dki.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                        }
                    }
                });
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
}

