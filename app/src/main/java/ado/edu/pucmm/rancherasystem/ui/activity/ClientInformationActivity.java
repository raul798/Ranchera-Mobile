package ado.edu.pucmm.rancherasystem.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ClientSearchAdapter;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;

public class ClientInformationActivity extends AppCompatActivity
        implements BillViewModel.Listener{

    private List<Client> clients;
    private BillViewModel billViewModel;
    private Client client;
    private Bill bill;
    private int clientId;
    private TextView billAmount;
    private RanchDatabaseRepo ranchDatabaseRepo;
    private List<Bill> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_information);

        ranchDatabaseRepo = new RanchDatabaseRepo();
        billAmount = findViewById(R.id.bill_clientes_text);

        clients = new ArrayList<Client>();
        bills = new ArrayList<Bill>();
        client = null;

        billViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        this.billViewModel.setListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            clientId = extras.getInt("clientId");
        }

        bills = ranchDatabaseRepo.getDoneBills(this, clientId);
        int cnt = 0;
        float payedAmount;
        float total;
        float owed;
        for(Bill bill : bills){
            payedAmount = ranchDatabaseRepo.getBillAmount(this, bill.getId());
            total = bill.getTotal();
            owed = total - payedAmount;
            if(owed != 0) cnt++;
        }
        String billMessage =  billMessage = String.valueOf(cnt);

        if(cnt == 1) {
            billMessage = billMessage + " factura vencida";
        }

        else if(cnt > 1){
            billMessage = billMessage + " facturas vencidas";
        }

        else{
            billAmount.setTextColor(Color.BLACK);
            billMessage = " No tiene facturas vencidas";
        }

        billAmount.setText(billMessage);
        billAmount.setVisibility(View.VISIBLE);

        client = ranchDatabaseRepo.getSingleClient(this, clientId);

        setText(R.id.name_clientes_text, client.getName());
        setText(R.id.phone_clientes_text, client.getPhoneNumber());
        setText(R.id.email_clientes_text, client.getEmail());
        setText(R.id.address_clientes_text, client.getAddress());
    }

    public void cancelOrder(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    public void toProductSelection(View view) {
            bill = new Bill(client.getId(),"Pending");
            billViewModel.insert(bill);
    }

    public void toGoogleMap(View view) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("google.navigation:q=" + client.getAddress()));
        startActivity(intent);
    }

    @Override
    public void onFinish(Bill bill) {
        if(client != null) {
            Intent intent = new Intent(this, SelectProductsActivity.class);
            intent.putExtra("billId", bill.getId());
            startActivity(intent);
        }
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }
}