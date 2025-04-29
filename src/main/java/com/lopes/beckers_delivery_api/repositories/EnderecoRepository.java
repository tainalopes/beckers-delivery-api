package com.lopes.beckers_delivery_api.repositories;

import com.lopes.beckers_delivery_api.models.EnderecoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<EnderecoModel, Long> {
}
