package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibrosRepository extends JpaRepository<Libros,Long > {
    Libros findByTitulo(String titulo);
    List<Libros> findAll();

    @Query("SELECT l FROM Libros l WHERE l.idiomas = LOWER(:idioma)")
    List<Libros> buscarLibrosPorIdioma(String idioma);
}
