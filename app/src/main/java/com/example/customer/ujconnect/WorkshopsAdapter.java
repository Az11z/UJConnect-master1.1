package com.example.customer.ujconnect;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WorkshopsAdapter extends RecyclerView.Adapter<WorkshopsAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<WorkShopDetails> workShopDetails;

    public WorkshopsAdapter(Context context, ArrayList<WorkShopDetails> workShopDetails) {
        this.context = context;
        this.workShopDetails = workShopDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.workshop_cardview,viewGroup,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        Glide.with(context).load(workShopDetails.get(i).getImage()).into(myViewHolder.workshopImage);
        Glide.with(context).load(workShopDetails.get(i).getImage()).into(myViewHolder.departmentImage);
        myViewHolder.title.setText(workShopDetails.get(i).getTitle());
        myViewHolder.date.setText(workShopDetails.get(i).getDate());
        myViewHolder.postingDate.setText(workShopDetails.get(i).getDate());
        myViewHolder.dpartment_name.setText(workShopDetails.get(i).getDepartment());

        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,workshop_expanded.class);
                intent.putExtra("department",workShopDetails.get(i));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return workShopDetails.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView workshopImage;
        ImageView departmentImage;
        TextView title;
        TextView postingDate;
        TextView date;
        TextView dpartment_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            workshopImage = itemView.findViewById(R.id.workshopImage);
            departmentImage = itemView.findViewById(R.id.departmentImage);
            title = itemView.findViewById(R.id.title);
            postingDate = itemView.findViewById(R.id.postingDate);
            date = itemView.findViewById(R.id.date);
            dpartment_name = itemView.findViewById(R.id.departmentName);
        }
    }
}
