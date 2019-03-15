package ado.edu.pucmm.rancherasystem;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
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

import ado.edu.pucmm.rancherasystem.db.Detalle;
import ado.edu.pucmm.rancherasystem.db.DetalleViewModel;
import ado.edu.pucmm.rancherasystem.db.Factura;
import ado.edu.pucmm.rancherasystem.db.FacturaViewModel;
import ado.edu.pucmm.rancherasystem.db.HelpRequestViewModel;
import ado.edu.pucmm.rancherasystem.db.Producto;
import ado.edu.pucmm.rancherasystem.db.ProductoViewModel;

public class PagarFacturaActivity extends AppCompatActivity {
    private FacturaViewModel facturaViewModel;
    private ProductoViewModel productoViewModel;
    private FacturaListAdapter facturaListAdapter;

    private DetalleListAdapter detalleListAdapter;
    private DetalleViewModel detalleViewModel;
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


        detalleViewModel = ViewModelProviders.of(this).get(DetalleViewModel.class);
        detalleViewModel.getAllDetalles().observe(this, new Observer<List<Detalle>>() {
            @Override
            public void onChanged(@Nullable List<Detalle> detalles) {

                detalleListAdapter.setDetalles(detalles);

            }
        });



    }

    public void payMe(View view) {
        Intent payIntent = new Intent(this, PagoFacturaActivity.class);
        //login logic
        startActivity(payIntent);
    }


}
