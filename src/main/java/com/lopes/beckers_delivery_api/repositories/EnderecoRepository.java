package com.lopes.beckers_delivery_api.repositories;

import com.lopes.beckers_delivery_api.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, UUID> {
}
