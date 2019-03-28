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

public class UserActivityAdapter extends RecyclerView.Adapter<UserActivityAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<UserActivityObject> activityObjectArrayList;

    public UserActivityAdapter(Context context, ArrayList<UserActivityObject> activityObjectArrayList) {
        this.context = context;
        this.activityObjectArrayList = activityObjectArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.user_activity_cardvview,viewGroup,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        String image = activityObjectArrayList.get(i).getImage();
        String title = activityObjectArrayList.get(i).getTitle();
        String date  = activityObjectArrayList.get(i).getDate();

        if(image!=null || image.equals("")){
            Glide.with(context).load(image).centerCrop().into(myViewHolder.activity_image);
        }
        if(date!=null || date.equals("")){
            myViewHolder.activity_date.setText(date);
        }
        if(title!=null || title.equals("")){
            myViewHolder.activity_name.setText(title);
        }

        myViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context,workshop_expanded.class).addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT));
            }
        });

    }

    @Override
    public int getItemCount() {
        return activityObjectArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout constraintLayout;
        ImageView activity_image;
        TextView activity_name;
        TextView activity_date;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
            activity_image = itemView.findViewById(R.id.activity_image);
            activity_name = itemView.findViewById(R.id.activity_name);
            activity_date = itemView.findViewById(R.id.acitivity_date);

        }
    }
}
