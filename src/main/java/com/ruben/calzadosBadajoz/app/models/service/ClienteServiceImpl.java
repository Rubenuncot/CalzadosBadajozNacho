package com.ruben.calzadosBadajoz.app.models.service;

import com.ruben.calzadosBadajoz.app.models.dao.IClienteDao;
import com.ruben.calzadosBadajoz.app.models.dao.IFacturaDao;
import com.ruben.calzadosBadajoz.app.models.dao.IZapatoDao;
import com.ruben.calzadosBadajoz.app.models.entity.Cliente;
import com.ruben.calzadosBadajoz.app.models.entity.Factura;
import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements IClienteService {

    @Autowired
    private IClienteDao clienteDao;
    private IZapatoDao zapatoDao;
    private IFacturaDao facturaDao;
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return clienteDao.findAll();
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clienteDao.save(cliente);

    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return (clienteDao.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        clienteDao.deleteById(id);

    }
    @Override
    public Page<Cliente> findAll(Pageable pageable) {
        return clienteDao.findAll(pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Zapato findZapatoById(Long id) {
        return  zapatoDao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Zapato> findByNombre(String term) {

        return zapatoDao.findByNombreLikeIgnoreCase("%"+term+"%");
    }

    @Override
    public void saveFactura(Factura factura) {
        facturaDao.save(factura);
    }
}

