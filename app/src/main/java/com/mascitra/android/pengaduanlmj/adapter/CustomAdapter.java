package com.mascitra.android.pengaduanlmj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mascitra.android.pengaduanlmj.R;

import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private Context context;
    private List<DataPengaduan> my_data;

    public CustomAdapter(Context context, List<DataPengaduan> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

            holder.nama.setText(my_data.get(position).getNama());
        holder.nik.setText(my_data.get(position).getNik());
        holder.tanggal.setText(my_data.get(position).getTanggal());
        holder.description.setText(my_data.get(position).getKomentar());
        Glide.with(context).load(my_data.get(position).getImageLink()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView nama,nik,tanggal,description;
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            nik = itemView.findViewById(R.id.tvNIK);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
