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

    public cliente verUno(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Cliente WHERE idCliente = ?", new String[]{id});

        if (c.moveToFirst()) {
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
            c.close();
            return cli;
        }

        c.close();
        return null;
    }
}
