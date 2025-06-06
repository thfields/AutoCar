package com.ifrn.autocar.controllers;

import com.ifrn.autocar.models.Usuario;
import com.ifrn.autocar.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    // Obter todos os usuários
    @GetMapping("/usuarios")
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obter um usuário por ID
    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if(usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Login de usuário
    @PostMapping("/login")
    public boolean login(@RequestBody String data) {
        System.out.println("Login: " + data);
        Usuario usuario = usuarioRepository.findByNome(data.split(",")[0]);

        if(usuario != null) {
            System.out.println("user found: " + usuario.getSenha());
            if(usuario.getSenha().equals(data.split(",")[1])) {
                System.out.println("Login OK");
                return true;
            }
        }

        System.out.println("Login error");
        return false;
    }

    // Adicionar novo usuário
    @PostMapping("/usuario")
    public ResponseEntity<Usuario> addUsuario(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioRepository.save(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    // Atualizar usuário existente
    @PutMapping("/usuario/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    usuario.setNome(usuarioAtualizado.getNome());
                    usuario.setEmail(usuarioAtualizado.getEmail());
                    usuario.setSenha(usuarioAtualizado.getSenha());
                    // Atualize outros campos conforme necessário

                    Usuario usuarioAtualizadoSalvo = usuarioRepository.save(usuario);
                    return ResponseEntity.ok(usuarioAtualizadoSalvo);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Deletar usuário
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        if(usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}