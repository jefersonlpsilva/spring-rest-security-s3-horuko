package app.dinamismo.restBase.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import app.dinamismo.restBase.domain.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
