package com.nisa.penjulanan.Boundary.Users;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.Boundary.Apoteker.AddItem;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.security.SecureRandom;
import java.util.List;

import static android.support.constraint.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class Detail_Item extends Fragment {
    FirebaseAuth firebaseAuth;
    List<Model> list;
    DatabaseReference myRef;
    String jumlui;

    public Detail_Item() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_detail_item, container, false);
        final TextView namaitem = view.findViewById(R.id.popupnamaitem);
        final TextView harga = view.findViewById(R.id.popupharga);
        TextView jenis = view.findViewById(R.id.popupjenis);
        //final TextView stock = view.findViewById(R.id.popupstok);
        final TextView nobpom = view.findViewById(R.id.popupbpom);
        final EditText jumlah = view.findViewById(R.id.popupjumlah);
        final TextView uraian = view.findViewById(R.id.popupuraian);
        final Button button_beli =  view.findViewById(R.id.beli);

        button_beli.setEnabled(false);
        jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                boolean ready = jumlah.getText().toString().length()>=0;
                button_beli.setEnabled(ready);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        final Bundle bundle =  getArguments();
        namaitem.setText(bundle.getString("namaitem"));
        final String hargas = bundle.getString("harga");
        harga.setText("Rp. " +hargas +"/Item");
        jenis.setText(bundle.getString("jenis"));
        //stock.setText(bundle.getString("stock"));
        jumlui = jumlah.getText().toString();
        nobpom.setText(bundle.getString("nobpom"));
        uraian.setText(bundle.getString("uraian"));


       // Button button_cancel =  view.findViewById(R.id.tidak);


        firebaseAuth = FirebaseAuth.getInstance();

        button_beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                FirebaseUser currentuser = firebaseAuth.getCurrentUser();
                final String uid = currentuser.getUid();
                //final boolean[] check = {false};
                //final String[] orderID = new String[1];
                myRef = database.getReference();
                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {

                        //Checking Root Child
                        if (snapshot.hasChild("Orderan")) {
                            // run some code
                            myRef.child("Orderan").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    boolean check = false;
                                    String orderID = null;
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        orderID = dataSnapshot1.getKey();
                                        if (dataSnapshot1.child("status_order").getValue().toString().equals("checkout")) {
                                            check = true;
                                        } else {
                                            check = false;
                                        }

                                    }
                                    String ordersIDS = orderID;
                                    if (check == true) {
                                        System.out.println("masuk ini true");
                                        SecureRandom random = new SecureRandom();
                                        int nom = random.nextInt(100000);
                                        String orderIDz = String.format("%05d", nom);

                                        String itemID = database.getReference("Orderan").child(uid).child(ordersIDS).push().getKey();
                                        myRef.child("Orderan").child(uid).child(orderID).child("orderID").setValue(orderIDz);
                                        myRef.child("Orderan").child(uid).child(orderID).child("status_order").setValue("checkout");
                                        myRef.child("Orderan").child(uid).child(orderID).child(itemID).child("namaitem").setValue(namaitem.getText().toString());
                                        myRef.child("Orderan").child(uid).child(orderID).child(itemID).child("harga").setValue(hargas);
                                        myRef.child("Orderan").child(uid).child(orderID).child(itemID).child("total").setValue(Integer.parseInt(hargas) * Integer.parseInt(jumlah.getText().toString()));
                                        myRef.child("Orderan").child(uid).child(orderID).child(itemID).child("jumlahitem").setValue(jumlah.getText().toString());

                                        redirectScreen();
                                        Toast.makeText(getActivity(), "Item Berhasil di Tambah", Toast.LENGTH_LONG).show();
                                    } else {
                                        SecureRandom random = new SecureRandom();
                                        int nom = random.nextInt(100000);
                                        String orderIDz = String.format("%05d", nom);

                                        System.out.println("masuk ini false");
                                        // myRef.child("Orderan").child(uid).child(orderID).child("status_order").setValue("checkout");
                                        String key = database.getReference("Orderan").child(uid).push().getKey();
                                        String key2 = database.getReference("Orderan").child(key).push().getKey();
                                        DatabaseReference reference = database.getReference().child("Orderan").child(uid).child(key).child(key2);
                                        DatabaseReference reference2 = database.getReference().child("Orderan").child(uid).child(key);

                                        reference2.child("orderID").setValue(orderIDz);
                                        reference2.child("status_order").setValue("checkout");
                                        reference.child("namaitem").setValue(namaitem.getText().toString());
                                        reference.child("harga").setValue(hargas);
                                        reference.child("total").setValue(Integer.parseInt(hargas) * Integer.parseInt(jumlah.getText().toString()));
                                        reference.child("jumlahitem").setValue(jumlah.getText().toString());
                                        redirectScreen();
                                        Toast.makeText(getActivity(), "Item Berhasil di Tambah", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            SecureRandom random = new SecureRandom();
                            int nom = random.nextInt(100000);
                            String orderIDz = String.format("%05d", nom);

                            String key = database.getReference("Orderan").child(uid).push().getKey();
                            String key2 = database.getReference("Orderan").child(key).push().getKey();
                            DatabaseReference reference = database.getReference().child("Orderan").child(uid).child(key).child(key2);
                            DatabaseReference reference2 = database.getReference().child("Orderan").child(uid).child(key);

                            reference2.child("orderID").setValue(orderIDz);
                            reference2.child("status_order").setValue("checkout");
                            reference.child("namaitem").setValue(namaitem.getText().toString());
                            reference.child("harga").setValue(hargas);
                            reference.child("total").setValue(Integer.parseInt(hargas) * Integer.parseInt(jumlah.getText().toString()));
                            reference.child("jumlahitem").setValue(jumlah.getText().toString());


                            redirectScreen();
                            Toast.makeText(getActivity(), "Item Berhasil di Tambah", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });


//        button_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(jumlah==null){
//                    Toast.makeText(getActivity(), "Masih ada yang kosong", Toast.LENGTH_LONG).show();
//                }
//            }
//        });
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
