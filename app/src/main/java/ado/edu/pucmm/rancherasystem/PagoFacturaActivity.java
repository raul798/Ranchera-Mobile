package ado.edu.pucmm.rancherasystem;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ado.edu.pucmm.rancherasystem.db.HelpRequestViewModel;
import ado.edu.pucmm.rancherasystem.db.Pago;
import ado.edu.pucmm.rancherasystem.db.PagoViewModel;

public class PagoFacturaActivity extends AppCompatActivity {

    private PagoViewModel pagoViewModel;
    private EditText pagoEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_factura);
        pagoViewModel = ViewModelProviders.of(this).get(PagoViewModel.class);
        pagoEditText = findViewById(R.id.MontoEditText);
        Button button = findViewById(R.id.RealizarPago);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                /*
                Intent resultIntent = new Intent();

                if(TextUtils.isEmpty(question.getText())) {
                    setResult(RESULT_CANCELED, resultIntent);
                }
                else {
                    String questionText = question.getText().toString();
                    String commentText = comment.getText().toString();

                    resultIntent.putExtra(QUESTION_ADDED, questionText);
                    resultIntent.putExtra(COMMENT_ADDED, commentText);

                    setResult(RESULT_OK, resultIntent);
                }
                finish();
                */
                Pago pago = new Pago(Double.parseDouble(pagoEditText.getText().toString()), 1,1 );
                pagoViewModel.insert(pago);


                Toast.makeText(
                        getApplicationContext(),
                        "pago realizado correctamente",
                        Toast.LENGTH_LONG).show();

                Intent mainIntent = new Intent(PagoFacturaActivity.this, MainActivity.class);
                startActivity(mainIntent);

            }
        });
    }




}
