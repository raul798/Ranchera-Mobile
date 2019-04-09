package ado.edu.pucmm.rancherasystem.ui.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ProductRecyclerViewAdapter;
import ado.edu.pucmm.rancherasystem.adapters.ProductSearchAdapter;
import ado.edu.pucmm.rancherasystem.db.RanchDatabaseRepo;
import ado.edu.pucmm.rancherasystem.entity.Detail;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.viewmodel.DetailViewModel;
import ado.edu.pucmm.rancherasystem.viewmodel.ProductViewModel;

public class SelectProductsActivity extends AppCompatActivity
    implements AdapterView.OnItemClickListener {

    private List<Product> products;
    private List<Integer> amounts;
    private List<Integer> max;

    private AutoCompleteTextView productAutoComplete;
    private ProductViewModel productViewModel;
    private ProductRecyclerViewAdapter recyclerAdapter;
    private int billId;
    private Detail detail;
    private DetailViewModel detailViewModel;
    final static int PRESET_AMOUNT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_producto);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerAdapter = new ProductRecyclerViewAdapter(this);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(this,(x) -> {});

        productAutoComplete = findViewById(R.id.search_producto);
        products = new ArrayList<>();
        amounts = new ArrayList<>();
        max = new ArrayList<>();

        ProductSearchAdapter adapter = new ProductSearchAdapter(this,
                R.layout.product_search_dropdown, products);
        productAutoComplete.setAdapter(adapter);
        productAutoComplete.setOnItemClickListener(this);

        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            billId = extras.getInt("billId");
        }
    }

    public void toClientSelect(View view) {
        Intent intent = new Intent(this, SelectClientActivity.class);
        startActivity(intent);
    }

    public void toOrderSummary(View view) {
        if(!products.isEmpty()) {
            int index = 0;
            int amount;
            for (Product product : products) {
                amount = amounts.get(index);
                detail = new Detail(billId, product.getId(), amount);
                detailViewModel.insert(detail);
                index++;
            }
            Intent intent = new Intent(this, OrderSummaryActivity.class);
            intent.putExtra("billId", billId);
            startActivity(intent);
        } else{
            Toast.makeText(SelectProductsActivity.this,"Seleccione un producto", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Product product = (Product) adapterView.getItemAtPosition(i);

        boolean isThere = false;
        for (Product product1 : products) {
            if(product1.getName().equals(product.getName())){
                isThere = true;
            }
        }

        if(!isThere) {
            if(product.getQuantity() > 0) {
                products.add(product);
                amounts.add(PRESET_AMOUNT);
                max.add(PRESET_AMOUNT);
                recyclerAdapter.setProducts(products);
                recyclerAdapter.setAmounts(amounts);
                recyclerAdapter.setMax(max);
            }
            else{
                Toast.makeText(SelectProductsActivity.this, "Actualmente no hay " + product.getName() + " en inventario.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
