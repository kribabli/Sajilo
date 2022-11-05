package com.sample.sajilo.Grocery.GroceryAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.sample.sajilo.Grocery.GroceryAllActivity.AllVendors;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.R;

import java.util.List;

public class AllCategoryAdapter extends RecyclerView.Adapter<AllCategoryAdapter.MyViewHolder>  {

    List<CategoryDataResponse> list;
    Context context;

    public AllCategoryAdapter(List<CategoryDataResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setFilterList(List<CategoryDataResponse> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public AllCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_category_list_adapter, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCategoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text.setText("" + list.get(position).getName());
        Glide.with(context)
                .load("http://adminapp.tech/Sajilo/" + list.get(position).getImage())
                .into(holder.image);


        Log.d("Amit","Value 111 "+list.get(position).getPinCode());


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, AllVendors.class);
                intent.putExtra("category",list.get(position).getId());
                intent.putExtra("pincode",list.get(position).getPinCode());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView image;
        TextView text;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
