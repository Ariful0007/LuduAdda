package com.meass.diabeticeschecking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MyGameAdapter extends RecyclerView.Adapter<MyGameAdapter.myview> {
    public List<BloodBankModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;
    public MyGameAdapter(List<BloodBankModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public MyGameAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.subbb,parent,false);
        return new MyGameAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGameAdapter.myview holder, final int position) {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        holder.customer_name.setText("#"+data.get(position).getName());
        holder.customer_area.setText(data.get(position).getTime()+" BDT");
        holder.customer_number.setText(data.get(position).getFeeentry());
        holder.logout.setText(data.get(position).getFeeprice()+" BDT");
        firebaseFirestore.collection("ListAll")
                .document(data.get(position).getUuid())
                .collection("List")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            int ncount = 0;
                            for (DocumentSnapshot document : task.getResult()) {
                                ncount++;
                            }
                            holder.limit.setText(ncount+" /"+" 2");
                            if (ncount==2) {
                               holder.joinnow.setEnabled(false);
                                holder.joinnow.setText("Slot Full");
                            }
                            else {
                                holder.joinnow.setText("Join Now");
                               holder.joinnow.setEnabled(true);
                            }
                        }
                    }
                });
        firebaseFirestore.collection("ListMy")
                .document(firebaseAuth.getCurrentUser().getEmail())
                .collection("list")
                .document(data.get(position).getUuid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                holder.joinnow.setText("Joined");
                            }
                        }
                    }
                });

        holder.joinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Conformation")
                        .setMessage("Are you want to report this game ? ").setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        firebaseFirestore.collection("ReportList")
                                .document(firebaseAuth.getCurrentUser().getEmail())
                                .collection("list")
                                .document(data.get(position).getUuid())
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            if (task.getResult().exists()) {
                                                Toasty.info(view.getContext(),"Already submitted report.",Toasty.LENGTH_SHORT,true).show();

                                            }
                                            else {
                                                Intent intent=new Intent(view.getContext(),ReportListt.class);
                                                intent.putExtra("roomid",data.get(position).getRoomid());
                                                intent.putExtra("uuid",data.get(position).getUuid());
                                                view.getContext().startActivity(intent);

                                            }
                                        }
                                    }
                                });
                    }
                }).create().show();

            }
        });
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.youtube.com/"));
                view.getContext().startActivity(intent);
            }
        });
        holder.viewprice.setText("See room id");
        holder.viewprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Room ")
                        .setMessage("Your room id is : "+data.get(position).getRoomid()).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        EditText limit;
        Button joinnow,details,viewprice;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.nametext);
            customer_number=itemView.findViewById(R.id.secondimage);
            customer_area=itemView.findViewById(R.id.balance);
            logout=itemView.findViewById(R.id.invalid);
            limit=itemView.findViewById(R.id.limit);
            joinnow=itemView.findViewById(R.id.joinnow);
            details=itemView.findViewById(R.id.details);
            viewprice=itemView.findViewById(R.id.viewprice);
        }
    }
}
