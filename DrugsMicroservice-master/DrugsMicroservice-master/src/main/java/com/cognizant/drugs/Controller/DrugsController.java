package com.cognizant.drugs.Controller;

import com.cognizant.drugs.FeignClients.AuthClient;
import com.cognizant.drugs.Model.AuthResponse;
import com.cognizant.drugs.exception.TokenInvalidException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.cognizant.drugs.Model.Drugs;
import com.cognizant.drugs.Service.DrugsService;
@Slf4j
@RestController
@RequestMapping("/drugs")
public class DrugsController {
	@Autowired
	DrugsService drugsService;

	@Autowired
	private AuthClient authClient;

	private static final String UNAUTHORIZED_USER= "USER UNAUTHORIZED";

	@GetMapping("/searchDrugsByID/{drugId}")
	public ResponseEntity<?> searchDrugsByID(@PathVariable Long drugId, @RequestHeader("Authorization") String token) throws TokenInvalidException {
		AuthResponse authResponse = authClient.validate(token);
		if(!authResponse.isValid()){
			log.info("Invalid!");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Drugs drug = drugsService.searchDrugsByID(drugId).orElse(null);
		if (drug != null) {
			log.info("show drug");
			return new ResponseEntity<>(drug, HttpStatus.OK);
		}
		return new ResponseEntity<>(drug, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/searchDrugsByName/{name}")
	public ResponseEntity<Drugs> searchDrugsByName(@PathVariable String name, @RequestHeader("Authorization") String token) throws TokenInvalidException{
		AuthResponse authResponse = authClient.validate(token);
		if(!authResponse.isValid()){
			log.info("Invalid!");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Drugs drug = drugsService.searchDrugsByName(name).orElse(null);
		if (drug != null)
			return new ResponseEntity<>(drug, HttpStatus.OK);
		return new ResponseEntity<>(drug, HttpStatus.NOT_FOUND);
	}

	@GetMapping("/getDispatchableDrugStock/{drugId}/{location}")
	public ResponseEntity<Drugs> getDispatchableDrugStock(@PathVariable Long drugId, @PathVariable String location, @RequestHeader("Authorization") String token) throws TokenInvalidException{
		AuthResponse authResponse = authClient.validate(token);
		if(!authResponse.isValid()){
			log.info("Invalid!");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		Drugs drug = drugsService.findByLocation(drugId, location);
	
		return new ResponseEntity<>(drug, HttpStatus.OK);
	}

	@GetMapping("/isavailable/{drugName}/{location}")
	public boolean isAvailable(@PathVariable String drugName, @PathVariable String location, @RequestHeader("Authorization") String token) throws TokenInvalidException{
		AuthResponse authResponse = authClient.validate(token);
		if(!authResponse.isValid()){
			log.info("Invalid!");
			throw new TokenInvalidException(UNAUTHORIZED_USER);
		}
		return drugsService.isAvailable(drugName, location);
	}
	@PostMapping("/drugsAdd")
	public void save(@RequestBody Drugs med) {
		log.info("Saved!");
		drugsService.save(med);
	}

}
