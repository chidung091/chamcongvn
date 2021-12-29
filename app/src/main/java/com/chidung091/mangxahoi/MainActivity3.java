package com.chidung091.mangxahoi;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {
    TextView a;
    String Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        try{
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        Intent intent = getIntent();
        if(intent.getExtras()!= null){
            String token = intent.getStringExtra("data");
            a.setText(token);
        }
    }
    public void logout(View view){
        startActivity(new Intent(MainActivity3.this,MainActivity.class));
    }
}
