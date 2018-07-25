package com.nisa.penjulanan.Adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nisa.penjulanan.Boundary.Apoteker.Detail_listorder;
import com.nisa.penjulanan.Boundary.Users.Detail_Item;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.List;

public class AdminListviewAdapter extends RecyclerView.Adapter<AdminListviewAdapter.ViewHolder> {
    private List<Model> list;

    public AdminListviewAdapter(List<Model> list) {
        this.list =list;
    }

    @NonNull
    @Override
    public AdminListviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_admin, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdminListviewAdapter.ViewHolder holder, int position) {
        final Model model = list.get(position);
        holder.orderId.setText(model.getOrderID());

        System.out.println("ini di recycler view");
        System.out.println(model.getOrderID());
        holder.namauser.setText(model.getNamauser());
        holder.alamatuser.setText(model.getAlamatuser());
        holder.hanyaalamat.setText(model.getHanyaalamat());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                Detail_listorder detail_listorder =  new Detail_listorder();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.container, detail_listorder).addToBackStack(null).commit();

                Bundle bundle = new Bundle();
                bundle.putString("userUID", model.getUid());
                bundle.putString("OrderUid", model.getOrderUID());
                bundle.putString("OrderID", model.getOrderID());
                detail_listorder.setArguments(bundle);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView namauser, alamatuser, orderId, hanyaalamat;
        public ViewHolder(View itemView) {
            super(itemView);

            orderId = itemView.findViewById(R.id.iduser_admin);
            namauser = itemView.findViewById(R.id.namauser_admin);
            alamatuser = itemView.findViewById(R.id.alamatuser_admin);
            hanyaalamat = itemView.findViewById(R.id.hanyaalamat);
        }
    }
}
