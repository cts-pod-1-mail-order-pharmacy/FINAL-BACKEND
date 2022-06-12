package com.cognizant.drugs.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.cognizant.drugs.Model.Drugs;

@Repository
public interface DrugsRepository extends CrudRepository<Drugs, Long>{

	Optional<Drugs> findByName(String name);
	
}
