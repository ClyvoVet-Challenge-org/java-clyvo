package com.example.clyvo_challenge3.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.clyvo_challenge3.model.Consulta;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
	
	@Query(
			"SELECT c FROM Consulta c "
			+ "WHERE c.veterinarioClinica.veterinario.id = :idVeterinario")
	List<Consulta> findByVeterinario(@Param("idVeterinario") Long iVeterinario);
	
	@Query("SELECT COUNT(c) > 0 FROM Consulta c "
			+ "WHERE c.veterinarioClinica.veterinario.id = :idVeterinario "
			+ "AND c.animal.responsavel.id = :idResponsavel")
	boolean veterinarioAtendeResponsavel(@Param("idVeterinario") Long idVeterinario,
			@Param("idResponsavel") Long idResponsavel);
	
	

}
