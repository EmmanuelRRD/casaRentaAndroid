package crud;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.casarenta.R;
import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    ArrayList<cliente> lista;
    daoCliente dao;
    Activity a;

    public Adaptador(ArrayList<cliente> lista, daoCliente dao, Activity a) {
        this.lista = lista;
        this.dao = dao;
        this.a = a;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, primerAp, segundoAp, correo;
        ImageView editar, eliminar;

        public ViewHolder(View v) {
            super(v);
            nombre = v.findViewById(R.id.text_nombre);
            primerAp = v.findViewById(R.id.text_pa);
            segundoAp = v.findViewById(R.id.text_sa);
            correo = v.findViewById(R.id.text_correo);
            editar = v.findViewById(R.id.btn_editar);
            eliminar = v.findViewById(R.id.btn_eliminar);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder h, int position) {
        cliente c = lista.get(position);

        h.nombre.setText(c.getNombre());
        h.primerAp.setText(c.getPrimerAp());
        h.segundoAp.setText(c.getSegundoAp());
        h.correo.setText(c.getCorreo());

        h.editar.setOnClickListener(v -> {
            // lógica editar
        });

        h.eliminar.setOnClickListener(v -> {
            // lógica eliminar
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
