package com.example.customer.ujconnect;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.MyViewHolder> {

    private Context context;
    private List<ViewCardObject> viewCardObjectList;


    public NewsCardAdapter(Context context, ArrayList<ViewCardObject> viewCardObjectList) {
        this.context = context;
        this.viewCardObjectList = viewCardObjectList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(context);
        view = mInflater.inflate(R.layout.cardview,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.textView.setText(viewCardObjectList.get(i).getName());
        myViewHolder.title.setText(viewCardObjectList.get(i).getTitle());
        myViewHolder.comment.setText(viewCardObjectList.get(i).getComments());
        myViewHolder.time.setText(viewCardObjectList.get(i).getTime());
        myViewHolder.imageView.setImageResource(R.drawable.fcit_logo);


    }

    @Override
    public int getItemCount() {
        return viewCardObjectList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        TextView title;
        TextView time;
        TextView comment;
        ImageView imageView;


        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.test_text);
            title = itemView.findViewById(R.id.nameD);
            time  = itemView.findViewById(R.id.posted);
            comment = itemView.findViewById(R.id.comments);
            imageView = itemView.findViewById(R.id.iconD);
        }
    }
}
