package com.in.trabajo_final_mobil_2026.ui.stock;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.databinding.FragmentStockBinding;
import com.in.trabajo_final_mobil_2026.modelo.Stock;

public class StockFragment extends Fragment implements StockAdapter.OnStockClickListener {
    private FragmentStockBinding b;
    private StockViewModel mv;

    public static StockFragment newInstance() {
        return new StockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        b = FragmentStockBinding.inflate(getLayoutInflater());
        mv = new ViewModelProvider(this).get(StockViewModel.class);

        b.rcStock.setLayoutManager(new LinearLayoutManager(getContext()));

        mv.getListaStock().observe(getViewLifecycleOwner(), stocks ->
                b.rcStock.setAdapter(new StockAdapter(stocks, getLayoutInflater(), this)));

        mv.getMensaje().observe(getViewLifecycleOwner(), msg -> b.tvMensajeStock.setText(msg));

        b.fabCrearStock.setOnClickListener(v -> mostrarDialogoStock(null));

        mv.ObtenerStock();

        return b.getRoot();
    }

    // si stock == null -> crear; si no -> modificar (precargado)
    private void mostrarDialogoStock(Stock stock) {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_stock, null);

        TextInputEditText edNombre = dialogView.findViewById(R.id.edStockNombre);
        TextInputEditText edCantidad = dialogView.findViewById(R.id.edStockCantidad);
        TextInputEditText edPrecio = dialogView.findViewById(R.id.edStockPrecio);

        boolean esModificar = stock != null;
        if (esModificar) {
            edNombre.setText(stock.getNombre_Pieza());
            edCantidad.setText(String.valueOf(stock.getCantidad_Stock()));
            edPrecio.setText(String.valueOf(stock.getPrecio_Unitario()));
        }

        new AlertDialog.Builder(requireContext())
                .setTitle(esModificar ? "Modificar pieza" : "Crear pieza")
                .setView(dialogView)
                .setPositiveButton("Guardar", (dialog, which) -> {
                    String nombre = edNombre.getText().toString();
                    String cantidad = edCantidad.getText().toString();
                    String precio = edPrecio.getText().toString();
                    if (esModificar) {
                        mv.ModificarStock(stock.getId_Stock(), nombre, cantidad, precio);
                    } else {
                        mv.CrearStock(nombre, cantidad, precio);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onModificar(Stock stock) {
        mostrarDialogoStock(stock);
    }

    @Override
    public void onEliminar(Stock stock) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Eliminar pieza")
                .setMessage("¿Seguro que desea eliminar " + stock.getNombre_Pieza() + "?")
                .setPositiveButton("Eliminar", (dialog, which) -> mv.EliminarStock(stock.getId_Stock()))
                .setNegativeButton("Cancelar", null)
                .show();
    }
}
