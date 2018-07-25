package com.nisa.penjulanan.Boundary.Users;


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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.Adapter.ListandSednValueAdapter;
import com.nisa.penjulanan.Adapter.ListandViewAdapter;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.util.ArrayList;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Checkout_item extends Fragment {
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference myRef;
    List<Model> list = new ArrayList<>();
    RecyclerView recyclerView;
    Context context;
    ListandViewAdapter listandViewAdapter;
    String uid;
    String uidOrderan;

    public Checkout_item() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =  inflater.inflate(R.layout.fragment_checkout_item, container, false);
        recyclerView = view.findViewById(R.id.recyclercheckout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        final TextView totalcheckout = view.findViewById(R.id.totalcheckout);
        final TextView IDOrdertv = view.findViewById(R.id.IDOrder);
        firebaseDatabase = FirebaseDatabase.getInstance();
        listandViewAdapter = new ListandViewAdapter(list);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        uid = currentuser.getUid();

        myRef = firebaseDatabase.getReference("Orderan").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long total_belanja = Long.valueOf(0);
                for (DataSnapshot dataSnapshotorderan: dataSnapshot.getChildren()){
                    uidOrderan =dataSnapshotorderan.getKey();
                    String check = dataSnapshotorderan.child("status_order").getValue().toString();
                    String orderIdku = dataSnapshotorderan.child("orderID").getValue().toString();
                    if (check.equals("checkout")){
                        System.out.println("checkout true");
                        for (DataSnapshot dataSnapshotitem:dataSnapshotorderan.getChildren()) {
                            String uidItem = dataSnapshotitem.getKey();
                            if(dataSnapshotitem.child("namaitem").getValue()!=null){
                                Model model = new Model();

                                String nama = (String) dataSnapshotitem.child("namaitem").getValue();
                                String harga = (String) dataSnapshotitem.child("harga").getValue();
                                String jumlah = (String) dataSnapshotitem.child("jumlahitem").getValue();
                                Long total = (Long) dataSnapshotitem.child("total").getValue();
                                total_belanja = total+total_belanja;

                                model.setUid(uid);
                                model.setOrderUID(uidOrderan);
                                model.setOrderItemUID(uidItem);
                                model.setOrderID(orderIdku);
                                model.setNamaitem(nama);
                                model.setHarga(harga);
                                model.setJumlahitem(jumlah);
                                model.setTotal(String.valueOf(total));
                                list.add(model);
                            }
                        }
                    }
                    IDOrdertv.setText("ID "+ orderIdku);
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
                DatabaseReference reference = firebaseDatabase.getReference().child("Orderan").child(uid).child(uidOrderan);
                reference.child("status_order").setValue("On Progress");
                redirectScreen();
                Toast.makeText(getActivity(), "Pembelian telah berhasil", Toast.LENGTH_LONG).show();
            }
        });
        return view;
        }


    private void redirectScreen() {
        Log.d(TAG, "Redirecting to login screen.");

        Homeuser homeuser = new Homeuser();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, homeuser)
                .addToBackStack(null).commit();
    }
}
