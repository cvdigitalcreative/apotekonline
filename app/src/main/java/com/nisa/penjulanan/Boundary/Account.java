package com.nisa.penjulanan.Boundary;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nisa.penjulanan.LoginRegister;
import com.nisa.penjulanan.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Account extends Fragment {
    FirebaseAuth firebaseAuth;
    DatabaseReference myStatus;
    FirebaseDatabase database2;

    public Account() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_account, container, false);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        database2 = FirebaseDatabase.getInstance();
        myStatus = database2.getReference("Users").child(user.getUid());
        myStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TextView usernameq = view.findViewById(R.id.your_name);
                TextView email = view.findViewById(R.id.email);
                TextView alamat = view.findViewById(R.id.alamauset);

                String nama = dataSnapshot.child("username").getValue().toString();
                String emaila = dataSnapshot.child("email").getValue().toString();
                String alamatla = dataSnapshot.child("alamat").getValue().toString();

                usernameq.setText(nama);
                email.setText(emaila);
                alamat.setText(alamatla);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Button btn_signout = view.findViewById(R.id.btn_logout);

        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

                Intent in = new Intent(getActivity(), LoginRegister.class);
                startActivity(in);
            }
        });
        return view;
    }

}
