package com.example.ebibliotek;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOutOfMemoryException;
import android.graphics.Bitmap;
import android.net.Uri;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ebibliotek.Modelo.Libro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class act_AgregarLibro extends AppCompatActivity {

    private static SQLiteDatabase db;
    private static final String NOMBRE_DB = "LibroSQLITE";
    public final String TABLA_BIBLIOTECA = "libros";

    EditText txtIsbn;
    EditText txtNombre;
    EditText txtAutor;
    EditText txtEditorial;
    EditText txtPublicacion;
    EditText txtEdicion;

    Button btnAgregarLib;
    ImageView imgLibro;
    Uri imagen;

    Libro libro;


    String isbn, nombre, autor,editorial, id;
    int publicacion, edicion;

    private final int GALERIA_IMAGENES=1, CAMARA=2;

    public static final String tbLibro = "CREATE TABLE IF NOT EXISTS libros( "+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+"isbn STRING NOT NULL,"+"nombre STRING NOT NULL, "+ "autor STRING NOT NULL,"+ "editorial STRING NOT NULL,"+"publicacion INT NOT NULL,"+"edicion INT NOT NULL,"+"imagen STRING NOT NULL);";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_agregar_libro);

        abrirDatabase();

        txtIsbn=findViewById(R.id.txtIsbn);
        txtNombre=findViewById(R.id.txtNombre);
        txtAutor=findViewById(R.id.txtAutor);
        txtEditorial=findViewById(R.id.txtEditorial);
        txtPublicacion=findViewById(R.id.txtPublicacion);
        txtEdicion=findViewById(R.id.txtEdicion);

        btnAgregarLib=findViewById(R.id.btnAgregarLib);
        imgLibro = findViewById(R.id.imgLibro);




        btnAgregarLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isbn=txtIsbn.getText().toString();
                nombre=txtNombre.getText().toString();
                autor=txtAutor.getText().toString();
                editorial=txtEditorial.getText().toString();
                publicacion=Integer.parseInt(txtPublicacion.getText().toString());
                edicion=Integer.parseInt(txtEdicion.getText().toString());

                if(!TextUtils.isEmpty(isbn)&&!TextUtils.isEmpty(nombre)&&!TextUtils.isEmpty(autor)&&!TextUtils.isEmpty(editorial)&&!TextUtils.isEmpty(txtPublicacion.getText().toString())&&!TextUtils.isEmpty(txtEdicion.getText().toString())) {

                    String isbnString = isbn;
                    String nombreString = nombre;
                    String autorString = autor;
                    String editorialString = editorial;
                    int publicacionInt = publicacion;
                    int edicionInt = edicion;
                    Uri imagenUri = imagen;

                    if (addLibro(isbnString, nombreString, autorString, editorialString, publicacionInt, edicionInt, imagenUri)) {
                        Toast.makeText(getApplicationContext(), "Libro agregado correctamente", Toast.LENGTH_SHORT).show();
                        limpiar();

                    } else {
                        Toast.makeText(getApplicationContext(), "Error al agregar en la base de datos", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imgLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alerDialog = new AlertDialog.Builder(act_AgregarLibro.this);
                alerDialog.setTitle("Seleccion de imagen de libro");
                alerDialog.setMessage("Que desea utilizar");
                alerDialog.setIcon(android.R.drawable.ic_dialog_alert);
                alerDialog.setCancelable(true);

                alerDialog.setPositiveButton("Galeria", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent,"Selecciona foto"), GALERIA_IMAGENES);
                    }
                });
                alerDialog.setNegativeButton("camara", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMARA);
                    }
                });
                alerDialog.show();
            }
        });

    }//Fin del onCreate

    public void onActivityResult(int rqCode, int resCode, Intent data){
        if(resCode == RESULT_OK){
            switch (rqCode){
                case GALERIA_IMAGENES:
                    imagen = data.getData();
                    imgLibro.setImageURI(imagen);
                    Toast.makeText(getApplicationContext(), "Imagen cargada correctamente", Toast.LENGTH_SHORT).show();
                    break;
                case CAMARA:
                    if(data != null){
                        Bitmap thumbail = (Bitmap)data.getExtras().get("data");
                        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                        thumbail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
                        FileOutputStream fo;
                        try{
                            fo= new FileOutputStream(destination);
                        }catch (FileNotFoundException ex){
                            ex.printStackTrace();
                        }catch (IOException io){
                            io.printStackTrace();
                        }
                        imgLibro.setImageBitmap(thumbail);
                        imagen = data.getData();
                        Toast.makeText(getApplicationContext(), "Exito al cargar la foto de la camara", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    public void abrirDatabase(){
        try{
            db= openOrCreateDatabase(NOMBRE_DB,MODE_PRIVATE, null);
            db.execSQL(tbLibro);
        }catch (SQLiteOutOfMemoryException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error al crear la base de datos", Toast.LENGTH_SHORT).show();
        }
    }// fin del metodo

    private boolean addLibro(String isbnString, String nombreString, String autorString, String editorialString, int publicacionInt, int edicionInt, Uri imagen){

        ContentValues content = new ContentValues();
        content.put("isbn", isbnString);
        content.put("nombre", nombreString);
        content.put("autor", autorString);
        content.put("editorial", editorialString);
        content.put("publicacion", publicacionInt);
        content.put("edicion", edicionInt);
        content.put("imagen", imagen+"");

        return db.insert(TABLA_BIBLIOTECA, null, content)>0;
    }//fin del metodo addlibros

    public void limpiar(){
        txtIsbn.setText("");
        txtNombre.setText("");
        txtAutor.setText("");
        txtEditorial.setText("");
        txtPublicacion.setText("");
        txtEdicion.setText("");
        imgLibro.setImageURI(null);
    }



}//Fin de la clase
