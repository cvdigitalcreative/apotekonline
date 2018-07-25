package com.nisa.penjulanan.Boundary.Apoteker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nisa.penjulanan.Model.Model;
import com.nisa.penjulanan.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.support.constraint.Constraints.TAG;
import static android.text.TextUtils.isEmpty;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddItem extends Fragment {
    FirebaseAuth firebaseAuth;

    public AddItem() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        final EditText namaitem =  view.findViewById(R.id.namaitem);
        namaitem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        final EditText hargaitem =  view.findViewById(R.id.harga);

        final EditText jenisitem = view.findViewById(R.id.jenisitem);
        jenisitem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        final EditText stockitem = view.findViewById(R.id.stockitem);
        stockitem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        final EditText nobpomitem =  view.findViewById(R.id.nobpomitem);
        nobpomitem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        final EditText uraianitem = view.findViewById(R.id.uraianitem);
        uraianitem.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);

        firebaseAuth = FirebaseAuth.getInstance();


        Button btn_submit =  view.findViewById(R.id.btn_kirim);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: attempting to signup.");

                //check for null valued Editext
                if (!isEmpty(namaitem.getText().toString().trim()) && !isEmpty(hargaitem.getText().toString().trim()) && !isEmpty(jenisitem.getText().toString().trim())
                        && !isEmpty(stockitem.getText().toString().trim()) && !isEmpty(nobpomitem.getText().toString().trim())
                         && !isEmpty(uraianitem.getText().toString().trim())) {

                    //Insert Data Laporan
                    final String string_namaitem = namaitem.getText().toString();
                    final String string_hargaitem = hargaitem.getText().toString();
                    final String string_jenisitem = jenisitem.getText().toString();
                    final String string_stockitem = stockitem.getText().toString();
                    final String string_nobpomitem = nobpomitem.getText().toString();
                    final String string_uraianitem = uraianitem.getText().toString();

                    Insertdatalaporan(string_namaitem, string_hargaitem, string_jenisitem, string_stockitem,
                            string_nobpomitem, string_uraianitem);

                    Toast.makeText(getActivity(), "Obat Telah Berhasil Di Upload", Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(getActivity(), "Periksa Jika Masih Ada Yang Kosong", Toast.LENGTH_LONG).show();
                }
            }
        });
        return view;
    }
    private boolean isEmpty(String string){
        return string.equals("");
    }

    private void Insertdatalaporan(String namaitem, String hargaitem, String jenisitem, String stockitem, String nobpomitem, String uraianitem) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseUser currentuser = firebaseAuth.getCurrentUser();
        String uid = currentuser.getUid();

        String key = database.getReference("Items").push().getKey();
        DatabaseReference reference = database.getReference().child("Items").child(key);

        //Write harus pake model

        reference.child("terima").setValue("tidak");
        reference.child("status_done").setValue("undone");
        reference.child("status_ongoing").setValue("notgoing");
        reference.child("status_read").setValue("unread");
        reference.child("userUid").setValue(uid);
        reference.child("namaitem").setValue(namaitem);
        reference.child("harga").setValue(hargaitem);
        reference.child("jenis").setValue(jenisitem);
        reference.child("stockitem").setValue(stockitem);
        reference.child("nobpomitem").setValue(nobpomitem);
        //reference.child("jumlahitem").setValue(jumlahitem);
        reference.child("uraianitem").setValue(uraianitem);
        redirectScreen();
    }

    private void redirectScreen() {
        Log.d(TAG, "Redirecting to login screen.");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container, new AddItem())
                .addToBackStack(null).commit();
    }
}
