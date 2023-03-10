package com.ruben.calzadosBadajoz.app.models.dao;

import com.ruben.calzadosBadajoz.app.models.entity.Factura;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IFacturaDao extends CrudRepository<Factura, Long> {
    void deleteById(Long id);
    List<Factura> findAll();
}
