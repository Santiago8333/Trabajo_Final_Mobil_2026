package com.in.trabajo_final_mobil_2026.ui.stock;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.modelo.Stock;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    public interface OnStockClickListener {
        void onModificar(Stock stock);
        void onEliminar(Stock stock);
    }

    private final List<Stock> stocks;
    private final LayoutInflater inflater;
    private final OnStockClickListener listener;

    public StockAdapter(List<Stock> stocks, LayoutInflater inflater, OnStockClickListener listener) {
        this.stocks = stocks;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stock = stocks.get(position);
        holder.tvNombrePieza.setText(stock.getNombre_Pieza());
        holder.tvCantidad.setText("Cantidad: " + stock.getCantidad_Stock());
        holder.tvPrecio.setText("Precio: $" + stock.getPrecio_Unitario());
        holder.tvFechaStock.setText("Creado: " + formatearFecha(stock.getFecha_Creacion()));

        holder.btnModificarStock.setOnClickListener(v -> listener.onModificar(stock));
        holder.btnEliminarStock.setOnClickListener(v -> listener.onEliminar(stock));
    }

    @Override
    public int getItemCount() {
        return stocks != null ? stocks.size() : 0;
    }

    // convierte "2026-07-14" a "14/7/2026"
    private String formatearFecha(String fecha) {
        if (fecha == null || fecha.isEmpty()) return "";
        try {
            String soloFecha = fecha.split("T")[0].split(" ")[0]; //se queda con solo la fecha
            String[] p = soloFecha.split("-");                    // [yyyy, MM, dd]
            if (p.length == 3) {
                int anio = Integer.parseInt(p[0]);
                int mes = Integer.parseInt(p[1]);
                int dia = Integer.parseInt(p[2]);
                return dia + "/" + mes + "/" + anio;
            }
        } catch (Exception e) {
            return fecha;
        }
        return fecha;
    }

    static class StockViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombrePieza;
        TextView tvCantidad;
        TextView tvPrecio;
        TextView tvFechaStock;
        ImageButton btnModificarStock;
        ImageButton btnEliminarStock;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombrePieza = itemView.findViewById(R.id.tvNombrePieza);
            tvCantidad = itemView.findViewById(R.id.tvCantidad);
            tvPrecio = itemView.findViewById(R.id.tvPrecio);
            tvFechaStock = itemView.findViewById(R.id.tvFechaStock);
            btnModificarStock = itemView.findViewById(R.id.btnModificarStock);
            btnEliminarStock = itemView.findViewById(R.id.btnEliminarStock);
        }
    }
}
