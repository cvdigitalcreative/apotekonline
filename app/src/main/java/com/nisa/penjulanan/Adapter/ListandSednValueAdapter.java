package com.nisa.penjulanan.Adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.nisa.penjulanan.Boundary.Users.Detail_Item;
import com.nisa.penjulanan.Boundary.Users.Homeuser;
import com.nisa.penjulanan.MenuActivity;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.List;

public class ListandSednValueAdapter extends RecyclerView.Adapter<ListandSednValueAdapter.ViewHolder>{
    private List<Model> list;
    DatabaseReference myRef;

    public ListandSednValueAdapter(List<Model> items) {
       // inflater = LayoutInflater.from(context);
        this.list = items;
    }

//    public ListandSednValueAdapter(List<Model> modellist, Context context){
//
//        this.list = modellist;
//        inflater=LayoutInflater.from(context);
//        this.context = context;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final  Model model = list.get(position);
        final View view = null;
        holder.uid.setText(model.getUid());
        holder.namaitem.setText(model.getNamaitem());
        holder.hargaitem.setText(model.getHarga());
        holder.jenisitem.setText(model.getJenisitem());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Detail_Item detailItem =  new Detail_Item();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, detailItem).addToBackStack(null).commit();

                Bundle bundle = new Bundle();
                String nama = model.getNamaitem();
                bundle.putString("namaitem", nama);
                String harga = model.getHarga();
                bundle.putString("harga", harga);
                String jenis = model.getJenisitem();
                bundle.putString("jenis", jenis);
                String stock = model.getStockitem();
                bundle.putString("stock", stock);
                String nobpom = model.getNobpomitem();
                bundle.putString("nobpom", nobpom);
                String uraian = model.getUriaianitem();
                bundle.putString("uraian", uraian);
                String namauser = String.valueOf(model.getNamauser());
                System.out.println("coy" +namauser);
                bundle.putString("namauser", namauser);
                String alamatuser = String.valueOf(model.getNamauser());
                bundle.putString("alamatuser", alamatuser);
                detailItem.setArguments(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namaitem, hargaitem, jenisitem, uid, namauser, alamatuser;
        public ViewHolder(View itemView) {
            super(itemView);

            namaitem = itemView.findViewById(R.id.namaitem);
            hargaitem = itemView.findViewById(R.id.hargaitem);
            jenisitem = itemView.findViewById(R.id.jenisitem);
//            namauser = itemView.findViewById(R.id.namauser);
//            alamatuser = itemView.findViewById(R.id.alamatuser);
            uid = itemView.findViewById(R.id.Uid);
        }
    }
}
