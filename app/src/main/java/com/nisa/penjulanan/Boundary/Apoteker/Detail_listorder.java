package com.nisa.penjulanan.Boundary.Apoteker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.Adapter.ListandViewAdapter;
import com.nisa.penjulanan.Boundary.Users.Homeuser;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_listorder extends Fragment {
    List<Model> list = new ArrayList<>();
    ListandViewAdapter listandViewAdapter;
    RecyclerView recyclerView;
    Context context;
    DatabaseReference myRef;
    FirebaseDatabase firebaseDatabase;

    public Detail_listorder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_listorder, container, false);
        recyclerView = view.findViewById(R.id.recyler_adminlistorder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final TextView totalcheckout = view.findViewById(R.id.totalcheckout);
        listandViewAdapter = new ListandViewAdapter(list);

        firebaseDatabase = FirebaseDatabase.getInstance();

        Bundle bundle = getArguments();
        final String useruid = bundle.getString("userUID");
       // System.out.println(useruid);
        final String orderuid = bundle.getString("OrderUid");
        TextView total = view.findViewById(R.id.total);
        total.setText(bundle.getString("OrderID"));

        myRef = firebaseDatabase.getReference("Orderan").child(useruid).child(orderuid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long total_belanja = Long.valueOf(0);
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                    if (dataSnapshot1.child("namaitem").getValue() != null) {
                        Model model = new Model();

                        String nama = (String) dataSnapshot1.child("namaitem").getValue();
                        String harga = (String) dataSnapshot1.child("harga").getValue();
                        String jumlah = (String) dataSnapshot1.child("jumlahitem").getValue();
                        Long total = (Long) dataSnapshot1.child("total").getValue();

                        total_belanja = total + total_belanja;

                        model.setNamaitem(nama);
                        model.setHarga(harga);
                        model.setJumlahitem(jumlah);
                        model.setTotal(String.valueOf(total));
                        list.add(model);
                    }
                }
                totalcheckout.setText(String.valueOf(total_belanja));
                recyclerView.setAdapter(listandViewAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        Button button_bayar = view.findViewById(R.id.bayar);
        button_bayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //DatabaseReference reference = firebaseDatabase.getReference().child("Orderan").child(useruid).child(orderuid);
                myRef.child("status_order").setValue("Selesai");

                redirectScreen();
                Toast.makeText(getActivity(), "Pembelian telah berhasil", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }

    private void redirectScreen() {
        Log.d(TAG, "Redirecting to login screen.");

        ListOrder detail_listorder = new ListOrder();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, detail_listorder)
                .addToBackStack(null).commit();
    }

}
