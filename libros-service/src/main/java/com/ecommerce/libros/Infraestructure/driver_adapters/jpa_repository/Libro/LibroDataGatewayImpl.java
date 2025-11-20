package com.ecommerce.libros.Infraestructure.driver_adapters.jpa_repository.Libro;

import com.ecommerce.libros.domain.model.Libro;
import com.ecommerce.libros.domain.model.Gateway.LibroGateway;
import com.ecommerce.libros.Infraestructure.mapper.MapperLibro;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class LibroDataGatewayImpl implements LibroGateway {

    private final LibroDataJpaRepository repository;
    private final MapperLibro mapper;

    @Override
    public Libro guardar(Libro libro) {
        LibroData entity = mapper.toData(libro);
        return mapper.toDomain(repository.save(entity));
    }

    @Override
    public Optional<Libro> buscarPorId(Long idLibro) {
        return repository.findById(idLibro)
                .map(mapper::toDomain);
    }

    @Override
    public List<Libro> buscarPorUsuarioId(Long usuarioId) {
        return repository.findByUsuarioId(usuarioId).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> buscarPorDisponibilidad(Boolean disponible) {
        return repository.findByDisponible(disponible).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminar(Long libroId) {
        repository.deleteById(libroId);
    }
}