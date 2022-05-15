package com.meass.diabeticeschecking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class LuduType extends AppCompatActivity {
FirebaseFirestore firebaseFirestore;
TextView one,two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ludu_type);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Ludu Game Type");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        firebaseFirestore=FirebaseFirestore.getInstance();
        one=findViewById(R.id.one);
        two=findViewById(R.id.two);
        firebaseFirestore.collection("Game1")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            one.setText("Ludu King (1vs1)-"+ncount+"Games");
                        }
                    }
                });
        firebaseFirestore.collection("Game2")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            two.setText("Ludu King (1vs2)-"+ncount+"Games");
                        }
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), HomeACTIVITY.class));
    }

    public void helpline(View view) {
        startActivity(new Intent(getApplicationContext(), Type1Game.class));
    }

    public void helpline1(View view) {
        startActivity(new Intent(getApplicationContext(), Type2Game.class));
    }
}