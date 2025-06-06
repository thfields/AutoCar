package com.ifrn.autocar.repositories;

import com.ifrn.autocar.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Long> {
    public Usuario findByNome(String nome);
}
