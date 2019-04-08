package ado.edu.pucmm.rancherasystem.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ClientSearchAdapter;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;

public class SearchClientActivity extends AppCompatActivity {

    private List<Client> clients;
    private Client client;
    private ImageView billIcon;
    private ImageView billButton;
    private TextView billAmount;
    private RanchDatabaseRepo ranchDatabaseRepo;
    private List<Bill> bills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        ranchDatabaseRepo = new RanchDatabaseRepo();
        clients = new ArrayList<Client>();
        bills = new ArrayList<Bill>();
        client = null;
        billIcon = findViewById(R.id.bill_icon);
        billButton = findViewById(R.id.bill_button);
        billAmount = findViewById(R.id.bill_clientes_text);
        AutoCompleteTextView clientAutoComplete = findViewById(R.id.search_cliente);
        ClientSearchAdapter adapter = new ClientSearchAdapter(this, R.layout.client_search_dropdown, clients);
        clientAutoComplete.setAdapter(adapter);
        clientAutoComplete.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            (adapterView, view, i, l) -> {
                billIcon.setVisibility(View.VISIBLE);
                billButton.setVisibility(View.INVISIBLE);
                client = (Client) adapterView.getItemAtPosition(i);
                bills = ranchDatabaseRepo.getDoneBills(this, client.getId());
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
                String billMessage = String.valueOf(cnt);

                if(cnt == 1) {
                    billButton.setVisibility(View.VISIBLE);
                    billMessage = billMessage + " factura vencida";
                }

                else if(cnt > 1){
                    billButton.setVisibility(View.VISIBLE);
                    billMessage = billMessage + " facturas vencidas";
                }

                else{
                    billAmount.setTextColor(Color.BLACK);
                    billMessage = " No tiene facturas vencidas";
                }

                billAmount.setText(billMessage);
                billAmount.setVisibility(View.VISIBLE);

                setText(R.id.name_clientes_text, client.getName());
                setText(R.id.phone_clientes_text, client.getPhoneNumber());
                setText(R.id.email_clientes_text, client.getEmail());
                setText(R.id.address_clientes_text, client.getAddress());
                hideKeyboard();
            };

    public void toBillList(View view){
        Intent intent = new Intent(this, ListOfBills.class);
        intent.putExtra("clientId", client.getId());
        startActivity(intent);
    }

    public void returnToMenu(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }
}