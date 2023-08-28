package com.example.api.Controller;

import com.example.api.Model.Produto;
import com.example.api.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    private final ProdutoRepository produtoRepository;

    @Autowired
    public ProdutoController(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @GetMapping("/selecionar")
    public List<Produto> listarProduto(){
        return produtoRepository.findAll();
    }

    @PostMapping("/inserir")
    public ResponseEntity<String> inserirProduto(@RequestBody Produto produto){
        produtoRepository.save(produto);
        return ResponseEntity.ok("Produto Inserido");
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<String> excluirProduto(@PathVariable Long id){
        produtoRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.CREATED).body("Produto Inserido");
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<String> atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado){
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()){
            Produto produto = produtoExistente.get();
            produto.setNome(produtoAtualizado.getNome());
            produto.setDescricao(produtoAtualizado.getDescricao());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setQuantidadeEstoque(produto.getQuantidadeEstoque());
            produtoRepository.save(produto);
            return ResponseEntity.ok("Produto Atualizado");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/selecionar/{id}")
    public ResponseEntity<Produto> gerProdutoById(@PathVariable Long id){
        if (produtoRepository.existsById(id)){
            Produto produto = produtoRepository.findById(id).orElse(null);
            return ResponseEntity.ok(produto);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
