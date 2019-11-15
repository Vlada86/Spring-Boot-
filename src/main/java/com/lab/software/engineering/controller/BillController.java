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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lab.software.engineering.exceptions.BadRequestException;
import com.lab.software.engineering.model.Bill;
import com.lab.software.engineering.model.Trip;
import com.lab.software.engineering.repository.BillRepository;
import com.lab.software.engineering.repository.TripRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "BillControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/bill", produces = MediaType.APPLICATION_JSON_VALUE)
public class BillController {

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private TripRepository tripRepo;

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Find all bill")
	@GetMapping
	public List<Bill> findAll() {
		return billRepo.findAll();
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Create new bill")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Bill createBill(@RequestBody @Valid Bill bill) {
		Trip trip = tripRepo.findById(bill.getTrip().getId())
				.orElseThrow(() -> new BadRequestException("Trip not found"));
		bill.setTrip(trip);
		return billRepo.save(bill);
	}

	@Secured({ "ROLE_ADMIN" })
	@ApiOperation("Delete  bill")
	@DeleteMapping(value = "/{id}")
	public void deleteBill(@PathVariable Long id) {
		if (id != null) {
			Optional<Bill> bill = billRepo.findById(id);
			if (bill != null) {
				billRepo.deleteById(id);
			}
		}

	}

	@Secured({ "ROLE_ADMIN" })
	@ApiOperation("Find bill with the specific id")
	@GetMapping(path = "/{id}")
	public Optional<Bill> findBillById(@PathVariable(value = "id") Long id) {
		return billRepo.findById(id);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Update bill")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Bill updateBill(@PathVariable Long id, @RequestBody @Valid Bill billRequest) {
		return update(billRequest);

	}

	private Bill update(Bill billRequest) {
		Bill bill = billRepo.findById(billRequest.getId())
				.orElseThrow(() -> new BadRequestException("Bill for provided id not found"));
		if (billRequest.getAmount() != null) {
			bill.setAmount(billRequest.getAmount());
		}
		if (billRequest.getDescription() != null) {
			bill.setDescription(billRequest.getDescription());
		}
		return billRepo.save(bill);
	}
	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Find trip by specific bill id")
	@GetMapping(params = { "id" })
	public List<Bill> findByTripId(@Valid @RequestParam(name = "id") Long id) {
		return billRepo.findByTripId(id);
	}

}