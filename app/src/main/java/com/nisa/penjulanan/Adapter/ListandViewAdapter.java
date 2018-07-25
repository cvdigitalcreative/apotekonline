package com.nisa.penjulanan.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nisa.penjulanan.Boundary.Users.Checkout_item;
import com.nisa.penjulanan.Boundary.Users.Detail_Item;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.List;

public class ListandViewAdapter extends RecyclerView.Adapter<ListandViewAdapter.ViewHolder> {
    private List<Model> list;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;
    View view;

    public ListandViewAdapter(List<Model> list) {
        this.list =list;
    }

    @NonNull
    @Override
    public ListandViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_checkout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ListandViewAdapter.ViewHolder holder, final int position) {
        final  Model model = list.get(position);
        final int[] mjumlah = {Integer.valueOf(model.getJumlahitem())};
        holder.uid.setText(model.getUid());
        holder.namaitem.setText(model.getNamaitem());
        holder.hargaitem.setText(model.getHarga());
        holder.total.setText(model.getTotal());
        holder.jumlah.setText(model.getJumlahitem());
        holder.totalsum.setText(model.getKey());

        Button btn_minus =  view.findViewById(R.id.btn_kurang);
        Button btn_plus =  view.findViewById(R.id.btn_tambah);

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mjumlah[0] = mjumlah[0] - 1;
                if (mjumlah[0] <= 0){
                    mjumlah[0] = 0;
                }
                int total = mjumlah[0] *Integer.parseInt(model.getHarga());
                holder.total.setText(String.valueOf(total));
                getMyRef(mjumlah[0], total, model.getUid(), model.getOrderUID(), model.getOrderItemUID());
                holder.jumlah.setText(String.valueOf(mjumlah[0]));
                redirectscreen();
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mjumlah[0] = mjumlah[0] + 1;
                int total = mjumlah[0] *Integer.parseInt(model.getHarga());
                holder.total.setText(String.valueOf(total));
                getMyRef(mjumlah[0], total, model.getUid(), model.getOrderUID(), model.getOrderItemUID());
                holder.jumlah.setText(String.valueOf(mjumlah[0]));
                redirectscreen();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView namaitem, hargaitem, jumlah, uid, total, totalsum;
        public ViewHolder(View itemView) {
            super(itemView);

            namaitem = itemView.findViewById(R.id.namaitem);
            hargaitem = itemView.findViewById(R.id.hargaitem);
            jumlah = itemView.findViewById(R.id.jumlahitemcheckout);
            total = itemView.findViewById(R.id.totalitem);
            totalsum = itemView.findViewById(R.id.totalsum);
            uid = itemView.findViewById(R.id.Uid);

        }
    }

    public DatabaseReference getMyRef(int jumle, int total, String uid, String orderUID, String orderItemUID) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference().child("Orderan").child(uid).child(orderUID).child(orderItemUID);
        myRef.child("total").setValue(total);
        myRef.child("jumlahitem").setValue(String.valueOf(jumle));
        return myRef;
    }

    public void redirectscreen(){
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Checkout_item checkout_item =  new Checkout_item();
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, checkout_item)
                .addToBackStack(null)
                .commit();

    }
}

