package com.example.casarenta;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
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
        adapter = new Adaptador(lista,dao,this);
        RecyclerView list = findViewById(R.id.lista_recycler);
        ImageButton agregar = findViewById(R.id.btn_agregar);

        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Gialog de agregar
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

                guardar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            //Vamos a pasar como parametros lo recopilado del dialogo.xml
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

                            dao.insertarCliente(c);
                            adapter.notifyDataSetChanged();
                            dao.verTodos();
                            dialogo.dismiss();


                        }catch (Exception e){
                            Toast.makeText(getApplication(),"Error",Toast.LENGTH_SHORT).show();
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


    }

}
