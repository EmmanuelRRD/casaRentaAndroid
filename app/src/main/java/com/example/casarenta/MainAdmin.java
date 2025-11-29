package com.example.casarenta;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import crud.Adaptador;
import crud.cliente;
import crud.daoCliente;

public class MainAdmin extends AppCompatActivity {

    daoCliente dao;
    Adaptador adapter;
    ArrayList<cliente> lista;
    cliente c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);

        dao = new daoCliente(this);
        lista = dao.verTodos();
        adapter = new Adaptador(lista, dao, this);

        RecyclerView list = findViewById(R.id.lista_recycler);
        ImageButton agregar = findViewById(R.id.btn_agregar);
        SearchView buscador = findViewById(R.id.searchView);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        // --- BUSCADOR ---
        buscador.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filtrar(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filtrar(newText);
                return true;
            }
        });

        // --- Crear elementos ---
        agregar.setOnClickListener(v -> {
            Dialog dialogo = new Dialog(MainAdmin.this);
            dialogo.setTitle("Nuevo Cliente");
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
            Button cancelar = dialogo.findViewById(R.id.btn_cancelar);

            guardar.setText("Agregar");

            guardar.setOnClickListener(v2 -> {

                // Capturar los valores
                String id = identificador.getText().toString().trim();
                String nom = nombre.getText().toString().trim();
                String ape1 = pa.getText().toString().trim();
                String ape2 = sa.getText().toString().trim();
                String sex = sexo.getSelectedItem().toString();
                String fecha = fechaNac.getText().toString().trim();
                String mail = email.getText().toString().trim();
                String telefono = tel.getText().toString().trim();

                // Validaciones -----------------------------

                if (id.isEmpty()) {
                    identificador.setError("Ingrese un identificador");
                    identificador.requestFocus();
                    return;
                }

                if (nom.isEmpty()) {
                    nombre.setError("Ingrese un nombre");
                    nombre.requestFocus();
                    return;
                }

                if (ape1.isEmpty()) {
                    pa.setError("Ingrese el primer apellido");
                    pa.requestFocus();
                    return;
                }

                if (ape2.isEmpty()) {
                    sa.setError("Ingrese el segundo apellido");
                    sa.requestFocus();
                    return;
                }

                if (sex.equals("Seleccione")) {   // Si tu spinner tiene "Seleccione" en el xml
                    Toast.makeText(MainAdmin.this, "Seleccione el sexo", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (fecha.isEmpty()) {
                    fechaNac.setError("Ingrese fecha de nacimiento");
                    fechaNac.requestFocus();
                    return;
                }

                if (mail.isEmpty()) {
                    email.setError("Ingrese el correo");
                    email.requestFocus();
                    return;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
                    email.setError("Correo no válido");
                    email.requestFocus();
                    return;
                }

                if (telefono.isEmpty()) {
                    tel.setError("Ingrese número de teléfono");
                    tel.requestFocus();
                    return;
                }

                if (telefono.length() < 8) {
                    tel.setError("Número muy corto");
                    tel.requestFocus();
                    return;
                }

                if (!telefono.matches("\\d+")) {
                    tel.setError("Solo números permitidos");
                    tel.requestFocus();
                    return;
                }


                cliente c = new cliente(
                        id, nom, ape1, ape2, sex, fecha, mail, telefono
                );

                dao.insertarCliente(c);

                lista.clear();
                lista.addAll(dao.verTodos());
                adapter.notifyDataSetChanged();

                Toast.makeText(MainAdmin.this, "Cliente agregado correctamente", Toast.LENGTH_SHORT).show();
                dialogo.dismiss();
            });


            cancelar.setOnClickListener(v3 -> dialogo.dismiss());
        });
    }

    // --- MÉTODO FILTRAR (debe estar fuera de onCreate()) ---
    private void filtrar(String texto) {
        ArrayList<cliente> filtrados = dao.buscarPorNombreCompleto(texto);

        lista.clear();
        lista.addAll(filtrados);

        adapter.notifyDataSetChanged();
    }
}
