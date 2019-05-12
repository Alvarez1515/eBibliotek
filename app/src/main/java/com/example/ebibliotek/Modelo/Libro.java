package com.example.ebibliotek.Modelo;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class Libro implements Parcelable {
    private String isbn, nombre, autor, editorial;
    private int publicacion, edicion, id;
    private Uri imagen;

    public Libro(int id, String isbn, String nombre, String autor, String editorial, int publicacion, int edicion, Uri imagen) {
        this.id = id;
        this.isbn = isbn;
        this.nombre = nombre;
        this.autor = autor;
        this.editorial = editorial;
        this.publicacion = publicacion;
        this.edicion = edicion;
        this.imagen = imagen;
    }
    public Libro() {
        this.id = 0;
        this.isbn = "";
        this.nombre = "";
        this.autor = "";
        this.editorial = "";
        this.publicacion = 0;
        this.edicion = 0;
        this.imagen = null;
    }

    protected Libro(Parcel in) {
        isbn = in.readString();
        nombre = in.readString();
        autor = in.readString();
        editorial = in.readString();
        id = in.readInt();
        publicacion = in.readInt();
        edicion = in.readInt();
        imagen = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<Libro> CREATOR = new Creator<Libro>() {
        @Override
        public Libro createFromParcel(Parcel in) {
            return new Libro(in);
        }

        @Override
        public Libro[] newArray(int size) {
            return new Libro[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(int publicacion) {
        this.publicacion = publicacion;
    }

    public int getEdicion() {
        return edicion;
    }

    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "id=" + id +
                "isbn=" + isbn +
                ", nombre=" + nombre  +
                ", autor=" + autor +
                ", editorial=" + editorial  +
                ", publicacion=" + publicacion +
                ", edicion=" + edicion +
                ", imagen=" + imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(isbn);
        dest.writeString(nombre);
        dest.writeString(autor);
        dest.writeString(editorial);
        dest.writeInt(id);
        dest.writeInt(publicacion);
        dest.writeInt(edicion);
        dest.writeParcelable(imagen, flags);
    }
}//Fin de la clase

