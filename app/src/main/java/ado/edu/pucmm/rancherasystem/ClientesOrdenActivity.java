package ado.edu.pucmm.rancherasystem;

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

import java.util.ArrayList;
import java.util.List;

public class ClientesOrdenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AutoCompleteTextView autoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       // String[] clientes = getResources().getStringArray(R.array.test_clientes);

        autoCompleteTextView = findViewById(R.id.autoCompleteTextView);
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, clientes);
       // editText.setAdapter(adapter);


        //Test names for the database
        /*
        RancheraDB rancheraDB = new RancheraDB(getApplicationContext());
        rancheraDB.create(new Client("Raul Test", "809-123-4567", "raul.test@email.com", "Test Address #10"));
        rancheraDB.create(new Client("Natalia Test", "829-123-4567", "natalia.test@email.com", "Test Address #20"));
        rancheraDB.create(new Client("Kelvin Test", "849-123-4567", "kelvin.test@email.com", "Test Address #30"));
        rancheraDB.create(new Client("Dante Test", "809-111-4567", "dante.test@email.com", "Test Address #40"));
        rancheraDB.create(new Client("Edmundy Test", "829-111-4567", "edmundy.test@email.com", "Test Address #50"));
        */

        loadData();

        //rancheraDB.create(new Client("Raul2 Test", "809-321-4567", "raul2.test@email.com", "Test Address #15"));
        //rancheraDB.create(new Client("Natalia2 Test", "829-321-4567", "natalia2.test@email.com", "Test Address #25"));
        //rancheraDB.create(new Client("Kelvin2 Test", "849-321-4567", "kelvin2.test@email.com", "Test Address #35"));
        //rancheraDB.create(new Client("Dante2 Test", "809-222-4567", "dante2.test@email.com", "Test Address #45"));
        //rancheraDB.create(new Client("Edmundy2 Test", "829-222-4567", "edmundy2.test@email.com", "Test Address #55"));

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

    private void loadData() {
        List<Client> clients = new ArrayList<Client>();
        ClientSearchAdapter clientSearchAdapter = new ClientSearchAdapter(getApplicationContext(), clients);
        autoCompleteTextView.setThreshold(1);
        autoCompleteTextView.setAdapter(clientSearchAdapter);
    }


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

        Intent intent = new Intent(this, ProductoOrdenActivity.class);
        startActivity(intent);
    }


}
