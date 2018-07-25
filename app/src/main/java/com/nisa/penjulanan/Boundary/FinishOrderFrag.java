package com.nisa.penjulanan.Boundary;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.Adapter.AdminListviewAdapter;
import com.nisa.penjulanan.Adapter.FinishOrderlisview;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FinishOrderFrag extends Fragment {
    FinishOrderlisview adapterrecycler;
    List<Model> list = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    Context context;

    public FinishOrderFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_finish_order, container, false);
        recyclerView = view.findViewById(R.id.recyler_finishorder);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        adapterrecycler =  new FinishOrderlisview(list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        myRef = firebaseDatabase.getReference();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        final String userCurrent = currentuser.getUid();

        final String admin = getArguments().getString("admin");

        myRef.child("Orderan").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    final String userUid = dataSnapshot1.getKey();

                    myRef.child("Users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final String nama = dataSnapshot.child("username").getValue().toString();
                            String statususer = dataSnapshot.child("status").getValue().toString();
                            System.out.println(statususer);
                            if (userCurrent.equals(userUid)){
                                myRef.child("Orderan").child(userCurrent).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshotorder1 : dataSnapshot.getChildren()){
                                            String status = dataSnapshotorder1.child("status_order").getValue().toString();
                                            String orderIDk = dataSnapshotorder1.child("orderID").getValue().toString();
                                                Model model = new Model();
                                                model.setOrderID(orderIDk);
                                                model.setNamauser(nama);
                                                model.setAlamatuser(status);
                                                model.setHanyaalamat("Status Order : ");
                                                list.add(model);

                                        }
                                        recyclerView.setAdapter(adapterrecycler);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            } else if(admin.equals("0")) {
                                myRef.child("Orderan").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshotorder1 : dataSnapshot.getChildren()){
                                            String status = dataSnapshotorder1.child("status_order").getValue().toString();
                                            String orderIDk = dataSnapshotorder1.child("orderID").getValue().toString();
                                                Model model = new Model();
                                                model.setOrderID(orderIDk);
                                                model.setNamauser(nama);
                                                model.setAlamatuser(status);
                                                model.setHanyaalamat("Status Order : ");
                                                list.add(model);

                                        }
                                        recyclerView.setAdapter(adapterrecycler);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

}

