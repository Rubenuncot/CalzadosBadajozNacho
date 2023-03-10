package com.ruben.calzadosBadajoz.app.models.service;

import java.util.List;

import com.ruben.calzadosBadajoz.app.models.dao.IZapatoDao;
import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZapatoServiceImpl implements IZapatoService {

	@Autowired
	private IZapatoDao zapatoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Zapato> findAllZapatos() {
		// TODO Auto-generated method stub
		return zapatoDao.findAll();
	}

	@Override
	@Transactional
	public void save(Zapato zapato) {
		zapatoDao.save(zapato);
	}

	@Override
	public Zapato findOne(Long id) {
		return null;
	}

	@Override
	@Transactional
	public void delete(Long id) {
		zapatoDao.deleteById(id);
		
	}

	@Override
	public List<Zapato> findByNombre(String term) {
		return zapatoDao.findByNombre(term);
	}

	@Override
	public Page<Zapato> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return zapatoDao.findAll(pageable);
	}

	
}
