package com.in.trabajo_final_mobil_2026.ui.vehiculo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.modelo.Vehiculo;

import java.util.List;

public class VehiculoAdapter extends RecyclerView.Adapter<VehiculoAdapter.VehiculoViewHolder> {

    public interface OnVehiculoClickListener {
        void onModificar(Vehiculo vehiculo);
        void onEliminar(Vehiculo vehiculo);
    }

    private final List<Vehiculo> vehiculos;
    private final LayoutInflater inflater;
    private final OnVehiculoClickListener listener;

    public VehiculoAdapter(List<Vehiculo> vehiculos, LayoutInflater inflater, OnVehiculoClickListener listener) {
        this.vehiculos = vehiculos;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VehiculoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_vehiculo, parent, false);
        return new VehiculoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiculoViewHolder holder, int position) {
        Vehiculo vehiculo = vehiculos.get(position);
        holder.tvModelo.setText(vehiculo.getModelo());
        holder.tvMatricula.setText("Matrícula: " + vehiculo.getMatricula());
        holder.tvFechaVehiculo.setText("Creado: " + formatearFecha(vehiculo.getFecha_Creacion()));

        holder.btnModificarVehiculo.setOnClickListener(v -> listener.onModificar(vehiculo));
        holder.btnEliminarVehiculo.setOnClickListener(v -> listener.onEliminar(vehiculo));
    }

    @Override
    public int getItemCount() {
        return vehiculos != null ? vehiculos.size() : 0;
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

    static class VehiculoViewHolder extends RecyclerView.ViewHolder {
        TextView tvModelo;
        TextView tvMatricula;
        TextView tvFechaVehiculo;
        ImageButton btnModificarVehiculo;
        ImageButton btnEliminarVehiculo;

        public VehiculoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModelo = itemView.findViewById(R.id.tvModelo);
            tvMatricula = itemView.findViewById(R.id.tvMatricula);
            tvFechaVehiculo = itemView.findViewById(R.id.tvFechaVehiculo);
            btnModificarVehiculo = itemView.findViewById(R.id.btnModificarVehiculo);
            btnEliminarVehiculo = itemView.findViewById(R.id.btnEliminarVehiculo);
        }
    }
}
