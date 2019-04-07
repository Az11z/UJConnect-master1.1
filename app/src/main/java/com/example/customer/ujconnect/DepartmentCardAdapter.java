package com.example.customer.ujconnect;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class DepartmentCardAdapter extends RecyclerView.Adapter<DepartmentCardAdapter.MyViewHolder> {
    private Context context;
    private List<DepartmentCardView> departmentCardViews;

    public DepartmentCardAdapter(Context context, List<DepartmentCardView> departmentCardViews) {
        this.context = context;
        this.departmentCardViews = departmentCardViews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.department_list_card,viewGroup,false);
        return new DepartmentCardAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        Glide.with(context).load(departmentCardViews.get(i).getImage()).into(myViewHolder.DepIcon);
        myViewHolder.DepTitle.setText(departmentCardViews.get(i).getDepartment_name());


        myViewHolder.Dep_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,DepartmentPageActivity.class);
                intent.putExtra("dep",departmentCardViews.get(i));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return departmentCardViews.size();
    }




    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView DepIcon;
        TextView DepTitle;
        CardView Dep_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DepIcon = itemView.findViewById(R.id.DepIconID);
            DepTitle = itemView.findViewById(R.id.DepTitleID);
            Dep_card = itemView.findViewById(R.id.dep_card);

        }
    }





}
