package com.in.trabajo_final_mobil_2026.ui.reparacion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.in.trabajo_final_mobil_2026.R;
import com.in.trabajo_final_mobil_2026.modelo.Reparacion;

import java.util.List;

public class ReparacionAdapter extends RecyclerView.Adapter<ReparacionAdapter.ReparacionViewHolder> {

    public interface OnReparacionClickListener {
        void onModificar(Reparacion reparacion);
        void onEliminar(Reparacion reparacion);
    }

    private final List<Reparacion> reparaciones;
    private final LayoutInflater inflater;
    private final OnReparacionClickListener listener;

    public ReparacionAdapter(List<Reparacion> reparaciones, LayoutInflater inflater,
                             OnReparacionClickListener listener) {
        this.reparaciones = reparaciones;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ReparacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_reparacion, parent, false);
        return new ReparacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReparacionViewHolder holder, int position) {
        Reparacion reparacion = reparaciones.get(position);
        holder.tvCliente.setText(reparacion.getNombre_Cliente());
        holder.tvMotivo.setText("Motivo: " + reparacion.getMotivo_Ingreso());
        holder.tvDescripcion.setText("Trabajo: " + reparacion.getDescripcion_Trabajo_Realizado());
        holder.tvCosto.setText("Mano de obra: $" + reparacion.getCosto_Mano_De_Obra());
        holder.tvFechaIngreso.setText("Ingreso: " + formatearFecha(reparacion.getFecha_Ingreso()));

        holder.btnModificarReparacion.setOnClickListener(v -> listener.onModificar(reparacion));
        holder.btnEliminarReparacion.setOnClickListener(v -> listener.onEliminar(reparacion));
    }

    @Override
    public int getItemCount() {
        return reparaciones != null ? reparaciones.size() : 0;
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

    static class ReparacionViewHolder extends RecyclerView.ViewHolder {
        TextView tvCliente;
        TextView tvMotivo;
        TextView tvDescripcion;
        TextView tvCosto;
        TextView tvFechaIngreso;
        ImageButton btnModificarReparacion;
        ImageButton btnEliminarReparacion;

        public ReparacionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCliente = itemView.findViewById(R.id.tvCliente);
            tvMotivo = itemView.findViewById(R.id.tvMotivo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
            tvCosto = itemView.findViewById(R.id.tvCosto);
            tvFechaIngreso = itemView.findViewById(R.id.tvFechaIngreso);
            btnModificarReparacion = itemView.findViewById(R.id.btnModificarReparacion);
            btnEliminarReparacion = itemView.findViewById(R.id.btnEliminarReparacion);
        }
    }
}
