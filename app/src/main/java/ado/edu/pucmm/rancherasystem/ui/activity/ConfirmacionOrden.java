package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;

public class ConfirmacionOrden extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Button finishButton;
    private ImageView finishCircle;
    private ImageView uninishCircle;
    private TextView finishText;
    private ImageView endStatusCircleComplete;
    private ImageView endStatusCircleIncomplete;
    private SignaturePad mSignaturePad;
    private TextView signatureText;
    private Button returnButton;
    private RanchDatabaseRepo ranchDatabaseRepo =  new RanchDatabaseRepo();
    private int bill_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //set button unabled
        finishButton = (Button) findViewById(R.id.finish_button);
        finishButton.setEnabled(false);
        finishButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));

        finishCircle = (ImageView) findViewById(R.id.confirmation_circle);
        finishCircle.setVisibility(View.INVISIBLE);

        finishText = (TextView) findViewById(R.id.confirmation_text);
        signatureText = (TextView) findViewById(R.id.firme_text);
        finishText.setVisibility(View.INVISIBLE);


        endStatusCircleComplete = (ImageView) findViewById(R.id.confirmation_final_circle);
        endStatusCircleIncomplete = (ImageView) findViewById(R.id.confirmacion_confirmacion_circle);
        endStatusCircleComplete.setVisibility(View.INVISIBLE);

        returnButton = findViewById(R.id.btn_return);
        returnButton.setEnabled(false);
        returnButton.setVisibility(View.INVISIBLE);

        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                finishButton.setEnabled(true);
                finishButton.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bill_id = extras.getInt("bill_id");
        }
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
        getMenuInflater().inflate(R.menu.confirmacion_orden, menu);
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

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    public void finishOrder(View view) {

        finishCircle.setVisibility(View.VISIBLE);
        finishText.setVisibility(View.VISIBLE);
        endStatusCircleComplete.setVisibility(View.VISIBLE);
        endStatusCircleIncomplete.setVisibility(View.INVISIBLE);
        mSignaturePad.setVisibility(View.INVISIBLE);
        finishButton.setVisibility(View.INVISIBLE);
        signatureText.setVisibility(View.INVISIBLE);
        returnButton.setVisibility(View.VISIBLE);
        returnButton.setEnabled(true);

        ranchDatabaseRepo.updateBillDescription(this, bill_id, "Done");

        Bitmap signature = mSignaturePad.getSignatureBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        ranchDatabaseRepo.updateBillSignature(this, bill_id, byteArray);
        setText(R.id.confirmation_text, "Orden #"+ String.valueOf(bill_id) + " enviada");
        signature.recycle();
    }

    public void toDashboard(View view) {

        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

}