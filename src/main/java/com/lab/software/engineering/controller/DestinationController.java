package com.lab.software.engineering.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.software.engineering.exceptions.BadRequestException;
import com.lab.software.engineering.model.Destination;
import com.lab.software.engineering.model.Employee;
import com.lab.software.engineering.repository.CountryRepository;
import com.lab.software.engineering.repository.DestinationRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "DestinationControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/destination", produces = MediaType.APPLICATION_JSON_VALUE)
public class DestinationController {

	@Autowired
	private DestinationRepository destionationRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) // testirano i radi
	@ApiOperation("Create new destination")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Destination createDestination(@RequestBody Destination destination) {
		destination.setCountry(countryRepo.findByName(destination.getCountry().getName()));
		Destination newDestination = destionationRepo.save(destination);
		return newDestination;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Find all destination")
	@GetMapping
	public Page<Destination> findAllDestination(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "100") int size) {
		Page<Destination> pagedResult = destionationRepo.findAll(PageRequest.of(page, size));
		return pagedResult;
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Find destination with the specific id")
	@GetMapping(path = "/{id}")
	public Optional<Destination> findEmployeeById(@PathVariable(value = "id") Long id) {
		return destionationRepo.findById(id);
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Update destination")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Destination updateDestination(@PathVariable Long id, @RequestBody @Valid Destination destinationRequest) {
		Destination destination = destionationRepo.findById(destinationRequest.getId())
				.orElseThrow(() -> new BadRequestException("Destination for provided id not found"));
		if (destinationRequest.getDailyAllowance() != null) {
			destination.setDailyAllowance(destinationRequest.getDailyAllowance());
		}
		if (destinationRequest.getName() != null) {
			destination.setName(destinationRequest.getName());
		}
		if (destinationRequest.getCountry() != null && destinationRequest.getCountry().getName() != null) {
			destination.setCountry(countryRepo.findByName(destination.getCountry().getName()));
		}
		return destionationRepo.save(destination);
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Delete  destination")
	@DeleteMapping(value = "/{id}")
	public void deleteDestination(@PathVariable Long id) {
		if (id != null) {
			Optional<Destination> destination = destionationRepo.findById(id);
			if (destination != null) {
				destionationRepo.deleteById(id);
			}
		}

	}
}
