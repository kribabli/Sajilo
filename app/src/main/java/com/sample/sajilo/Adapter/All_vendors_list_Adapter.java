package com.sample.sajilo.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sample.sajilo.Common.DatabaseHelper;
import com.sample.sajilo.Model.CategoryDataResponse;
import com.sample.sajilo.Model.VendorsStoreData;
import com.sample.sajilo.R;

import java.util.List;

public class All_vendors_list_Adapter extends RecyclerView.Adapter<All_vendors_list_Adapter.MyViewHolder> {
    List<VendorsStoreData> list;
    Context context;
    DatabaseHelper databaseHelper;

    public All_vendors_list_Adapter(List<VendorsStoreData> list, Context context) {
        this.list = list;
        this.context = context;
        databaseHelper=new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public All_vendors_list_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_vendors_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull All_vendors_list_Adapter.MyViewHolder holder, int position) {

        Glide.with(context)
                .load("http://adminapp.tech/Sajilo/" + list.get(position).getStore_image())
                .into(holder.inside_imageview);
        holder.title.setText(""+list.get(position).getTitle());
        holder.address_shop.setText(""+list.get(position).getAddress());
        holder.contactNumber.setText(""+list.get(position).getMobile());


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
                fav.put(DatabaseHelper.FAVOURITE_NAME, list.get(position).getTitle());
                fav.put(DatabaseHelper.FAVOURITE_IMAGE, list.get(position).getStore_image());
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
        ImageView inside_imageview,favrouite;
        TextView title,address_shop,contactNumber;
        Button explore;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            inside_imageview=itemView.findViewById(R.id.inside_imageview);
            favrouite=itemView.findViewById(R.id.favrouite);
            title=itemView.findViewById(R.id.title);
            address_shop=itemView.findViewById(R.id.address_shop);
            contactNumber=itemView.findViewById(R.id.contactNumber);
            explore=itemView.findViewById(R.id.explore);
        }
    }
}
