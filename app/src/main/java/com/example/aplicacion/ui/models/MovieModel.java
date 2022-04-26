package com.example.aplicacion.ui.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieModel implements Parcelable {

    // Esta clase se utiliza para las películas, por tanto debe tener
    // los mismos atributos que recibimos por el json


    private String titulo;
    private String portada;
    private String fecha_publicacion;
    private int id;
    private float valoracion;
    private String descripcion;


    // Constructor
    public MovieModel(String titulo, String portada, String fecha_publicacion, int id, float valoracion, String descripcion) {
        this.titulo = titulo;
        this.portada = portada;
        this.fecha_publicacion = fecha_publicacion;
        this.id = id;
        this.valoracion = valoracion;
        this.descripcion = descripcion;
    }


    // Getters


    protected MovieModel(Parcel in) {
        titulo = in.readString();
        portada = in.readString();
        fecha_publicacion = in.readString();
        id = in.readInt();
        valoracion = in.readFloat();
        descripcion = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getTitulo() {
        return titulo;
    }

    public String getPortada() {
        return portada;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public int getId() {
        return id;
    }

    public float getValoracion() {
        return valoracion;
    }

    public String getDescripcion() {
        return descripcion;
    }


    @Override
    public int describeContents() {
         return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(titulo);
        parcel.writeString(portada);
        parcel.writeString(fecha_publicacion);
        parcel.writeInt(id);
        parcel.writeFloat(valoracion);
        parcel.writeString(descripcion);
    }
}
