package com.universidade.catalogo.domain.repository;

import com.universidade.catalogo.domain.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // boolean existsByDescricao(String descricao);

}