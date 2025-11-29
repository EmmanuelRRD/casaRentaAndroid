package crud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class daoCliente extends SQLiteOpenHelper {

    private static final String nombreBD = "BD_casarentas";
    private static final int versionBD = 1;

    public daoCliente(Context context) {
        super(context, nombreBD, null, versionBD);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Cliente (" +
                "idCliente VARCHAR(6) PRIMARY KEY, " +
                "Nombre VARCHAR(20) NOT NULL, " +
                "PrimerAp VARCHAR(20) NOT NULL, " +
                "SegundoAp VARCHAR(20), " +
                "Sexo CHAR(1) NOT NULL, " +
                "FechaNac DATE NOT NULL, " +
                "Correo VARCHAR(45) NOT NULL, " +
                "NumTel VARCHAR(10) NOT NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // eliminar y recrear tablas
    }

    public boolean editar(cliente c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("Nombre", c.getNombre());
        cv.put("PrimerAp", c.getPrimerAp());
        cv.put("SegundoAp", c.getSegundoAp());
        cv.put("Sexo", c.getSexo());
        cv.put("FechaNac", c.getFechaNac());
        cv.put("Correo", c.getCorreo());
        cv.put("NumTel", c.getNumTel());

        int filasAfectadas = db.update("Cliente",cv,"idCliente = ?",new String[]{c.getIdCliente()});

        return filasAfectadas > 0; //true si actualizó algo
    }


    public boolean insertarCliente(cliente c) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("idCliente", c.getIdCliente());
        cv.put("Nombre", c.getNombre());
        cv.put("PrimerAp", c.getPrimerAp());
        cv.put("SegundoAp", c.getSegundoAp());
        cv.put("Sexo", c.getSexo());
        cv.put("FechaNac", c.getFechaNac());
        cv.put("Correo", c.getCorreo());
        cv.put("NumTel", c.getNumTel());

        long resultado = db.insert("Cliente", null, cv);
        return resultado > 0;
    }

    public ArrayList<cliente> verTodos() {
        ArrayList<cliente> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM Cliente", null);

        if (c.moveToFirst()) {
            do {
                cliente cli = new cliente(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7)
                );
                lista.add(cli);
            } while (c.moveToNext());
        }

        c.close();
        return lista;
    }

    public ArrayList<cliente> buscarPorNombreCompleto(String texto) {
        ArrayList<cliente> lista = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Texto para LIKE
        String filtro = "%" + texto + "%";

        // CONCAT permite buscar el nombre completo junto
        Cursor c = db.rawQuery(
                "SELECT * FROM Cliente WHERE " +
                        "Nombre || ' ' || PrimerAp || ' ' || SegundoAp LIKE ?",//asi se concatena pa realizar la consulta de nombre completo
                new String[]{ filtro }
        );

        if (c.moveToFirst()) {
            do {
                cliente cli = new cliente(
                        c.getString(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6),
                        c.getString(7)
                );
                lista.add(cli);
            } while (c.moveToNext());
        }

        c.close();
        return lista;
    }


    public boolean eliminar(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int filasAfectadas = db.delete("Cliente", "idCliente=?", new String[]{id});
        return filasAfectadas > 0;
    }



}
