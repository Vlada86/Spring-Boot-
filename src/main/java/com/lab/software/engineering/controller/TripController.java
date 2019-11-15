package com.lab.software.engineering.controller;

import java.util.List;
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
import com.lab.software.engineering.model.Trip;
import com.lab.software.engineering.repository.DestinationRepository;
import com.lab.software.engineering.repository.EmployeeRepository;
import com.lab.software.engineering.repository.TripRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api(value = "TravelControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/trip", produces = MediaType.APPLICATION_JSON_VALUE)
public class TripController {

	@Autowired
	private TripRepository tripRepo;

	@Autowired
	private EmployeeRepository employeeRepo;

	@Autowired
	private DestinationRepository destionationRepo;

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Get all trip")
	@GetMapping
	public Page<Trip> findAllTrip(@RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "100") int size) {
		Page<Trip> pagedResult = tripRepo.findAll(PageRequest.of(page, size));
		return pagedResult;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Create new travel")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Trip createTrip(@RequestBody @Valid Trip tripRequest) {
		Employee employee = employeeRepo.findById(tripRequest.getEmployee().getId())
				.orElseThrow(() -> new BadRequestException("Employee not found"));
		Destination destination = destionationRepo.findById(tripRequest.getDestination().getId())
				.orElseThrow(() -> new BadRequestException("Destination not found"));
		tripRequest.setEmployee(employee);
		tripRequest.setDestination(destination);
		return tripRepo.save(tripRequest);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) // testirano i radi
	@ApiOperation("Find trip with the specific id")
	@GetMapping(path = "/{id}")
	public Optional<Trip> findTripById(@PathVariable(value = "id") Long id) {
		return tripRepo.findById(id);
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Delete  trip")
	@DeleteMapping(value = "/{id}")
	public void deleteTrip(@PathVariable Long id) {
		if (id != null) {
			Optional<Trip> trip = tripRepo.findById(id);
			if (trip != null) {
				tripRepo.deleteById(id);
			}
		}

	}

	@Secured({ "ROLE_ADMIN" })
	@ApiOperation("Update trip")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Trip updateTrip(@PathVariable Long id, @RequestBody @Valid Trip tripRequest) {
		Trip trip = tripRepo.findById(tripRequest.getId())
				.orElseThrow(() -> new BadRequestException("Trip for provided id not found"));
		if (tripRequest.getDeparture_date() != null) {
			trip.setDeparture_date(tripRequest.getDeparture_date());
		}
		if (tripRequest.getReturn_date() != null) {
			trip.setReturn_date(tripRequest.getReturn_date());
		}
		if (tripRequest.getDestination() != null && tripRequest.getDestination().getName() != null) {
			trip.setDestination(destionationRepo.findById(tripRequest.getDestination().getId()).orElse(null));
		}
		if (tripRequest.getEmployee() != null && tripRequest.getEmployee().getId() != null) {
			trip.setEmployee(employeeRepo.findById(tripRequest.getEmployee().getId()).orElse(null));
		}
		return tripRepo.save(trip);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Find trip by specific employee id")
	@GetMapping(params = { "id" })
	public List<Trip> findByEmployeeId(@Valid @RequestParam(name = "id") Long id) {
		return tripRepo.findByEmployeeId(id);

	}

}