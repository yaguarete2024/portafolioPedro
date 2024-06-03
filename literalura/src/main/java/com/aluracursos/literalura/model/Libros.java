package com.aluracursos.literalura.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "librostbd")
public class Libros {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    private String idiomas;
    private Double numeroDeDescargas;
    @ManyToOne
    private Autor autor;

    public Libros(){}

    public Libros(DatosLibros datosLibros, Autor autor){
        this.titulo = datosLibros.titulo();
        this.autor = autor;
        this.idiomas = datosLibros.idiomas().get(0);
        this.numeroDeDescargas = datosLibros.numeroDeDescargas();

    }

    @Override
    public String toString() {
        return "----------Libros----------" + '\n' +
                "TÍtulo = " + titulo + '\n' +
                 "Autor = " + autor.getNombre() + '\n' +
                "Idiomas = " + idiomas + '\n' +
                "NÚmero de descargas = " + numeroDeDescargas + '\n' +
                "------------------------";
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /*public List<DatosAutor> getAutor() {
        return autor;
    }

    public void setAutor(List<DatosAutor> autor) {
        this.autor = autor;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }*/

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }
}
