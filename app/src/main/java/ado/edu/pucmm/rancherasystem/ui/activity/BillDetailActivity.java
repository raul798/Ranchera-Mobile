package ado.edu.pucmm.rancherasystem.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ado.edu.pucmm.rancherasystem.R;

public class BillDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_detail);

        TextView textview =  findViewById(R.id.text_id_bill);

        Intent intent = getIntent();
        int id =0;
        if(intent.hasExtra("id_client")) {
            Bundle b = getIntent().getExtras();
            id = b.getInt("id_client");
        }
        textview.setText(String.valueOf(id));

    }
}
