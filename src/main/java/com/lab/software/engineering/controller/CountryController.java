package com.lab.software.engineering.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lab.software.engineering.exceptions.BadRequestException;
import com.lab.software.engineering.model.Bill;
import com.lab.software.engineering.model.Country;
import com.lab.software.engineering.repository.CountryRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "CountryControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/country", produces = MediaType.APPLICATION_JSON_VALUE)
public class CountryController {

	@Autowired
	private CountryRepository countryRepo;

	@Secured({"ROLE_ADMIN", "ROLE_USER"})
	@GetMapping
	public List<Country> findAllCountries() {
		return countryRepo.findAll();
	}

	@Secured({"ROLE_ADMIN" })
	@ApiOperation("Create new country")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Country createCountry(@RequestBody @Valid Country country) {
		return countryRepo.save(country);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Find country with the specific id")
	@GetMapping(path = "/{id}")
	public Optional<Country> findCountryById(@PathVariable(value = "id") long id) {
		return countryRepo.findById(id);
	}
	@ApiOperation("Find country by name")
	@GetMapping(path = "/name")
	public Country findByName(@PathVariable String name ) {
		return countryRepo.findByName(name);
	}

	@Secured({"ROLE_ADMIN" })
	@ApiOperation("Update country")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Country updateCountry(@PathVariable Long id, @RequestBody @Valid Country countryRequest) {
		Country country = countryRepo.findById(countryRequest.getId())
				.orElseThrow(() -> new BadRequestException("Country for provided id not found"));
		if (countryRequest.getName() != null) {
			country.setName(countryRequest.getName());
		}
		return countryRepo.save(country);
	}

	@Secured({ "ROLE_ADMIN" })
	@ApiOperation("Delete  country")
	@DeleteMapping(value = "/{id}")
	public void deleteCountry(@PathVariable Long id) {
		if (id != null) {
			Optional<Country> country = countryRepo.findById(id);
			if (country != null) {
				countryRepo.deleteById(id);
			}
		}
	}

}
