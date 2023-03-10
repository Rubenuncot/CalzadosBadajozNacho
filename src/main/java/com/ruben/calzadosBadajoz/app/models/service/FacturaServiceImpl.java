package com.ruben.calzadosBadajoz.app.models.service;

import com.ruben.calzadosBadajoz.app.models.dao.IFacturaDao;
import com.ruben.calzadosBadajoz.app.models.entity.Cliente;
import com.ruben.calzadosBadajoz.app.models.entity.Factura;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements IFacturaService{

    IFacturaDao iFacturaDao;

    @Override
    public void delete(Long id) {
        iFacturaDao.deleteById(id);
    }

    @Override
    public void save(Factura factura) {
        iFacturaDao.save(factura);
    }

    @Override
    public List<Factura> findAll() {
        return iFacturaDao.findAll();
    }
}
