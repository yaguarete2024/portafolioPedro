package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private int fechaDeNacimiento;
    private int fechaDefuncion;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libros> libros;

    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaDeNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaDefuncion = datosAutor.fechaDefuncion();

    }
    @Override
    public String toString(){
        String librosTitulos = libros.stream()
                .map(Libros::getTitulo)
                .collect(Collectors.joining(","));

        return  "Autor: " + nombre + "\n" +
                "Año de nacimiento: " + fechaDeNacimiento + "\n" +
                "Año de fallecimiento: " + fechaDefuncion + "\n" +
                "Libros: " + librosTitulos + "\n";

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(int fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public int getFechaDefuncion() {
        return fechaDefuncion;
    }

    public void setFechaDefuncion(int fechaDefuncion) {
        this.fechaDefuncion = fechaDefuncion;
    }

    public List<Libros> getLibros() {
        return libros;
    }

    public void setLibros(List<Libros> libros) {
        this.libros = libros;
    }


}
