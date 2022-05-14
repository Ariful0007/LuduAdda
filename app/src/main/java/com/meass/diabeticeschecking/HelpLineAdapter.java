package com.meass.diabeticeschecking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class HelpLineAdapter  extends RecyclerView.Adapter<HelpLineAdapter.myview> {
    public List<Help_Model> data;
    FirebaseFirestore firebaseFirestore;

    public HelpLineAdapter(List<Help_Model> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public HelpLineAdapter.myview onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.calling,parent,false);
        return new HelpLineAdapter.myview(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HelpLineAdapter.myview holder, final int position) {
        holder.customer_name.setText(data.get(position).getName());
        holder.customer_number.setText(data.get(position).getPhone());
        holder.customer_area.setText("Any Area");
        holder.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                builder.setTitle("Calling")
                        .setMessage("Are you want to call ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String s="tel:"+data.get(position).getPhone();
                        Intent intent33=new Intent(Intent.ACTION_DIAL);
                        intent33.setData(Uri.parse(s));
                        v.getContext().startActivity(intent33);

                    }
                }).create().show();
            }
        });

        holder.reay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setTitle("Calling")
                        .setMessage("Are you want to call ?")
                        .setPositiveButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        String s="tel:"+data.get(position).getPhone();
                        Intent intent33=new Intent(Intent.ACTION_DIAL);
                        intent33.setData(Uri.parse(s));
                        view.getContext().startActivity(intent33);

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
        CardView reay;
        public myview(@NonNull View itemView) {
            super(itemView);
            customer_name=itemView.findViewById(R.id.customer_name);
            customer_number=itemView.findViewById(R.id.customer_number);
            customer_area=itemView.findViewById(R.id.customer_area);
            logout=itemView.findViewById(R.id.logout);
            reay=itemView.findViewById(R.id.reay);
        }
    }
}
