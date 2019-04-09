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
import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Product;

public class ConfirmOrderActivity extends AppCompatActivity {

    private Button finishButton;
    private ImageView finishCircle;
    private TextView finishText;
    private ImageView endStatusCircleComplete;
    private ImageView endStatusCircleIncomplete;
    private SignaturePad mSignaturePad;
    private TextView signatureText;
    private Button returnButton;
    private RanchDatabaseRepo ranchDatabaseRepo =  new RanchDatabaseRepo();
    private int bill_id;
    private Intent intent;
    private List<Integer> products;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_orden);

        products = new ArrayList<Integer>();

        //set button unabled
        finishButton = (Button) findViewById(R.id.finish_button);
        finishButton.setEnabled(false);
        finishButton.setBackgroundColor(getResources().getColor(R.color.customRed));

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
                finishButton.setBackgroundColor(getResources().getColor(R.color.colorGreen));
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            bill_id = extras.getInt("billId");
        }
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

        Client client = null;
        Bill bill = null;

        bill = ranchDatabaseRepo.getBill(this, bill_id);
        client = ranchDatabaseRepo.getSingleClient(this, bill.getClient());

        intent = new Intent(this, MenuActivity.class);
        intent.putExtra("clientId", client.getId());

        //aiuda
        products = ranchDatabaseRepo.getProductsFromDetail(this, bill_id);
        int selectedQuantity;
        for (Integer productId : products){
            product = ranchDatabaseRepo.getOrderProduct(this, productId);
            selectedQuantity = ranchDatabaseRepo.getSelectedProductAmount(this, productId, bill_id);
            ranchDatabaseRepo.updateQuantity(this, productId, product.getQuantity() - selectedQuantity);
        }
    }

    public void toDashboard(View view) {
        startActivity(intent);
    }

}