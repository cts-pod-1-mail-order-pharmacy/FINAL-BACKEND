package com.cognizant.drugs.Service;

import java.util.Optional;

import com.cognizant.drugs.Model.Drugs;

public interface DrugsDAO {

	public Optional<Drugs> searchDrugsByID(Long drugId);

	public Optional<Drugs> searchDrugsByName(String drugname);

	public Drugs findByLocation(Long drugId, String location);

	public boolean isAvailable(String drugName, String location);

}
