package ado.edu.pucmm.rancherasystem.ui.activity;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.adapters.ClientSearchAdapter;
import ado.edu.pucmm.rancherasystem.adapters.ProductSearchAdapter;
import ado.edu.pucmm.rancherasystem.entity.Bill;
import ado.edu.pucmm.rancherasystem.entity.Client;
import ado.edu.pucmm.rancherasystem.entity.Product;
import ado.edu.pucmm.rancherasystem.viewmodel.BillViewModel;

public class SearchProductActivity extends AppCompatActivity {

    private List<Product> products;
    private Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);

        products = new ArrayList<Product>();
        product = null;

        AutoCompleteTextView productAutoComplete = findViewById(R.id.search_cliente);
        ProductSearchAdapter adapter = new ProductSearchAdapter(this, R.layout.product_search_dropdown, products);
        productAutoComplete.setAdapter(adapter);
        productAutoComplete.setOnItemClickListener(onItemClickListener);
    }

    private AdapterView.OnItemClickListener onItemClickListener =
            (adapterView, view, i, l) -> {
                product = (Product) adapterView.getItemAtPosition(i);
                setText(R.id.name_clientes_text, String.valueOf(product.getName()));
                setText(R.id.phone_clientes_text, String.valueOf(product.getQuantity()));
                setText(R.id.email_clientes_text, String.valueOf(product.getPrice()));
                setText(R.id.address_clientes_text, product.getDescription());
                hideKeyboard();
            };

    public void returnToMenu(View view) {
        startActivity(new Intent(this, MenuActivity.class));
    }

    private void setText(int resourceId, String text){
        ((TextView)findViewById(resourceId)).setText(text);
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getRootView().getWindowToken(), 0);
    }
}