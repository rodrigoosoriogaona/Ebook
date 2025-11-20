package com.ecommerce.libros.Infraestructure.mapper;

import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Libro.LibroData;
import org.springframework.stereotype.Component;

@Component
public class MapperLibro {

    public LibroData toData(Libro domain) {
        if (domain == null) return null;

        LibroData data = new LibroData();
        data.setIdLibro(domain.getIdLibro());
        data.setUsuarioId(domain.getUsuarioId());
        data.setTitulo(domain.getTitulo());
        data.setAutor(domain.getAutor());
        data.setGenero(domain.getGenero());
        data.setSinopsis(domain.getSinopsis());
        data.setEstadoFisico(domain.getEstadoFisico());
        data.setPrecio(domain.getPrecio());
        data.setDisponible(domain.getDisponible());
        data.setEnVenta(domain.getEnVenta());
        data.setEnIntercambio(domain.getEnIntercambio());
        data.setFechaCreacion(domain.getFechaCreacion());
        data.setFechaActualizacion(domain.getFechaActualizacion());
        return data;
    }

    public Libro toDomain(LibroData data) {
        if (data == null) return null;

        Libro domain = new Libro();
        domain.setIdLibro(data.getIdLibro());
        domain.setUsuarioId(data.getUsuarioId());
        domain.setTitulo(data.getTitulo());
        domain.setAutor(data.getAutor());
        domain.setGenero(data.getGenero());
        domain.setSinopsis(data.getSinopsis());
        domain.setEstadoFisico(data.getEstadoFisico());
        domain.setPrecio(data.getPrecio());
        domain.setDisponible(data.getDisponible());
        domain.setEnVenta(data.getEnVenta());
        domain.setEnIntercambio(data.getEnIntercambio());
        domain.setFechaCreacion(data.getFechaCreacion());
        domain.setFechaActualizacion(data.getFechaActualizacion());
        return domain;
    }
}