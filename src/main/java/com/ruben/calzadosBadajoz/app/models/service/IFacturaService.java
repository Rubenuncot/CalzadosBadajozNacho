package com.ruben.calzadosBadajoz.app.models.service;

import com.ruben.calzadosBadajoz.app.models.entity.Factura;

import java.util.List;

public interface IFacturaService {
    public void delete(Long id);
    public void save(Factura factura);
    public List<Factura> findAll();
}
