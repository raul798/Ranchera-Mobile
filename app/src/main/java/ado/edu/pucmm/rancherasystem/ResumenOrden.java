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
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ado.edu.pucmm.rancherasystem.db.Bill;
import ado.edu.pucmm.rancherasystem.db.Client;
import ado.edu.pucmm.rancherasystem.db.Factura;
import ado.edu.pucmm.rancherasystem.db.Product;
import ado.edu.pucmm.rancherasystem.db.RancheraDatabaseRepo;

public class ResumenOrden extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int bill_id;
    private Factura bill;
    private Client client;
    private List<Product> products;
    private RancheraDatabaseRepo rancheraDatabaseRepo = new RancheraDatabaseRepo();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumen_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bill_id = extras.getInt("bill_id");
        }

        bill = rancheraDatabaseRepo.getBill(this, bill_id);

        client = rancheraDatabaseRepo.getSingleClient(this,bill.getId_client());


        //setText(R.id.name_clientes_text, client.getName());
        //setText(R.id.phone_clientes_text, client.getPhoneNumber());
        //setText(R.id.email_clientes_text, client.getEmail());
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resumen_orden, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.helpButton) {
            Intent testIntent = new Intent(this, MainActivity.class);
            startActivity(testIntent);
        }

        return super.onOptionsItemSelected(item);
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

    public void toSelectProducto(View view) {

        Intent intent = new Intent(this, SeleccionarProducto.class);
        startActivity(intent);
    }

    public void toConfirmacion(View view) {

        Intent intent = new Intent(this, ConfirmacionOrden.class);
        startActivity(intent);
    }
}
