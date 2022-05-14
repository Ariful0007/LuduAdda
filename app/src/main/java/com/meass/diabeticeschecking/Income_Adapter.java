package com.meass.diabeticeschecking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class Income_Adapter extends RecyclerView.Adapter<Income_Adapter.myview> {
    public List<Income> data;
    FirebaseFirestore firebaseFirestore;

    public Income_Adapter(List<Income> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public Income_Adapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.payment,parent,false);
        return new Income_Adapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Income_Adapter.myview holder, final int position) {
        holder.customer_name.setText("Username : "+data.get(position).getUsername());
        holder.customer_number.setText("Type  : "+data.get(position).getType());
        holder.customer_area.setText(data.get(position).getDate());
        holder.logout.setText(data.get(position).getPayment());



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class myview extends RecyclerView.ViewHolder{
        TextView customer_name,customer_number,customer_area,logout;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
        }
    }
}

