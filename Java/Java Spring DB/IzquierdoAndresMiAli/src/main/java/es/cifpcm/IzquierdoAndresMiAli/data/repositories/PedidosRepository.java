package es.cifpcm.IzquierdoAndresMiAli.data.repositories;

import es.cifpcm.IzquierdoAndresMiAli.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PedidosRepository extends JpaRepository<Pedido, Integer>, JpaSpecificationExecutor<Pedido> {

}