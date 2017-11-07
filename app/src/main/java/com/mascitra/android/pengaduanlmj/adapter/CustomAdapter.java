package com.mascitra.android.pengaduanlmj.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mascitra.android.pengaduanlmj.Click.ItemClickListener;
import com.mascitra.android.pengaduanlmj.R;

import java.util.List;

/**
 * Created by filipp on 9/16/2016.
 */

class RecyclerViewHolde extends RecyclerView.ViewHolder implements View.OnClickListener {

    public ImageView imgPengaduan;
    public TextView tvnama,tvnik,tvjam,tvkomentar;
    private ItemClickListener itemClickListener;

    public RecyclerViewHolde(View itemView) {
        super(itemView);
        imgPengaduan = itemView.findViewById(R.id.imageLists);
        tvnama = itemView.findViewById(R.id.name_ktp);
        tvnik = itemView.findViewById(R.id.ktp_id);
        tvjam = itemView.findViewById(R.id.jam);
        tvkomentar = itemView.findViewById(R.id.komentar);

        itemView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}
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

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    Toast.makeText(context, "Item Klik : ", Toast.LENGTH_SHORT).show();
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
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            nama = itemView.findViewById(R.id.tvNama);
            nik = itemView.findViewById(R.id.tvNIK);
            tanggal = itemView.findViewById(R.id.tvTanggal);
            description = itemView.findViewById(R.id.description);
            imageView = itemView.findViewById(R.id.image);
        }


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }
    }
}
