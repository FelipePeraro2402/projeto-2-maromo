package com.universidade.catalogo.domain.service;

import com.universidade.catalogo.api.dto.ProdutoRequestDTO;
import com.universidade.catalogo.api.dto.ProdutoResponseDTO;
import com.universidade.catalogo.domain.exception.RecursoNaoEncontradoException;
import com.universidade.catalogo.domain.model.Produto;
import com.universidade.catalogo.domain.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public ProdutoService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> listarTodos() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = buscarEntidadePorId(id);
        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto) {
        Produto produto = new Produto();

        copiarDtoParaEntidade(dto, produto);

        Produto produtoSalvo = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produtoSalvo);
    }

    @Transactional
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto produto = buscarEntidadePorId(id);

        copiarDtoParaEntidade(dto, produto);

        Produto produtoAtualizado = produtoRepository.save(produto);

        return new ProdutoResponseDTO(produtoAtualizado);
    }

    @Transactional
    public void excluir(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new RecursoNaoEncontradoException(
                    "Produto não encontrado com o ID: " + id
            );
        }

        produtoRepository.deleteById(id);
    }

    private Produto buscarEntidadePorId(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException(
                        "Produto não encontrado com o ID: " + id
                ));
    }

    private void copiarDtoParaEntidade(
            ProdutoRequestDTO dto,
            Produto produto
    ) {
        produto.setDescricao(dto.descricao());
        produto.setTipo(dto.tipo());
        produto.setUnidadeMedida(dto.unidadeMedida());
        produto.setValor(dto.valor());
    }

}