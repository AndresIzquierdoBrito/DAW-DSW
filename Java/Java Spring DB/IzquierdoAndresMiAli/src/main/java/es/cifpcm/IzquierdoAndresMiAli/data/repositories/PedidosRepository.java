package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidosRepository extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido> {

    @Query("select p from Pedido p where p.usuario = ?1")
    List<Pedido> findByUsuario(String usuario);
}