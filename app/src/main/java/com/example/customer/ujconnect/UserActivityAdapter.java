package com.example.customer.ujconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    }

    @Override
    public int getItemCount() {
        return activityObjectArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
