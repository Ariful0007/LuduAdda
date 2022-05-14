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

public class WinnarAdapter extends RecyclerView.Adapter<WinnarAdapter.myview> {
    public List<Lottery_Model> data;
    FirebaseFirestore firebaseFirestore;

    public WinnarAdapter(List<Lottery_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public WinnarAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.winnar,parent,false);
        return new WinnarAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WinnarAdapter.myview holder, final int position) {
        holder.customer_name.setText("Winner : "+data.get(position).getUsername()+"\nLottery Name : "+data.get(position).getLottery_name()+"\n"
               +" Lottery Price :"+data.get(position).getLottery_price()+"\nLottery Winner Prize : "+data.get(position).getLotteryPrize());

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

