package com.lopes.beckers_delivery_api.repositories;

import com.lopes.beckers_delivery_api.domain.Usuario;
import com.lopes.beckers_delivery_api.models.UsuarioModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, UUID> {
    Optional<UsuarioModel> findByCpf(String cpf);

    Optional<UsuarioModel> findByEmail(String email);

    Page<UsuarioModel> findAll(Pageable pageable);
}
