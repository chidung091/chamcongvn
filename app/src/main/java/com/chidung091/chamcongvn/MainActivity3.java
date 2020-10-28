package com.chidung091.chamcongvn;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity3 extends AppCompatActivity {
    TextView a;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String Userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        try{
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        Userid = fAuth.getCurrentUser().getUid();
        a = findViewById(R.id.textView);
        DocumentReference documentReference = fStore.collection("tenngdung").document(Userid);
        documentReference.addSnapshotListener(this,new EventListener<DocumentSnapshot>(){

            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                a.setText(value.getString("name"));
            }
        });
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity3.this,MainActivity.class));
    }
}
