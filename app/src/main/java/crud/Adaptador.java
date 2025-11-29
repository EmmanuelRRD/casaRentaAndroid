package crud;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.casarenta.MainAdmin;
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
        cliente cl = lista.get(position);
        h.nombre.setText(cl.getNombre());
        h.primerAp.setText(cl.getPrimerAp());
        h.segundoAp.setText(cl.getSegundoAp());
        h.correo.setText(cl.getCorreo());

        h.editar.setOnClickListener(v -> {
            int pos = h.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                //Gialog de agregar
                Dialog dialogo = new Dialog(a);
                dialogo.setTitle("Editar registro");
                dialogo.setCancelable(true);
                dialogo.setContentView(R.layout.dialogo);
                dialogo.show();

                EditText nombre = dialogo.findViewById(R.id.caja_nombre);
                EditText pa = dialogo.findViewById(R.id.caja_pa);
                EditText sa = dialogo.findViewById(R.id.caja_sa);
                EditText email = dialogo.findViewById(R.id.caja_correo);
                EditText fechaNac = dialogo.findViewById(R.id.fecha_nac);
                EditText identificador = dialogo.findViewById(R.id.caja_id);
                EditText tel = dialogo.findViewById(R.id.caja_tel);
                Spinner sexo = dialogo.findViewById(R.id.spinner_sexo);
                Button guardar = dialogo.findViewById(R.id.btn_add);
                guardar.setText("Guardar");

                identificador.setKeyListener(null);

                Button cancelar = dialogo.findViewById(R.id.btn_cancelar);

                cliente c = lista.get(pos);

                c= lista.get(pos);

                identificador.setText(c.getIdCliente());
                nombre.setText(c.getNombre());
                pa.setText(c.getPrimerAp());
                sa.setText(c.getSegundoAp());
                email.setText(c.getCorreo());
                fechaNac.setText(c.getFechaNac());
                tel.setText(c.getNumTel());
                String valorSexo = c.getSexo();

                ArrayAdapter adapterSpinner = (ArrayAdapter) sexo.getAdapter();//para sacar la posicion del spiner
                int posSexo = adapterSpinner.getPosition(valorSexo);
                sexo.setSelection(posSexo);

                nombre.setText(c.getNombre());

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //Vamos a pasar como parametros lo recopilado del dialogo.xml
                            int pos = h.getAdapterPosition();

                            if (pos != RecyclerView.NO_POSITION) {
                                cliente c = lista.get(pos);

                                c = new cliente(
                                        identificador.getText().toString(),
                                        nombre.getText().toString(),
                                        pa.getText().toString(),
                                        sa.getText().toString(),
                                        sexo.getSelectedItem().toString(),
                                        fechaNac.getText().toString(),
                                        email.getText().toString(),
                                        tel.getText().toString()
                                );

                                dao.editar(c);

                                // Actualiza la lista y refresca solo esa fila
                                lista.set(pos, c);
                                notifyItemChanged(pos);

                                dialogo.dismiss();


                            }

                        }catch (Exception e){
                            Toast.makeText(a.getApplication(),"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                cancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogo.dismiss();
                    }
                });

            }



        });

        h.eliminar.setOnClickListener(v -> {
            int pos = h.getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION) {

                cliente c = lista.get(pos);

                AlertDialog.Builder del = new AlertDialog.Builder(a);
                del.setMessage("¿Seguro que desea eliminar este registro?");
                del.setPositiveButton("Sí", (dialog, which) -> {

                    dao.eliminar(c.getIdCliente());

                    lista.remove(pos);

                    notifyItemRemoved(pos);
                    notifyItemRangeChanged(pos, lista.size());

                });
                del.setNegativeButton("No", null);
                del.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
