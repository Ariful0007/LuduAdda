package com.meass.diabeticeschecking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import es.dmoral.toasty.Toasty;

public class BuyCoin extends AppCompatActivity {

    private EditText spinnerTextSize,spinnerTextSize1,spinnerTextSize2;
    EditText Email_Log;
    EditText spinner1;
    String valueFromSpinner;
    String valueFromSpinner1;
    String valueFromSpinner2;
    Button upgrade;
    DocumentReference documentReference,documentReference1;
    RecyclerView recyclerView,rreeeewd;
    PackageInAdapter getDataAdapter1,getDataAdapter2;
    List<Package> getList,getList2;
    String url;

    FirebaseUser firebaseUser;
    KProgressHUD progressHUD;
    String cus_name;
    LottieAnimationView empty_cart,empty_carwt;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_coin);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Deposite");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        upgrade=findViewById(R.id.upgrade);

        spinner1=findViewById(R.id.spinner1);

        upgrade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(spinner1.getText().toString())) {
                    Toasty.error(getApplicationContext(),"Enter amount of balance.", Toast.LENGTH_SHORT,true).show();
                }
                else {
                    if(Integer.parseInt(spinner1.getText().toString())<50 ||
                            Integer.parseInt(spinner1.getText().toString())>1000) {
                        Toasty.info(getApplicationContext(),"Deposite  balance range is 50-1000.",Toast.LENGTH_SHORT,true).show();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), ConfromActivity.class);
                        intent.putExtra("package", "Balance Request");
                        intent.putExtra("price", spinner1.getText().toString());
                        intent.putExtra("packing", "Balance Request");
                        startActivity(intent);
                    }
                }

            }
        });
        /*
        final FlatDialog flatDialog = new FlatDialog(AllUserActivity.this);
                                                   flatDialog.setIcon(R.drawable.crying)
                                                           .setTitle("User have not enough money !")
                                                           .setTitleColor(Color.parseColor("#000000"))
                                                           .setSubtitle("choose an action")
                                                           .setSubtitleColor(Color.parseColor("#000000"))
                                                           .setBackgroundColor(Color.parseColor("#a26ea1"))
                                                           .setFirstButtonColor(Color.parseColor("#f18a9b"))
                                                           .setFirstButtonTextColor(Color.parseColor("#000000"))
                                                           .setFirstButtonText("Cancel")
                                                           .withFirstButtonListner(new View.OnClickListener() {
                                                               @Override
                                                               public void onClick(View view) {
                                                                   flatDialog.dismiss();
                                                               }
                                                           })
                                                           .show();
         */
        //
        empty_cart=findViewById(R.id.empty_cart);
          /*
            if (valueFromSpinner.contains("Select Your Package")) {
                Toast.makeText(this, ""+valueFromSpinner, Toast.LENGTH_SHORT).show();
            }
            else {
                upgrade.setEnabled(true);
            }
           */

        firebaseFirestore = FirebaseFirestore.getInstance();
        //counting
        firebaseFirestore.collection("MyPackage")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int count = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                count++;
                            }
                            if (count == 0) {
                                empty_cart.setVisibility(View.VISIBLE);

                            } else {
                                empty_cart.setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                });

        getList = new ArrayList<>();
        getDataAdapter1 = new PackageInAdapter(getList);
        firebaseFirestore = FirebaseFirestore.getInstance();
        documentReference  = firebaseFirestore.collection("MyPackage")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List").document();
        recyclerView =findViewById(R.id.rreeeed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BuyCoin.this));
        recyclerView.setAdapter(getDataAdapter1);
        reciveData();


    }

    private void reciveData() {
        firebaseFirestore.collection("MyPackage")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("List").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                for (DocumentChange ds : queryDocumentSnapshots.getDocumentChanges()) {
                    if (ds.getType() == DocumentChange.Type.ADDED) {

                 /*String first;
                 first = ds.getDocument().getString("name");
                 Toast.makeText(MainActivity2.this, "" + first, Toast.LENGTH_SHORT).show();*/
                        Package get = ds.getDocument().toObject(Package.class);
                        getList.add(get);
                        getDataAdapter1.notifyDataSetChanged();
                    }

                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HomeACTIVITY.class));
    }
}