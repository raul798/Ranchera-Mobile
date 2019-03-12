package ado.edu.pucmm.rancherasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.RoomDatabase;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Bill;
import ado.edu.pucmm.rancherasystem.db.BillDao;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class ListOfBills extends AppCompatActivity {

    private BillViewModel mBillViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final BillListAdapter adapter = new BillListAdapter(this);

        /*
        mBillViewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        mBillViewModel.getmAllBills().observe(this, new Observer<List<Bill>>() {
            @Override
            public void onChanged(@Nullable final List<Bill> bills) {
                // Update the cached copy of the words in the adapter.
                adapter.setBills(bills);
            }
        }); */

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }

}
