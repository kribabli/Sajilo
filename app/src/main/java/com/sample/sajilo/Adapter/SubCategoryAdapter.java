package com.sample.sajilo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sample.sajilo.BottomFragments.Response.SubCategoryResponse;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.R;
import com.sample.sajilo.ServicesRelatedFragment.CategoryAdapter;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    List<SubCategoryResponse> list;
    Context context;

    public SubCategoryAdapter(List<SubCategoryResponse> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load("http://adminapp.tech/Sajilo/" + list.get(position).getCat_img())
                .into(holder.cat_Image_View);

        holder.cat_Name.setText("" + list.get(position).getName());
        holder.subCategory_name.setText("" + list.get(position).getCategory());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_Image_View,favrouite;
        TextView cat_Name;
        TextView subCategory_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_Image_View=itemView.findViewById(R.id.cat_Image_View);
            favrouite=itemView.findViewById(R.id.favrouite);
            cat_Name=itemView.findViewById(R.id.cat_Name);
            subCategory_name=itemView.findViewById(R.id.subCategory_name);
        }
    }
}
