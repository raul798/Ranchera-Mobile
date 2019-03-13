package ado.edu.pucmm.rancherasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Bill;

import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class ListOfBills extends AppCompatActivity {

    private BillViewModel mBillViewModel;
    private RancheraDatabaseRepo repo;
    private List<Bill> bills;
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);

        final BillListAdapter adapter = new BillListAdapter(this);
        mBillViewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        repo = new RancheraDatabaseRepo(getApplication());

        bill = new Bill(1,5000,"3/4/16",1);
        repo.insert(bill);
        bill = new Bill(2,5340,"3/6/16",1);
        repo.insert(bill);
        bill = new Bill(3,3456,"3/8/16",1);
        repo.insert(bill);
        bills = repo.getBills(this,1 );

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setBills(bills);
        adapter.notifyDataSetChanged();
    }

}
