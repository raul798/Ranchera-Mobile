package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.ui.adapter.DetalleListAdapter;
import ado.edu.pucmm.rancherasystem.ui.adapter.FacturaListAdapter;
import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.viewmodel.DetailViewModel;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;
import ado.edu.pucmm.rancherasystem.viewmodel.ProductViewModel;

public class PagarFacturaActivity extends AppCompatActivity {
    private BillViewModel billViewModel;
    private ProductViewModel productoViewModel;
    private FacturaListAdapter facturaListAdapter;

    private DetalleListAdapter detalleListAdapter;
    private DetailViewModel detailViewModel;
    private TextView id_factura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pagar_factura);

        id_factura = findViewById(R.id.FacturaID);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        detalleListAdapter = new DetalleListAdapter(this);
        recyclerView.setAdapter(detalleListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        detailViewModel.getAllDetails().observe(this, new Observer<List<Detail>>() {
            @Override
            public void onChanged(@Nullable List<Detail> details) {

                detalleListAdapter.setDetalles(details);

            }
        });



    }

    public void payMe(View view) {
        Intent payIntent = new Intent(this, PagoFacturaActivity.class);
        //login logic
        startActivity(payIntent);
    }


}
