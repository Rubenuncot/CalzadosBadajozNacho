package com.ruben.calzadosBadajoz.app.models.dao;

import com.ruben.calzadosBadajoz.app.models.entity.Cliente;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long> {

    List<Cliente> findAll();

    Optional<Cliente> findById(Long id);

    void deleteById(Long id);

    void save(Cliente cliente);
}