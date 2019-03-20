package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.ui.adapter.BillListAdapter;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;

public class ListOfBills extends AppCompatActivity {

    private BillViewModel mBillViewModel;
    private RanchDatabaseRepo repo;
    private List<Bill> bills;
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);

        final BillListAdapter adapter = new BillListAdapter(this);
        mBillViewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        repo = new RanchDatabaseRepo(getApplication());

        bills = repo.getBills(this,1 );

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setBills(bills);
        adapter.notifyDataSetChanged();
    }

}