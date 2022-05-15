package com.meass.diabeticeschecking;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.myview> {
    public List<LotteryModel> data;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth firebaseAuth;

    public LotteryAdapter(List<LotteryModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LotteryAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.imagegine,parent,false);
        return new LotteryAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LotteryAdapter.myview holder, final int position) {
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore.collection("AdminRequest1")
                .document(data.get(position).getLottery_name().toLowerCase().toString())
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
                            holder.customer_name.setText("Lottery Name : "+data.get(position).getLottery_name()+"\n"
                                    +"Lottery Price :"+data.get(position).getLottery_Price()+"\nLottery Winner Prize : "+data.get(position).getLottery_prize()+"\n"+"Lottery Range : "+String.valueOf(ncount)+" / "+data.get(position).getLottery_baki_ase());

                        }
                    }
                });
        firebaseFirestore.collection("Donee")
                .document(data.get(position).getLottery_name().toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                holder.dddddd.setVisibility(View.GONE);
                            }
                            else {
                                holder.dddddd.setVisibility(View.VISIBLE);
                            }
                        }
                        else {
                            holder.dddddd.setVisibility(View.VISIBLE);
                        }
                    }
                });


        holder.dddddd.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
        builder.setTitle("Conform")
                .setMessage("Are you want to take this lottery?")
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                final KProgressHUD progressDialog=  KProgressHUD.create(view.getContext())
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Checking Data.....")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                firebaseFirestore.collection("AdminRequest1")
                        .document(data.get(position).getLottery_name().toLowerCase().toString())
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
                                   if (ncount==Integer.parseInt(data.get(position).getLottery_baki_ase())) {
                                       progressDialog.dismiss();
                                       Toasty.error(view.getContext(),"There is no lottery. Range is fill up",Toasty.LENGTH_SHORT,true).show();
                                       return;
                                   }
                                   else {
                                       firebaseAuth= FirebaseAuth.getInstance();
                                       firebaseFirestore= FirebaseFirestore.getInstance();
                                       firebaseFirestore.collection("Users")
                                               .document(firebaseAuth.getCurrentUser().getUid())
                                               .collection("Main_Balance")
                                               .document(firebaseAuth.getCurrentUser().getEmail())
                                               .get()
                                               .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                       if (task.isSuccessful()) {
                                                           if (task.getResult().exists()) {
                                                               String purches_balance=task.getResult().getString("main_balance");
                                                               String shoping_balance=task.getResult().getString("purches_balance");
                                                               String self_income=task.getResult().getString("monthly_income");
                                                               String daily_income=task.getResult().getString("daily_income");
                                                 if (Integer.parseInt(data.get(position).getLottery_Price())>Integer.parseInt(shoping_balance)) {
                                                     progressDialog.dismiss();
                                                     Toasty.error(view.getContext(),"You havn't much money to take this lottery.",Toasty.LENGTH_SHORT,true).show();
                                                     return;
                                                 }
                                                 else {
                                                     int second=Integer.parseInt(shoping_balance)-Integer.parseInt(data.get(position).getLottery_Price());
                                                     firebaseFirestore.collection("Users")
                                                             .document(firebaseAuth.getCurrentUser().getUid())
                                                             .collection("Main_Balance")
                                                             .document(firebaseAuth.getCurrentUser().getEmail())
                                                             .update("purches_balance",""+second)
                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {

                                                                 }
                                                             });
                                                     Map<String, Object> user1 = new HashMap<>();
                                                     user1.put("first", ""+data.get(position).getLottery_name().toLowerCase());
                                                     user1.put("counter", "0");
                                                     firebaseFirestore.collection("CurrentLottery")
                                                             .document(firebaseAuth.getCurrentUser().getEmail())
                                                             .set(user1)
                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                     if (task.isSuccessful()) {
                                                                     }
                                                                 }
                                                             });
                                                     LotteryModel lotteryModel=new LotteryModel(data.get(position).getLottery_name(),data.get(position).getLottery_Price(),
                                                             data.get(position).getLottery_prize(),data.get(position).getLottery_baki_ase(),data.get(position).getTime(),data.get(position).getImage());
                                                     firebaseFirestore.collection("MyLottery").document(firebaseAuth.getCurrentUser().getEmail())
                                                             .collection("List").document(data.get(position).getTime()).set(lotteryModel)
                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                     if (task.isSuccessful()) {
                                                                         firebaseFirestore.collection("User2")
                                                                                 .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                 .get()
                                                                                 .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                                     @Override
                                                                                     public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                                                                         if (task.isSuccessful()) {
                                                                                             if (task.getResult().exists()) {
                                                                                                 try {

                                                                                                    String username=task.getResult().getString("username");
                                                                                                     Lottery_Model lottery_model=new Lottery_Model(username,firebaseAuth.getCurrentUser().getEmail(),data.get(position).getLottery_name(),data.get(position).getLottery_Price(),
                                                                                                             data.get(position).getLottery_prize(),data.get(position).getTime(),firebaseAuth.getCurrentUser().getEmail(),firebaseAuth.getCurrentUser().getUid(),data.get(position).getImage());
                                                                                                     /*
                                                                                                     firebaseFirestore.collection(data.get(position).getLottery_name().toUpperCase().toString()).add(lottery_model)
                                                                                                             .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                                                                                                 @Override
                                                                                                                 public void onComplete(@NonNull Task<DocumentReference> task) {

                                                                                                                 }
                                                                                                             });
                                                                                                      */
                                                                                                     firebaseFirestore.collection("AdminRequest1")
                                                                                                             .document(data.get(position).getLottery_name().toLowerCase().toString())
                                                                                                             .collection("List")
                                                                                                             .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                             .set(lottery_model)
                                                                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                 @Override
                                                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                                                     if (task.isSuccessful()) {
                                                                                                                         progressDialog.dismiss();
                                                                                                                         new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                 .setTitle("Conformation")
                                                                                                                                 .setMessage("You are successfully taken this lottery.")
                                                                                                                                 .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                 .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                     @Override
                                                                                                                                     public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                         builder.dismiss();
                                                                                                                                         view.getContext().startActivity(new Intent(view.getContext(),SecondHome.class));

                                                                                                                                     }
                                                                                                                                 }).show();

                                                                                                                     }
                                                                                                                 }
                                                                                                             });

                                                                                                 }catch (Exception e) {

                                                                                                     String username=task.getResult().getString("username");
                                                                                                     Lottery_Model lottery_model=new Lottery_Model(username,firebaseAuth.getCurrentUser().getEmail(),data.get(position).getLottery_name(),data.get(position).getLottery_Price(),
                                                                                                             data.get(position).getLottery_prize(),data.get(position).getTime(),firebaseAuth.getCurrentUser().getEmail(),firebaseAuth.getCurrentUser().getUid(),data.get(position).getImage());
                                                                                                     firebaseFirestore.collection("AdminRequest1")
                                                                                                             .document(data.get(position).getLottery_name().toLowerCase().toString())
                                                                                                             .collection("List")
                                                                                                             .document(firebaseAuth.getCurrentUser().getEmail())
                                                                                                             .set(lottery_model)
                                                                                                             .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                                                 @Override
                                                                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                                                                     if (task.isSuccessful()) {
                                                                                                                         progressDialog.dismiss();
                                                                                                                         new AestheticDialog.Builder((Activity) view.getContext(), DialogStyle.FLASH, DialogType.SUCCESS)
                                                                                                                                 .setTitle("Conformation")
                                                                                                                                 .setMessage("You are successfully taken this lottery.")
                                                                                                                                 .setAnimation(DialogAnimation.SPLIT)
                                                                                                                                 .setOnClickListener(new OnDialogClickListener() {
                                                                                                                                     @Override
                                                                                                                                     public void onClick(AestheticDialog.Builder builder) {
                                                                                                                                         builder.dismiss();
                                                                                                                                         view.getContext().startActivity(new Intent(view.getContext(),SecondHome.class));

                                                                                                                                     }
                                                                                                                                 }).show();

                                                                                                                     }
                                                                                                                 }
                                                                                                             });
                                                                                                 }

                                                                                             }
                                                                                             else {
                                                                                             }
                                                                                         }
                                                                                         else {
                                                                                         }
                                                                                     }
                                                                                 });
                                                                     }
                                                                 }
                                                             });

                                                 }


                                                           }
                                                       }
                                                   }
                                               });

                                   }
                                }
                            }
                        });

            }
        }).create().show();
    }
});
        try {
            Picasso.get().load(data.get(position).getImage()).placeholder(R.drawable.raffle).into(holder.ddimage);
        }catch (Exception e) {
            Picasso.get().load(data.get(position).getImage()).placeholder(R.drawable.raffle).into(holder.ddimage);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        CardView dddddd;
        ImageView ddimage;
        public myview(@NonNull View itemView) {
            super(itemView);
           customer_name=itemView.findViewById(R.id.lottery_card);
            dddddd=itemView.findViewById(R.id.card_view8);
            ddimage=itemView.findViewById(R.id.ddimage);
        }
    }
}

