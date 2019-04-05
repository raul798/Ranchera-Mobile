package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.entity.Payment;
import ado.edu.pucmm.rancherasystem.viewmodel.PaymentViewModel;

public class PayBillActivity extends AppCompatActivity {

    private PaymentViewModel paymentViewModel;
    private EditText pagoEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_factura);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        pagoEditText = findViewById(R.id.MontoEditText);
        Button button = findViewById(R.id.RealizarPago);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Payment payment = new Payment(Float.parseFloat(pagoEditText.getText().toString()), 1,1 );
                paymentViewModel.insert(payment);

                Toast.makeText(
                        getApplicationContext(),
                        "payment realizado correctamente",
                        Toast.LENGTH_LONG).show();

                Intent mainIntent = new Intent(PayBillActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }
        });
    }




}
