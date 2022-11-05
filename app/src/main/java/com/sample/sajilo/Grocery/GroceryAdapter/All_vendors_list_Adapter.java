package com.sample.sajilo.Grocery.GroceryAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sample.sajilo.Common.DatabaseHelper;
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
    public void setFilterList(List<VendorsStoreData> filteredList){
        this.list=filteredList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public All_vendors_list_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_vendors_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull All_vendors_list_Adapter.MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
