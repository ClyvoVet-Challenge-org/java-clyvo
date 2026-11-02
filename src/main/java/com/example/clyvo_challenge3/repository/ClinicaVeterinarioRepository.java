package com.example.clyvo_challenge3.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.clyvo_challenge3.model.ClinicaVeterinario;

@Repository
public interface ClinicaVeterinarioRepository extends JpaRepository<ClinicaVeterinario, Long>{
	
	Optional<ClinicaVeterinario> findByClinicaAndVeterinario(Long idClinica,Long idVeterinario);

}
