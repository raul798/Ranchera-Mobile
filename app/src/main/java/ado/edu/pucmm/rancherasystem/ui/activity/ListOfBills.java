package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.ui.adapter.BillListAdapter;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;

public class ListOfBills extends AppCompatActivity {

    private BillViewModel billViewModel;
    private RanchDatabaseRepo repo;
    private List<Bill> bills;
    private Bill bill;
    private Integer clientId;
    private TextView clientName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);
        final BillListAdapter adapter = new BillListAdapter(this);
        billViewModel = ViewModelProviders.of(this).get(BillViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        repo = new RanchDatabaseRepo(getApplication());

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            clientId = extras.getInt("clientId");
        }

        clientName = findViewById(R.id.name_clientes_text);
        Client current = repo.getSingleClient(this,clientId);
        clientName.setText(current.getName());

        bills = repo.getDoneBills(this, clientId);
        float payedAmount;
        float total;
        for(Bill bill : bills){
            payedAmount = repo.getBillAmount(this,bill.getId());
            total = bill.getTotal();

            if(total - payedAmount <= 0){
                bills.remove(bill);
                break;
            }
        }

        adapter.setBills(bills);

    }

    public void toSearchClient(View view){
        Context context = view.getContext();
        Intent intent = new Intent(context, SearchClientActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ClientInformationActivity.class);
        startActivity(intent);
    }

}