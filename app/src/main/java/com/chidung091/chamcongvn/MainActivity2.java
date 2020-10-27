package com.chidung091.chamcongvn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class MainActivity2 extends AppCompatActivity {
    EditText email,pass1,pass2,name;
    Button btt1,btt2;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
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
        try{
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        email = findViewById(R.id.emailfield);
        pass1 = findViewById(R.id.passfield);
        pass2 = findViewById(R.id.pass2field);
        name = findViewById(R.id.namefield);
        btt1 = findViewById(R.id.register2_btn);
        btt2 = findViewById(R.id.login2_btn);
        pb = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        btt1.setOnClickListener(new View.OnClickListener(){
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
                    fAuth.createUserWithEmailAndPassword(em,pw1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity2.this,"Đăng k thành công tài khoản",Toast.LENGTH_SHORT).show();
                                UserID = fAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = fStore.collection("tenngdung").document(UserID);
                                Map<String,Object> user = new HashMap<>();
                                user.put("name",naem);
                                user.put("Uid",UserID);
                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG,"Ghi thành công:"+ UserID);
                                    }
                                });
                                startActivity(new Intent(MainActivity2.this,MainActivity3.class));
                            } else{
                                Toast.makeText(MainActivity2.this,"Lỗi" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                pb.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
            }
        });
        btt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this,MainActivity.class));
            }
        });
    }
    private boolean xacthucEmail(){
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
    private boolean xacthucpw(){
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
}