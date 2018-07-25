package com.nisa.penjulanan.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.List;

public class FinishOrderlisview extends RecyclerView.Adapter<FinishOrderlisview.ViewHolder> {
    private List<Model> list;

    public FinishOrderlisview(List<Model> list) {
        this.list=list;
    }

    @NonNull
    @Override
    public FinishOrderlisview.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_admin, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FinishOrderlisview.ViewHolder holder, int position) {
        final Model model = list.get(position);
        holder.orderId.setText(model.getOrderID());

        System.out.println(model.getOrderID());
        holder.namauser.setText(model.getNamauser());
        holder.alamatuser.setText(model.getAlamatuser());
        holder.hanyaalamat.setText(model.getHanyaalamat());
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