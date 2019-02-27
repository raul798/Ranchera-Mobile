package ado.edu.pucmm.rancherasystem;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.db.Client;
import ado.edu.pucmm.rancherasystem.db.RancheraDB;

public class ConsultarClientes extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String DATABASE_NAME = "ranchera_database";
    private RancheraDB db;
    private List<Client> clients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*
        db = Room.databaseBuilder(getApplicationContext(),
                RancheraDB.class, DATABASE_NAME)
                .build();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Client client = new Client(1,"Raúl","809-888-8888",
                        "PUCMM edificio de ingeniería y ciencias","habichuelon@gmail.com");
            }
        }) .start();
        */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_clientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //String[] clientes = getResources().getStringArray(R.array.test_clientes);

        AutoCompleteTextView clientAutoComplete = findViewById(R.id.search_cliente);
        clients = new ArrayList<Client>();
        ClientSearchAdapter adapter = new ClientSearchAdapter(this,
                R.layout.client_search_dropdown, clients);
        clientAutoComplete.setAdapter(adapter);
        clientAutoComplete.setOnItemClickListener(onItemClickListener);
        //List<Client> adapter = new List<String>(this, android.R.layout.simple_list_item_1, clientes);
        //editText.setAdapter(adapter);

        /*
        final TextView nameTextView = (TextView)findViewById(R.id.name_clientes_text);
        final TextView phoneTextView = (TextView)findViewById(R.id.phone_clientes_text);
        final TextView emailTextView = (TextView)findViewById(R.id.email_clientes_text);
        final TextView addressTextView = (TextView)findViewById(R.id.address_clientes_text);
        editText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                String selection = (String)parent.getItemAtPosition(position);
                nameTextView.setText(selection);
                phoneTextView.setText("809-123-4567");
                emailTextView.setText(selection + "@email.com");
                addressTextView.setText("Test address #50");
            }
        });
        */
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Client client = ( Client) adapterView.getItemAtPosition(i);
                    setText(R.id.name_clientes_text, client.getName());
                    setText(R.id.phone_clientes_text, client.getPhoneNumber());
                    setText(R.id.email_clientes_text, client.getEmail());
                    setText(R.id.address_clientes_text, client.getAddress());
                    Toast.makeText(ConsultarClientes.this, "", Toast.LENGTH_SHORT).show();
                }
            };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_resumen) {
            Intent testIntent = new Intent(this, MainActivity.class);
            startActivity(testIntent);

        } else if (id == R.id.nav_productos) {

        } else if (id == R.id.nav_consultar_cliente) {

        } else if (id == R.id.nav_ordenes) {
            //do nothing
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

        Intent intent = new Intent(this, SeleccionarProducto.class);
        startActivity(intent);
    }


}
