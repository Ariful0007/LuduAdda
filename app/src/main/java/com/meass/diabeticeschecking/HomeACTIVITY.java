package com.meass.diabeticeschecking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeACTIVITY extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    CardView card_view2;
    KProgressHUD progressHUD;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String  email;
    int count = 0,count1=0;
    TextView totaluser,paid,pending,blocking,notificationn,invalid,todaystask,margo_signup;
    int count11=0,countpaid,block=0;


    //
    EditText methodename,minwith,hint;
    Button logo,login_button;
    ImageView myImage;
    FirebaseStorage storage;
    StorageReference storageReference;
    Dialog mDialog;
    EditText name,ammount;

    private Toolbar mainToolbar;
    private String current_user_id;
    private BottomNavigationView mainBottomNav;
    private DrawerLayout mainDrawer;
    private ActionBarDrawerToggle mainToggle;
    private NavigationView mainNav;

    FrameLayout frameLayout;
    private TextView drawerName;
    private CircleImageView drawerImage;

    private FirebaseFirestoreSettings settings;
    private DatabaseReference mUserRef;
//lo

    private LottieAnimationView tv_no_item;
    private LinearLayout activitycartlist;
    private LottieAnimationView emptycart;
    //v

    DocumentReference documentReference;
    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_a_c_t_i_v_i_t_y);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Ludo Gamer Adda");
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        firebaseAuth=FirebaseAuth.getInstance();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        //fragment
        drawerLayout = findViewById(R.id.drawer);
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        progressHUD = KProgressHUD.create(HomeACTIVITY.this);

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        mainNav = findViewById(R.id.nav_view);
        mainNav.setNavigationItemSelectedListener(this);
        mainNav = findViewById(R.id.nav_view);
        View headerView = mainNav.getHeaderView(0);
        drawerName = headerView.findViewById(R.id.nav_name);
        drawerImage = headerView.findViewById(R.id.nav_image);
        firebaseFirestore.collection("User2")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {

                                    drawerName.setText(task.getResult().getString("name"));
                                }catch (Exception e) {

                                    drawerName.setText(task.getResult().getString("name"));
                                }

                            }
                            else {
                            }
                        }
                        else {
                        }
                    }
                });
        firebaseFirestore.collection("Counter")

                .document("abc@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    String first=task.getResult().getString("first");
                                    if (Integer.parseInt(first)==1) {
                                        firebaseFirestore.collection("CurrentLottery")
                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful()) {
                                                            if (task.getResult().exists()) {
                                                                try {
                                                                    String first=task.getResult().getString("first");
                                                                    String counter=task.getResult().getString("counter");
                                                                    if (Integer.parseInt(counter)==0) {
                                                                        firebaseFirestore.collection("DoneeList")
                                                                                .document("List")
                                                                                .collection(first.toLowerCase().toString())
                                                                                .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                .get()
                                                                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                    @Override
                                                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                        if (task.isSuccessful()) {
                                                                                            if (task.getResult().exists()) {
                                                                                                new AestheticDialog.Builder(HomeACTIVITY.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                        .setTitle("Congratulations")
                                                                                                        .setMessage("You are the winner of this lottery.")
                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                builder.dismiss();
                                                                                                                firebaseFirestore.collection("CurrentLottery")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                        .update("counter","1")
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                                            }
                                                                                                                        });

                                                                                                            }
                                                                                                        }).show();
                                                                                            }
                                                                                            else {
                                                                                                new AestheticDialog.Builder(HomeACTIVITY.this, DialogStyle.FLASH, DialogType.ERROR)
                                                                                                        .setTitle("Opps")
                                                                                                        .setMessage("You are the losser of this lottery.")
                                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                                            @Override
                                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                                builder.dismiss();
                                                                                                                firebaseFirestore.collection("CurrentLottery")
                                                                                                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                                        .update("counter","1")
                                                                                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                            @Override
                                                                                                                            public void onComplete(@NonNull Task<Void> task) {

                                                                                                                            }
                                                                                                                        });

                                                                                                            }
                                                                                                        }).show();
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                });

                                                                    }

                                                                }catch (Exception e) {
                                                                }
                                                            }
                                                        }
                                                    }
                                                });
                                    }
                                }catch (Exception e) {
                                }
                            }
                        }
                    }
                });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.addNote11:
                startActivity(new Intent(getApplicationContext(),SecondHome.class));
                break;
            case R.id.winnar:
                startActivity(new Intent(getApplicationContext(),Winnar.class));
                break;
            case R.id.helpl__1:
                firebaseFirestore.collection("Links")
                        .document("face@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            Intent intent=new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(task.getResult().getString("link")));
                                            startActivity(intent);
                                        }catch (Exception e) {
                                            Intent intent=new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(task.getResult().getString("link")));
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        });
                break;
                //
            case R.id.helpl__d1:
                firebaseFirestore.collection("Links")
                        .document("face@gmail.com")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    if (task.getResult().exists()) {
                                        try {
                                            Intent intent=new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(task.getResult().getString("group")));
                                            startActivity(intent);
                                        }catch (Exception e) {
                                            Intent intent=new Intent(Intent.ACTION_VIEW);
                                            intent.setData(Uri.parse(task.getResult().getString("group")));
                                            startActivity(intent);
                                        }
                                    }
                                }
                            }
                        });
                break;
            //
            case R.id.ploicey:
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.facebook.com/"));
                startActivity(intent);
                break;
            case R.id.helpl:
                startActivity(new Intent(getApplicationContext(),HelpLineActivity.class));
                break;
            case R.id.infooo:
                startActivity(new Intent(getApplicationContext(),AdminPolicy.class));
                break;
            case R.id.shareapp2:
                AlertDialog.Builder builder=new AlertDialog.Builder(HomeACTIVITY.this);
                builder.setTitle("Logout")
                        .setMessage("Are you want to logout?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        firebaseAuth.signOut();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                }).create().show();
                break;
        }
        return false;
    }
    @Override
    public void onBackPressed() {

            AlertDialog.Builder builder = new AlertDialog.Builder(HomeACTIVITY.this);
            builder.setTitle("Exit")
                    .setMessage("Are you want to exit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            finishAffinity();

                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create().show();
        }


    public void deposite(View view)
    {
        startActivity(new Intent(getApplicationContext(),BuyCoin.class));
    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
    }

    public void withdraal(View view) {
        startActivity(new Intent(getApplicationContext(),PaymentActivity.class));
    }

    public void history(View view) {
        startActivity(new Intent(getApplicationContext(),History.class));
    }

    public void helpline(View view) {
        startActivity(new Intent(getApplicationContext(),HelpLineActivity.class));
    }

    public void page(View view) {
        firebaseFirestore.collection("Links")
                .document("face@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    Intent intent=new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(task.getResult().getString("link")));
                                    startActivity(intent);
                                }catch (Exception e) {
                                    Intent intent=new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(task.getResult().getString("link")));
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                });
    }

    public void group(View view) {
        firebaseFirestore.collection("Links")
                .document("face@gmail.com")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                try {
                                    Intent intent=new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(task.getResult().getString("group")));
                                    startActivity(intent);
                                }catch (Exception e) {
                                    Intent intent=new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse(task.getResult().getString("group")));
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                });
    }

    public void privecy(View view) {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.facebook.com/"));
        startActivity(intent);
    }

    public void terms(View view) {
        startActivity(new Intent(getApplicationContext(),AdminPolicy.class));
    }

    public void lottery(View view) {
        Intent intent=new Intent(getApplicationContext(),LotteryList.class);
       startActivity(intent);
    }
}