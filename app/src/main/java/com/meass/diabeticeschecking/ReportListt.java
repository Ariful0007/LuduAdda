package com.meass.diabeticeschecking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class ReportListt extends AppCompatActivity {
String roomid;
EditText limit;
Button joinnow;
ImageView image;


    TextView name_button,nameTv,namfeTv;
    private TextView namebutton;
    private CircleImageView primage;
    private TextView updateDetails;
    private LinearLayout wishlistView;
    private ImageSlider imageSlider;

    //to get user session data
    private UserSession session;
    private TextView tvemail,tvphone;
    private HashMap<String,String> user;
    private String name,email,photo,mobile;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    CircleImageView profileImage;
    private StorageReference mStorageRef;
    //
    TextView changeProfilePhoto;
    ImageButton image_button;
    ImageView imageView;
    private final int PICK_IMAGE_REQUEST = 71;
    private Uri filePath;//Firebase

    DocumentReference documentReference;
    Button floatingActionButton;
    FirebaseStorage storage;
    StorageReference storageReference;
    private static final int CAMERA_REQUEST = 1888;
    Button generate_btn;
    //doctor
    private static final int READCODE = 1;
    private static final int WRITECODE = 2;

    private Uri mainImageUri = null;
    String uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_listt);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setTitle("Report");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setElevation(10.0f);
        try {
            roomid=getIntent().getStringExtra("roomid");
            uuid=getIntent().getStringExtra("uuid");
        }catch (Exception e) {
            roomid=getIntent().getStringExtra("roomid");
            uuid=getIntent().getStringExtra("uuid");
        }
        limit=findViewById(R.id.limit);
        limit.setText(roomid);
        joinnow=findViewById(R.id.joinnow);
        image=findViewById(R.id.image);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore =FirebaseFirestore.getInstance();
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });
        storageReference = FirebaseStorage.getInstance().getReference();
        joinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(limit.getText().toString())) {
                    Toasty.error(view.getContext(),"Error",Toasty.LENGTH_SHORT,true).show();
                }
                else {
                    uploadImage();
                }
            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                image.setImageBitmap(bitmap);


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
    }
    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            StorageReference ref = storageReference.child("Doctor_images/"+ UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful());
                            final Uri downloadUri=uriTask.getResult();



                            if (uriTask.isSuccessful()) {
                                firebaseFirestore.collection("User2")
                                        .document(firebaseAuth.getCurrentUser().getEmail())
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    if (task.getResult().exists()) {
                                                        try {
                                                            Long tsLong = System.currentTimeMillis() / 1000;
                                                           String ts = tsLong.toString();
                                                            String username=task.getResult().getString("username");
                                                            String number=task.getResult().getString("number");
                                                            ImageUpload imageUpload=new ImageUpload(username,number,
                                                                    downloadUri.toString(),limit.getText().toString(),
                                                                    firebaseAuth.getCurrentUser().getEmail(),
                                                                    firebaseAuth.getCurrentUser().getUid(),uuid,ts);
                                                            firebaseFirestore.collection("Allreport")
                                                                    .document(uuid)
                                                                    .set(imageUpload)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {

                                                                        }
                                                                    });
                                                            firebaseFirestore.collection("ReportList")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .collection("list")
                                                                    .document(uuid)
                                                                    .set(imageUpload)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                progressDialog.dismiss();
                                                                                new AestheticDialog.Builder(ReportListt.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                        .setTitle("Conformation")
                                                                                        .setMessage("You have successfully submitted report to admin.")
                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                            @Override
                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                builder.dismiss();
                                                                                             startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                                                                                            }
                                                                                        }).show();
                                                                            }
                                                                        }
                                                                    });

                                                        }catch (Exception e) {
                                                            Long tsLong = System.currentTimeMillis() / 1000;
                                                            String ts = tsLong.toString();
                                                            String username=task.getResult().getString("username");
                                                            String number=task.getResult().getString("number");
                                                            ImageUpload imageUpload=new ImageUpload(username,number,
                                                                    downloadUri.toString(),limit.getText().toString(),
                                                                    firebaseAuth.getCurrentUser().getEmail(),
                                                                    firebaseAuth.getCurrentUser().getUid(),uuid,ts);
                                                            firebaseFirestore.collection("ReportList")
                                                                    .document(firebaseAuth.getCurrentUser().getEmail())
                                                                    .collection("list")
                                                                    .document(uuid)
                                                                    .set(imageUpload)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            if (task.isSuccessful()) {
                                                                                progressDialog.dismiss();
                                                                                new AestheticDialog.Builder(ReportListt.this, DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                        .setTitle("Conformation")
                                                                                        .setMessage("You have successfully submitted report to admin.")
                                                                                        .setAnimation(DialogAnimation.SPLIT)
                                                                                        .setOnClickListener(new OnDialogClickListener() {
                                                                                            @Override
                                                                                            public void onClick(AestheticDialog.Builder builder) {
                                                                                                builder.dismiss();
                                                                                                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));

                                                                                            }
                                                                                        }).show();
                                                                            }
                                                                        }
                                                                    });
                                                        }
                                                    }
                                                }
                                            }
                                        });



                            }
                            else {
                                progressDialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
                            }

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ReportListt.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }
}