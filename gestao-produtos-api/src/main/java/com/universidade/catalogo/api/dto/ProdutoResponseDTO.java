package com.universidade.catalogo.api.dto;

import com.universidade.catalogo.domain.model.Produto;
import com.universidade.catalogo.domain.model.TipoProduto;
import com.universidade.catalogo.domain.model.UnidadeMedida;

import java.math.BigDecimal;

public record ProdutoResponseDTO(

        Long id,
        String descricao,
        TipoProduto tipo,
        UnidadeMedida unidadeMedida,
        BigDecimal valor

) {

    public ProdutoResponseDTO(Produto produto) {
        this(
                produto.getId(),
                produto.getDescricao(),
                produto.getTipo(),
                produto.getUnidadeMedida(),
                produto.getValor()
        );
    }

}