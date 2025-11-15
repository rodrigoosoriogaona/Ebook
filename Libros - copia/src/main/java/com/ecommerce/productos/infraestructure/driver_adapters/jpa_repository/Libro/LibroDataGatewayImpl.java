package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro;

import com.ecommerce.productos.domain.model.Libro;
import com.ecommerce.productos.domain.model.gateway.LibroGateway;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro.LibroData;
import com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Libro.LibroDataJpaRepository;
import com.ecommerce.productos.infraestructure.mapper.MapperLibro;
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
    public List<Libro> buscarPorTitulo(String titulo) {
        return repository.findByTituloContainingIgnoreCase(titulo).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Libro> buscarPorAutor(String autor) {
        return repository.findByAutorContainingIgnoreCase(autor).stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarLibro(Long idLibro) {
        repository.deleteById(idLibro);
    }

    @Override
    public boolean existeLibro(Long idLibro) {
        return repository.existsById(idLibro);
    }
}