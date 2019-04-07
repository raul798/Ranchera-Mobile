package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.ByteArrayOutputStream;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.viewmodel.PaymentViewModel;

public class PayBillActivity extends AppCompatActivity {

    private PaymentViewModel paymentViewModel;
    private EditText pagoEditText;
    private int billId;
    private float toPay;
    private Bill bill;
    private int clientId;
    private RanchDatabaseRepo ranchDatabaseRepo;

    private Button finishButton;
    private ImageView finishCircle;
    private TextView finishText;
    private ImageView endStatusCircleComplete;
    private ImageView endStatusCircleIncomplete;
    private SignaturePad signaturePad;
    private TextView signatureText;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ranchDatabaseRepo = new RanchDatabaseRepo(getBaseContext());
        setContentView(R.layout.activity_pago_factura);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        pagoEditText = findViewById(R.id.MontoEditText);
        signatureText = findViewById(R.id.firme_text3);
        finishButton = findViewById(R.id.finish_button3);
        endStatusCircleComplete = findViewById(R.id.confirmacion_resumen_circle3);
        returnButton = findViewById(R.id.btn_return3);
        signaturePad = (SignaturePad) findViewById(R.id.signature_pad3);
        finishCircle = findViewById(R.id.confirmation_circle3);
        finishText = (TextView) findViewById(R.id.confirmation_text3);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

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
            billId = extras.getInt("billId");
        }

        bill = ranchDatabaseRepo.getBill(this,billId);
        clientId = bill.getClient();

    }


    public void finishOrder(View view) {
        String input = pagoEditText.getText().toString();
        if (!input.isEmpty()) {
            toPay = Float.valueOf(pagoEditText.getText().toString());
            if(toPay > 0) {
                finishCircle.setVisibility(View.VISIBLE);
                finishText.setVisibility(View.VISIBLE);
                signaturePad.setVisibility(View.INVISIBLE);
                finishButton.setVisibility(View.INVISIBLE);
                signatureText.setVisibility(View.INVISIBLE);
                returnButton.setVisibility(View.VISIBLE);
                returnButton.setEnabled(true);
                endStatusCircleComplete.setImageResource(R.drawable.ic_check_circle_24dp);
                Payment payment = new Payment(Float.parseFloat(pagoEditText.getText().toString()), billId, clientId);
                paymentViewModel.insert(payment);

                bill.setTotal(bill.getTotal() - toPay);
                Bitmap signature = signaturePad.getSignatureBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                signature.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //byte[] byteArray = stream.toByteArray();
                //ranchDatabaseRepo.updateBillSignature(this, bill_id, byteArray);
                finishText.setText("Pago realizado");
                signature.recycle();
            }

            else{
                Toast.makeText(PayBillActivity.this, "Ingrese monto a pagar válido", Toast.LENGTH_SHORT).show();
            }
        }

        else{
            Toast.makeText(PayBillActivity.this, "Ingrese monto a pagar", Toast.LENGTH_SHORT).show();
        }
    }


    public void toMenu(View view){
        Intent menuIntent = new Intent(PayBillActivity.this, MenuActivity.class);
        startActivity(menuIntent);
    }
}
