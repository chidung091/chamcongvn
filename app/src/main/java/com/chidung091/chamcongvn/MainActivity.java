package com.chidung091.chamcongvn;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    Button bt1;
    Button bt2;
    EditText user;
    EditText pass;
    TextView tv2;
    private int counter=5;
    TextView tv;
    boolean isvalid=false;
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    private String BASE_URL ="http://localhost:27017";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitInterface = retrofit.create(RetrofitInterface.class);
        user = findViewById(R.id.editTextTextPersonName3);
        pass = findViewById(R.id.editTextTextPassword3);
        bt1 = findViewById(R.id.button);
        bt2 = findViewById(R.id.button3);
        tv = findViewById(R.id.textView4);
        tv.setText("Bạn có 5 lần thử!");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String, String> map = new HashMap<>();
                map.put("tdn",user.getText().toString());
                map.put("pass",pass.getText().toString());
                Call<LoginResult> call = retrofitInterface.executeLogin(map);
                call.enqueue(new Callback<LoginResult>() {
                    @Override
                    public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                        if(response.code() == 200){
                            LoginResult rt = response.body();
                            AlertDialog.Builder bd1 = new AlertDialog.Builder(MainActivity.this);
                            bd1.setTitle(rt.getName());
                            bd1.setMessage(rt.getEmail());
                            bd1.show();
                        }
                        else if(response.code() == 404){
                            Toast.makeText(MainActivity.this,"Sai thong tin!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });
                String st1 = user.getText().toString();
                String st2 = pass.getText().toString();
                isvalid=xacthuc(st1,st2);
                if(!isvalid){
                    counter--;
                    tv.setText("Bạn còn " + String.valueOf(counter) + " lần thử!");
                    if (counter == 0) {
                        bt1.setEnabled(false);
                    }
                }
                else{
                    Intent it = new Intent(MainActivity.this, MainActivity2.class);
                    startActivity(it);
                }
            }
        });
        bt2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                user.clearComposingText();
                pass.clearComposingText();
            }
        });
    }
    private boolean xacthuc(String un1,String pw1){
        String un = "queanh99";
        String pw = "liengrang";
        if((un1.equals(un)) && (pw1.equals(pw))){
            return true;
        }else{
            return false;
            }
        }
    }

