package ado.edu.pucmm.rancherasystem.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import ado.edu.pucmm.rancherasystem.R;
import ado.edu.pucmm.rancherasystem.db.Product;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<ProductRecyclerViewAdapter.ProductViewHolder> {

    class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView productItemView;
        private final TextView productQuantityView;
        private final TextView productPriceView;
        private final TextView productStockView;
        private final DetailsAdapterListener onClickListener;
        private final Button plusButton;
        private final Button minusButton;

        private ProductViewHolder(View itemView) {
            super(itemView);
            plusButton = itemView.findViewById(R.id.bttn_plus);
            minusButton = itemView.findViewById(R.id.bttn_minus);
            productPriceView = itemView.findViewById(R.id.productPriceRecyclerView);
            productStockView = itemView.findViewById(R.id.productStockRecyclerView);
            onClickListener = new DetailsAdapterListener();
            productItemView = itemView.findViewById(R.id.productNameRecyclerView);
            productQuantityView = itemView.findViewById(R.id.quantityView);
            plusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickPlus(v, getAdapterPosition());
                }
            });
            minusButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClickMinus(v, getAdapterPosition());
                }
            });
        }

    }

    private final LayoutInflater inflater;
    private List<Product> products; // Cached copy of products
    private List<Integer> amounts;

    public ProductRecyclerViewAdapter(Context context) { inflater = LayoutInflater.from(context); }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.recyclerview_product, parent, false);
        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        if (products != null) {
            Product current = products.get(position);
            String priceString = "Price: $" + String.valueOf(current.getPrice());
            String stockString = "Stock: " + String.valueOf(current.getQuantity());
            int currentAmount = amounts.get(position);
            holder.productItemView.setText(current.getName());
            holder.productQuantityView.setText(String.valueOf(currentAmount));
            holder.productPriceView.setText(priceString);
            holder.productStockView.setText(stockString);

        } else {
            // Covers the case of data not being ready yet.
            holder.productItemView.setText("No Product");
        }
    }

    public void setProducts(List<Product> products){
        this.products = products;
        notifyDataSetChanged();
    }

    public void setAmounts(List<Integer> amounts){
        this.amounts = amounts;
        notifyDataSetChanged();
    }

    public void setAmount(int amount){
        int current = getItemCount();
        amounts.set(--current,amount);
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mProducts has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (products != null)
            return products.size();
        else return 0;
    }

    public int getAmount(){
        int current = getItemCount();
        return amounts.get(--current);
    }

    public class DetailsAdapterListener {

        void onClickPlus(View view, int position){
            int current = amounts.get(position);
            amounts.set(position,++current);
            notifyDataSetChanged();
        }

        void onClickMinus(View view, int position){
            int current = amounts.get(position);
            if(current > 0) {
                amounts.set(position, --current);
            }
            notifyDataSetChanged();
        }
    }

}
