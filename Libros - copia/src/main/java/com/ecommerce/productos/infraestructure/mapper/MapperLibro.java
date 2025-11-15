package com.ecommerce.productos.infraestructure.mapper;

import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro.LibroData;
import org.springframework.stereotype.Component;

@Component
public class MapperLibro {

    public LibroData toData(Libro libro) {
        LibroData libroData = new LibroData();
        libroData.setIdLibro(libro.getIdLibro());
        libroData.setTitulo(libro.getTitulo());
        libroData.setAutor(libro.getAutor());
        libroData.setIsbn(libro.getIsbn());
        libroData.setDescripcion(libro.getDescripcion());
        libroData.setPrecio(libro.getPrecio());
        libroData.setStock(libro.getStock());
        libroData.setEstado(libro.getEstado());
        libroData.setUsuarioId(libro.getUsuarioId());
        libroData.setDisponible(libro.getDisponible());
        return libroData;
    }

    public Libro toDomain(LibroData libroData) {
        Libro libro = new Libro();
        libro.setIdLibro(libroData.getIdLibro());
        libro.setTitulo(libroData.getTitulo());
        libro.setAutor(libroData.getAutor());
        libro.setIsbn(libroData.getIsbn());
        libro.setDescripcion(libroData.getDescripcion());
        libro.setPrecio(libroData.getPrecio());
        libro.setStock(libroData.getStock());
        libro.setEstado(libroData.getEstado());
        libro.setUsuarioId(libroData.getUsuarioId());
        libro.setDisponible(libroData.getDisponible());
        return libro;
    }
}