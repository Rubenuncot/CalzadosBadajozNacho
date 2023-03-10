package com.ruben.calzadosBadajoz.app.models.dao;

import java.util.List;
import java.util.Optional;

import com.ruben.calzadosBadajoz.app.models.entity.Zapato;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IZapatoDao extends PagingAndSortingRepository<Zapato, Long>, CrudRepository<Zapato, Long> {

	List<Zapato> findAll();
	@Query("select p from Zapato p where p.nombre like %?1%")
	public List<Zapato> findByNombreLikeIgnoreCase(String term);
	void deleteById(Long id);

	List<Zapato> findByNombre(String term);
}
