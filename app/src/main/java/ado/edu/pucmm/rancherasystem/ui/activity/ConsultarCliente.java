package ado.edu.pucmm.rancherasystem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import ado.edu.pucmm.rancherasystem.R;

public  class ConsultarCliente extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar__clientes);
    }
    public void ToastTBD(View view){
        // Toast myToast = Toast.makeText(this, message, duration);
        Toast myToast = Toast.makeText(this, "TO BE DESIGNED",
                Toast.LENGTH_SHORT);
        myToast.show();
    }
}