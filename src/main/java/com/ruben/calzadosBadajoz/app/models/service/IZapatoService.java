package com.ruben.calzadosBadajoz.app.models.service;

import java.util.List;

import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IZapatoService {
	
	public List<Zapato> findAllZapatos();
	
	public Page<Zapato> findAll(Pageable pageable);

	public void save(Zapato zapato);
	
	public Zapato findOne(Long id);
	
	public void delete(Long id);

	public List<Zapato> findByNombre(String term);
}
