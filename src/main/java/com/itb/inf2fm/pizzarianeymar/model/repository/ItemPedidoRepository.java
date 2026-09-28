package com.itb.inf2fm.pizzarianeymar.model.repository;

import com.itb.inf2fm.pizzarianeymar.model.entity.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
