package com.example.customer.ujconnect;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Attendence> attendenceArrayList;


    public AttendenceAdapter(Context context, ArrayList<Attendence> attendenceArrayList) {
        this.context = context;
        this.attendenceArrayList = attendenceArrayList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.student_bar_view,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.s_name.setText("Omar Abbas Alem");

    }

    @Override
    public int getItemCount() {
        return attendenceArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView s_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            s_name = itemView.findViewById(R.id.student_name);
        }
    }
}
