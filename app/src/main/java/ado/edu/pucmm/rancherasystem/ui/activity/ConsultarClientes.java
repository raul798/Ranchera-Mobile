package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ClientSearchAdapter;
import ado.edu.pucmm.rancherasystem.db.RanchDb;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;

public class ConsultarClientes extends AppCompatActivity
        implements BillViewModel.Listener {

    private RanchDb db;
    private List<Client> clients;
    private BillViewModel billViewModel;
    private Client client;
    private Bill bill;
    private int cnt = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_clientes);

        AutoCompleteTextView clientAutoComplete = findViewById(R.id.search_cliente);
        clients = new ArrayList<>();
        client = null;
        ClientSearchAdapter adapter = new ClientSearchAdapter(this,
                R.layout.client_search_dropdown, clients);
        clientAutoComplete.setAdapter(adapter);
        clientAutoComplete.setOnItemClickListener(onItemClickListener);
        billViewModel = ViewModelProviders.of(this).get(BillViewModel.class);
        this.billViewModel.setListener(this);
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            (adapterView, view, i, l) -> {
                client = (Client) adapterView.getItemAtPosition(i);
                setText(R.id.name_clientes_text, client.getName());
                setText(R.id.phone_clientes_text, client.getPhoneNumber());
                setText(R.id.email_clientes_text, client.getEmail());
                setText(R.id.address_clientes_text, client.getAddress());
                Toast.makeText(ConsultarClientes.this, "", Toast.LENGTH_SHORT).show();
            };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.consultar_clientes, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.helpButton) {

            Intent testIntent = new Intent(this, MainActivity.class);
            startActivity(testIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelOrder(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toProductSelection(View view) {
        if(client!= null) {
            bill = new Bill(client.getId(),"Pending");
            billViewModel.insert(bill);
        }
        else Toast.makeText(ConsultarClientes.this,"Seleccione un cliente", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFinish(Bill bill) {
        if(client != null) {
            Intent intent = new Intent(this, SeleccionarProducto.class);
            intent.putExtra("bill_id", bill.getId());
            startActivity(intent);
        }
    }
}