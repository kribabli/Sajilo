package com.sample.sajilo.Adapter;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sample.sajilo.AllSubCategory;
import com.sample.sajilo.BottomFragments.Response.SubCategoryResponse;
import com.sample.sajilo.Common.DatabaseHelper;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.R;
import com.sample.sajilo.ServicesRelatedFragment.CategoryAdapter;

import java.util.ArrayList;
import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    List<SubCategoryResponse> list;
    Context context;
    DatabaseHelper databaseHelper;

    public SubCategoryAdapter(List<SubCategoryResponse> list, Context context) {
        this.list = list;
        this.context = context;
        databaseHelper=new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public SubCategoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_category_item, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context)
                .load("http://adminapp.tech/Sajilo/" + list.get(position).getCat_img())
                .into(holder.cat_Image_View);

        holder.cat_Name.setText("" + list.get(position).getName());
        holder.subCategory_name.setText("" + list.get(position).getCategory());

        if(databaseHelper.getFavouriteById(list.get(position).getId(),DatabaseHelper.TABLE_FAVOURITE)){
            holder.favrouite.setImageResource(R.drawable.img_over);
        }

        holder.favrouite.setOnClickListener(v -> {
            ContentValues fav = new ContentValues();
            if(databaseHelper.getFavouriteById(list.get(position).getId(), DatabaseHelper.TABLE_FAVOURITE)){
                databaseHelper.removeFavouriteById(list.get(position).getId(), DatabaseHelper.TABLE_FAVOURITE);
                Toast.makeText(context, "Favourite has been removed", Toast.LENGTH_SHORT).show();
                holder.favrouite.setImageResource(R.drawable.favrouite);
            }
            else{
                fav.put(DatabaseHelper.FAVOURITE_ID, list.get(position).getId());
                fav.put(DatabaseHelper.FAVOURITE_NAME, list.get(position).getName());
                fav.put(DatabaseHelper.FAVOURITE_IMAGE, list.get(position).getCat_img());
                databaseHelper.addFavourite(DatabaseHelper.TABLE_FAVOURITE, fav, null);
                holder.favrouite.setImageResource(R.drawable.img_over);
                Toast.makeText(context, "Favourite has been added", Toast.LENGTH_SHORT).show();
            }

        });

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
