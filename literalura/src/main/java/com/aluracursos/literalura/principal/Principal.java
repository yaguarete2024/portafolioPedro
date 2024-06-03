package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibrosRepository;
import com.aluracursos.literalura.service.ConsumoAPI;
import com.aluracursos.literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);
    private LibrosRepository libroRepo;
    private AutorRepository autorRepo;

    public Principal(LibrosRepository libroRepository, AutorRepository autorRepository ) {
        this.libroRepo = libroRepository;
        this.autorRepo = autorRepository;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    --------------------------
                    Elija la opción a través de su número:
                    1.- Buscar en Api(/Registrar en BD) Libro por título
                    2.- Listar libros registrados en BD
                    3.- Listar autores registrados en BD
                    4.- Listar autores vivo en un determinado año registrado en BD
                    5.- Listar libros por idioma registrados en BD
                    0.- Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();
            switch (opcion) {
                case 1:
                    busquedaLibrosTitulo();
                    break;
                case 2:
                    muestraLibrosBuscados();
                    break;
                case 3:
                    listarAutoresRegistradosBD();
                    break;
                case 4:
                    System.out.println("Introduce un año para listar a los autores vivos en ese año");
                    var year = teclado.nextInt();
                    buscarAutoresVivos(year);
                    break;
                case 5:
                    System.out.println("""
                        Ingrese el idioma para buscar los libros:
                        es - español
                        en - inglés
                        fr - francés
                        pt - portugués
                        """);
                    var idioma = teclado.next();
                    teclado.nextLine();
                    listarLibrosPorIdioma(idioma);
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación");
                    break;
                default:
                    System.out.println("Opción invalida");
            }
        }
    }



    private Datos buscarLibroApi(){
        System.out.println("Ingresa el nombre del libro que desea buscar");
        var buscarLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + buscarLibro.replace(" ","+"));
        Datos librosEncontradosApi = conversor.obtenerDatos(json, Datos.class);

        //Optional-> se usa si encuentra o no los datos
       /* Optional<DatosLibros> libroBuscado = librosEncontradosApi.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(buscarLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro Encontrado ");
            //System.out.println(libroBuscado.get());
            //return datosBusqueda;

        }else {
            System.out.println("Libro no encontrado");
        }*/
        return librosEncontradosApi;
    }
    private void busquedaLibrosTitulo() {
        Datos librosEncontradosApi = buscarLibroApi();
        if (!librosEncontradosApi.resultados().isEmpty()){
            DatosLibros datosLibros = librosEncontradosApi.resultados().get(0);
            DatosAutor datosAutor = datosLibros.autor().get(0);
            Libros libroDb = libroRepo.findByTitulo(datosLibros.titulo());
            if(libroDb != null){
                System.out.println("\n--- LIBRO ENCONTRADO EN LA BD ---");
                System.out.println(libroDb);
            }
            else {
                Autor autorDb = autorRepo.findByNombre(datosAutor.nombre());
                if (autorDb == null){
                    Autor autor = new Autor(datosAutor);
                    autorRepo.save(autor);
                    Libros libro = new Libros(datosLibros, autor);
                    libroRepo.save(libro);
                    System.out.println("\n --- LIBRO ENCONTRADO EN LA API1 ---");
                    System.out.println(libro);

                }
                else{
                    Libros libro = new Libros(datosLibros, autorDb);
                    libroRepo.save(libro);
                    System.out.println("\n --- LIBRO ENCONTRADO EN LA API2 ---");
                    System.out.println(libro);
                }
            }

        }
        else{
            System.out.println("---- LIBRO NO ENCONTRADO ------");
        }
        //Libros datlibro = new Libros(datosl);
        //repositorio.save(datlibro);
        System.out.println(librosEncontradosApi);
    }

    private void muestraLibrosBuscados() {
        List<Libros> librosBD = libroRepo.findAll();
        System.out.println("\n --- LISTA DE LIBROS REGISTRADOS EN LA BD ---");
        if (librosBD.isEmpty()){
            System.out.println("Lista de libros vacia");
        }
        else{
            librosBD.stream()
                    .sorted((Comparator.comparing(Libros::getId)))
                    .forEach(System.out::println);
            System.out.println(librosBD.size() + "libro(s) encontrado(s) en total");
        }
    }
    private void listarAutoresRegistradosBD(){
        List<Autor> autoresBD = autorRepo.findAll();
        System.out.println("\n --- LIST DE AUTORES REGISTRADOS ---");
        if (autoresBD.isEmpty()){
            System.out.println("Lista de autores vacía");
        }
        else{
            autoresBD.stream()
                    .sorted(Comparator.comparing(Autor::getNombre))
                    .forEach(System.out::println);
            System.out.println(autoresBD.size() + "autor(es) encontrado(s) en total");
        }
    }
    private void buscarAutoresVivos(int year){
        List<Autor> busquedaAutoresVivos = autorRepo.buscarAutorVivo(year);
        System.out.println("\n ---AUTORES VIVOS EN EL AÑO " + year + "---");
        if (busquedaAutoresVivos.isEmpty()){
            System.out.println("No se encontraron autores vivos de la BD en " + year + "\n");
        }
        else{
            long cantidadAutores = busquedaAutoresVivos.stream()
                    .filter(a-> a.getFechaDeNacimiento() != 0 && (year - a.getFechaDeNacimiento()) < 100)
                    .peek(a -> System.out.println(a.getNombre() + " (" + a.getFechaDeNacimiento() + " - " + a.getFechaDefuncion() + ")"))
                    .count();
            System.out.println("\n" + cantidadAutores + "autor(es) encontrados en total" );

        }
    }
    private void listarLibrosPorIdioma(String idioma){
        List<Libros> librosPorIdioma = libroRepo.buscarLibrosPorIdioma(idioma);
        System.out.println("\n --- LISTA DE LIBROS EN IDIOMA " + idioma.toUpperCase() + "---");
        if (librosPorIdioma.isEmpty()){
            System.out.println("No se encontrron libros en este idioma: (\n");
        }
        else{
            librosPorIdioma.stream()
                    .sorted((Comparator.comparing(Libros::getId)))
                    .forEach(System.out::println);
            System.out.println(librosPorIdioma.size() + " libros(s) encontrado(s) en total");
        }
    }
}
