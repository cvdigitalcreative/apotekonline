package com.nisa.penjulanan.Boundary.Apoteker;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.Adapter.AdminListviewAdapter;
import com.nisa.penjulanan.Adapter.ListandViewAdapter;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListOrder extends Fragment {
    AdminListviewAdapter adapterrecycler;
    List<Model> list = new ArrayList<>();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;
    FirebaseAuth firebaseAuth;
    RecyclerView recyclerView;
    Context context;

    public ListOrder() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_list_order, container, false);
            recyclerView = view.findViewById(R.id.orderlist_recycler);
            firebaseDatabase = FirebaseDatabase.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            adapterrecycler =  new AdminListviewAdapter(list);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            myRef = firebaseDatabase.getReference();

             myRef.child("Orderan").addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(DataSnapshot dataSnapshot) {
                   for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                       final String userUid = dataSnapshot1.getKey();

                       myRef.child("Users").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               final String nama = dataSnapshot.child("username").getValue().toString();
                               final String alamat = dataSnapshot.child("alamat").getValue().toString();

                                myRef.child("Orderan").child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshotorder1 : dataSnapshot.getChildren()){
                                            String orderUID = dataSnapshotorder1.getKey();
                                            String status = dataSnapshotorder1.child("status_order").getValue().toString();
                                            String orderIDk = dataSnapshotorder1.child("orderID").getValue().toString();
                                            if (status.equals("On Progress")){
                                                System.out.println(orderIDk);
                                                Model model = new Model();
                                                model.setOrderID(orderIDk);
                                                model.setUid(userUid);
                                                model.setOrderUID(orderUID);
                                                model.setNamauser(nama);
                                                model.setAlamatuser(alamat);
                                                model.setHanyaalamat("Alamat : ");
                                                list.add(model);

                                            }

                                        }
                                        recyclerView.setAdapter(adapterrecycler);
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

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
