package com.chidung091.chamcongvn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button bt1;
    Button bt2;
    EditText user;
    EditText pass;
    TextView tv2;
    private int counter=5;
    TextView tv;
    boolean isvalid=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.editTextTextPersonName3);
        pass = findViewById(R.id.editTextTextPassword3);
        bt1 = findViewById(R.id.button);
        bt2 = findViewById(R.id.button3);
        tv = findViewById(R.id.textView4);
        tv.setText("Bạn có 5 lần thử!");
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

