package com.universidade.catalogo.api.controller;

import com.universidade.catalogo.api.dto.ProdutoRequestDTO;
import com.universidade.catalogo.api.dto.ProdutoResponseDTO;
import com.universidade.catalogo.domain.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<ProdutoResponseDTO> lista = produtoService.listarTodos();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(
            @PathVariable Long id
    ) {
        ProdutoResponseDTO produto = produtoService.buscarPorId(id);

        return ResponseEntity.ok(produto);
    }

    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody @Valid ProdutoRequestDTO requestDTO,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        ProdutoResponseDTO produtoCriado = produtoService.salvar(requestDTO);

        URI uri = uriComponentsBuilder
                .path("/api/produtos/{id}")
                .buildAndExpand(produtoCriado.id())
                .toUri();

        return ResponseEntity.created(uri).body(produtoCriado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoRequestDTO requestDTO
    ) {
        ProdutoResponseDTO produtoAtualizado =
                produtoService.atualizar(id, requestDTO);

        return ResponseEntity.ok(produtoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        produtoService.excluir(id);

        return ResponseEntity.noContent().build();
    }

}