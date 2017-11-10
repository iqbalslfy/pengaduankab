package com.mascitra.android.pengaduanlmj.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mascitra.android.pengaduanlmj.Data.DataPengaduan;
import com.mascitra.android.pengaduanlmj.R;
import com.mascitra.android.pengaduanlmj.activity.DetailPengaduanActivity;

import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */

    public class DataDashboardAdapter extends RecyclerView.Adapter<DataDashboardAdapter.ViewHolder> {

    private Context context;
    private List<DataPengaduan> my_data;

    public DataDashboardAdapter(Context context, List<DataPengaduan> my_data) {
        this.context = context;
        this.my_data = my_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card,parent,false);

        return new ViewHolder(itemView);
    }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {

            holder.nama.setText(my_data.get(position).getNama());
            holder.nik.setText(my_data.get(position).getNik());
            holder.tanggal.setText(my_data.get(position).getTanggal());
            holder.description.setText(my_data.get(position).getKomentar());
            Glide.with(context).load(my_data.get(position).getImageLink()).into(holder.imageView);

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        Intent intent = new Intent(context.getApplicationContext(), DetailPengaduanActivity.class);
                        Bundle b = new Bundle();
                        b.putString("ktp", holder.nik.getText().toString());
                        intent.putExtras(b);
                        context.startActivity(intent);
                }
            });

        }

    @Override
    public int getItemCount() {
        return my_data.size();
    }

    public  class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView nama,nik,tanggal,description;
        public ImageView imageView;
        public RelativeLayout layout;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            nik = itemView.findViewById(R.id.tvNIK);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
            layout = itemView.findViewById(R.id.relatifLayout);
        }
    }
}
