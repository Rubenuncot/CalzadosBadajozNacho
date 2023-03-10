package com.ruben.calzadosBadajoz.app.models.service;

import com.ruben.calzadosBadajoz.app.models.entity.Cliente;
import com.ruben.calzadosBadajoz.app.models.entity.Factura;
import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClienteService {

    public List<Cliente> findAll();

    public Page<Cliente> findAll(Pageable pageable);
    public Zapato findZapatoById(Long id);
    public List<Zapato> findByNombre(String term);
    public void save(Cliente cliente);

    public Cliente findOne(Long id);
    void saveFactura(Factura factura);

    public void delete(Long id);

}
