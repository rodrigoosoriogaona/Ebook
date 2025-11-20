package com.ecommerce.productos.infraestructure.driver_adapters.jpa_repository.Intercambio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfertaIntercambioDataJpaRepository extends JpaRepository<OfertaIntercambioData, Long> {


    List<OfertaIntercambioData> findByEstado(String estado);

    List<OfertaIntercambioData> findByPublicacionId(Long publicacionId);

    List<OfertaIntercambioData> findByUsuarioOferenteId(Long usuarioOferenteId);

    List<OfertaIntercambioData> findByLibroOfrecidoId(Long libroOfrecidoId);

    Optional<OfertaIntercambioData> findByIdOferta(Long idOferta);

    // Método para actualizar estado masivo
    @Modifying
    @Query("UPDATE OfertaIntercambioData o SET o.estado = :estado WHERE o.idOferta IN :ids")
    void updateEstadoByIds(@Param("ids") List<Long> ids, @Param("estado") String estado);

    // Método para buscar ofertas pendientes por publicación
    @Query("SELECT o FROM OfertaIntercambioData o WHERE o.publicacionId = :publicacionId AND o.estado = 'PENDIENTE'")
    List<OfertaIntercambioData> findOfertasPendientesByPublicacion(@Param("publicacionId") Long publicacionId);

    // Método para buscar ofertas aceptadas por publicación
    @Query("SELECT o FROM OfertaIntercambioData o WHERE o.publicacionId = :publicacionId AND o.estado = 'ACEPTADA'")
    List<OfertaIntercambioData> findOfertasAceptadasByPublicacion(@Param("publicacionId") Long publicacionId);
}