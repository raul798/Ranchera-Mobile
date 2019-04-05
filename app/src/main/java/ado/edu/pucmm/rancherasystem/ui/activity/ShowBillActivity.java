package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ado.edu.pucmm.rancherasystem.ui.adapter.BillListAdapter;
import ado.edu.pucmm.rancherasystem.ui.adapter.DetalleListAdapter;
import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.viewmodel.DetailViewModel;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;
import ado.edu.pucmm.rancherasystem.viewmodel.ProductViewModel;

public class ShowBillActivity extends AppCompatActivity {
    private BillViewModel billViewModel;
    private ProductViewModel productoViewModel;
    private BillListAdapter billListAdapter;

    private DetalleListAdapter detalleListAdapter;
    private DetailViewModel detailViewModel;
    private TextView id_factura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_bills);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        billListAdapter = new BillListAdapter(this);
        recyclerView.setAdapter(billListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getAllDetails();



    }

    public void payMe(View view) {
        Intent payIntent = new Intent(this, PayBillActivity.class);
        //login logic
        startActivity(payIntent);
    }


}