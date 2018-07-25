package com.nisa.penjulanan.Boundary.Users;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.Adapter.ListandSednValueAdapter;
import com.nisa.penjulanan.MenuActivity;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Homeuser extends Fragment {
    private ListandSednValueAdapter listandSednValueAdapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    List<Model> list = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;

    public Homeuser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homeuser, container, false);
        list.clear();
        recyclerView = view.findViewById(R.id.recycleruser);
        firebaseDatabase = FirebaseDatabase.getInstance();
        listandSednValueAdapter = new ListandSednValueAdapter(list);
        myRef = firebaseDatabase.getReference("Items");
        recyclerView.setHasFixedSize(true);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(layoutManager);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Model model = new Model();
                    String nama = dataSnapshot1.child("namaitem").getValue().toString();
                    String harga = dataSnapshot1.child("harga").getValue().toString();
                    String jenis = dataSnapshot1.child("jenis").getValue().toString();
                    String stock = dataSnapshot1.child("stockitem").getValue().toString();
                    String nobpom = dataSnapshot1.child("nobpomitem").getValue().toString();
                    String uraian = dataSnapshot1.child("uraianitem").getValue().toString();

                    model.setNamaitem(nama);
                    model.setHarga(harga);
                    model.setJenisitem(jenis);
                    model.setStockitem(stock);
                    model.setNobpomitem(nobpom);
                    model.setUriaianitem(uraian);
                    list.add(model);
                }
                recyclerView.setAdapter(listandSednValueAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}
