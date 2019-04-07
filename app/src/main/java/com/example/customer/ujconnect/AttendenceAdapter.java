package com.example.customer.ujconnect;

import android.content.Context;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<User> userArrayList;
    private WorkShopDetails workShopDetails;


    public AttendenceAdapter(Context context, ArrayList<User> userArrayList,WorkShopDetails workShopDetails) {
        this.context = context;
        this.userArrayList = userArrayList;
        this.workShopDetails = workShopDetails;
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.s_name.setText(userArrayList.get(i).getName());
        myViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).child("Checked").child(userArrayList.get(i).getFirebase_id()).setValue(userArrayList.get(i));
                    Certificate certificate = new Certificate();
                    certificate.setDate(workShopDetails.getDate());
                    certificate.setTitle(workShopDetails.getTitle());
                    certificate.setDean(workShopDetails.getDean());
                    certificate.setDep(workShopDetails.getDepartment());
                    certificate.setId(workShopDetails.getFirebase_id());
                    certificate.setHours(workShopDetails.getDuration());
                    certificate.setName(userArrayList.get(i).getName());
                    certificate.setInstructor(workShopDetails.getInstructor());
                    certificate.setLocation(workShopDetails.getLocation());
                    FirebaseDatabase.getInstance().getReference("Certificates").child(userArrayList.get(i).getFirebase_id()).setValue(certificate);
                }
                else{
                    FirebaseDatabase.getInstance().getReference("Workshops").child("department").child(workShopDetails.getFirebase_id()).child("Checked").child(userArrayList.get(i).getFirebase_id()).removeValue();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView s_name;
        CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.check_box_attend);
            s_name = itemView.findViewById(R.id.student_name);
        }
    }
}
