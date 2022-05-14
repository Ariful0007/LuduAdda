package com.meass.diabeticeschecking;

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

import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LotteryAdapter11 extends RecyclerView.Adapter<LotteryAdapter11.myview> {
    public List<LotteryModel> data;
    FirebaseFirestore firebaseFirestore;

    public LotteryAdapter11(List<LotteryModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public LotteryAdapter11.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.sss,parent,false);
        return new LotteryAdapter11.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LotteryAdapter11.myview holder, final int position) {
        holder.customer_name.setText("Lottery Name : "+data.get(position).getLottery_name()+"\n"
               +" Lottery Price :"+data.get(position).getLottery_Price()+"\nLottery Winner Prize : "+data.get(position).getLottery_prize());


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
            dddddd=itemView.findViewById(R.id.dddddd);
            ddimage=itemView.findViewById(R.id.ddimage);
        }
    }
}

