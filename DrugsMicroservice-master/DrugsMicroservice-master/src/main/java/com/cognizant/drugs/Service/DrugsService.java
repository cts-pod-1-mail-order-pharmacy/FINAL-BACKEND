package com.cognizant.drugs.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.drugs.Model.DrugDetails;
import com.cognizant.drugs.Model.Drugs;
import com.cognizant.drugs.Repository.DrugsRepository;

@Service
public class DrugsService implements DrugsDAO {
	@Autowired DrugsRepository drugsRepository;
	public DrugsService(DrugsRepository repo) {
	}
	public Optional<Drugs> searchDrugsByID(Long drugId) {
		return drugsRepository.findById(drugId);
	}
	public Optional<Drugs> searchDrugsByName(String drugname) {
		return drugsRepository.findByName(drugname);
	}
	
	
	public Drugs findByLocation(Long drugId, String location){
		Drugs drug=searchDrugsByID(drugId).get();
		
		List<DrugDetails> drugs=drug.getDrugDetails();
		for(DrugDetails drugDetail:drugs) {	
			if(drugDetail.getLocation().equals(location)) {
				List<DrugDetails> drugDetailWithGivenLocation=new ArrayList<>();
				drugDetailWithGivenLocation.add(drugDetail);
				drug.setDrugDetails(drugDetailWithGivenLocation);
				return drug;
			}
		}
		return null;
	}

	public boolean isAvailable(String drugName, String location){
		Drugs drug = searchDrugsByName(drugName).get();
		List<DrugDetails> drugs = drug.getDrugDetails();
		for(DrugDetails drugDetails: drugs){
			if(drugDetails.getLocation().equals(location)){
				return true;
			}
		}
		return false;
	}
	
	public Drugs save(Drugs drugs) {

		return drugsRepository.save(drugs);
	}


}
