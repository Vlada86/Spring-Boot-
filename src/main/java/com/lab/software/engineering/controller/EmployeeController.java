package com.lab.software.engineering.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.lab.software.engineering.model.Employee;
import com.lab.software.engineering.repository.EmployeeRepository;
import com.lab.software.engineering.repository.RoleRepository;
import com.lab.software.engineering.services.EmployeePrincipal;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "EmployeeControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/employee", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	PasswordEncoder passEncoder;
	

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) // testirano i radi
	@ApiOperation("Find all employee")
	@GetMapping(path="/all")
	public Page<Employee> findAllEmployee(@RequestParam(name= "page", defaultValue = "0") int page, 
			@RequestParam(name="size" ,defaultValue = "100") int size){
		Page<Employee> pagedResult = employeeRepo.findAll(PageRequest.of(page, size));
		return pagedResult;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) // testirano i radi
	@ApiOperation("Create new employee")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee createEmployee(@RequestBody @Valid Employee employee) {
		// validate
		employee.setPassword(passEncoder.encode(employee.getPassword()));
		employee.setRole(roleRepo.findByName(employee.getRole().getName()));
		Employee newEmployee = employeeRepo.save(employee);
		return newEmployee;
	}

	@Secured({ "ROLE_ADMIN" }) 
	@ApiOperation("Delete  employee")
	@DeleteMapping(value = "/{id}")
	public void deleteEmployee(@PathVariable Long id) {
		if (id != null) {
			Optional<Employee> employee = employeeRepo.findById(id);
			if (employee != null) {
				employeeRepo.deleteById(id);
			}
		}

	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) 
	@ApiOperation("Find employee with the specific id")
	@GetMapping(path = "/{id}")
	public Optional<Employee> findEmployeeById(@PathVariable(value = "id") Long id) {
		return employeeRepo.findById(id);
	}

	@Secured({ "ROLE_ADMIN" }) 
	@ApiOperation("Update employee")
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee updateEmployee(@PathVariable Long id, @RequestBody @Valid Employee employeeRequest) {
		employeeRequest.setId(id);
		return updateEmployeeInternal(employeeRequest);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Update employee")
	@PutMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Employee updateMe(@RequestBody @Valid Employee employeeRequest) {
		Employee me = getLoggedInEmployee();
		if (me == null) {
			throw new BadRequestException("Logged in employee not recognized");
		}
		employeeRequest.setId(me.getId());
		return updateEmployeeInternal(employeeRequest);
	}

	private Employee updateEmployeeInternal(Employee employeeRequest) {
		Employee employee = employeeRepo.findById(employeeRequest.getId())
				.orElseThrow(() -> new BadRequestException("Employee for provided id not found"));
		if (employeeRequest.getUsername() != null) {
			employee.setUsername(employeeRequest.getUsername());
		}
		if (employeeRequest.getFirstName() != null) {
			employee.setFirstName(employeeRequest.getFirstName());
		}
		if (employeeRequest.getLastName() != null) {
			employee.setLastName(employeeRequest.getLastName());
		}
		if (employeeRequest.getEmail() != null) {
			employee.setEmail(employeeRequest.getEmail());
		}
		if (employeeRequest.getPassword() != null) {
			employee.setPassword(passEncoder.encode(employeeRequest.getPassword()));
		}
		if (employeeRequest.getRole() != null && employeeRequest.getRole().getName() != null) {
			employee.setRole(roleRepo.findByName(employee.getRole().getName()));
		}
		return employeeRepo.save(employee);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) 
	@ApiOperation("Find logged employee")
	@GetMapping(path = "/me")
	public Employee getLoggedInEmployee() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof EmployeePrincipal) {
			EmployeePrincipal empPrincipal = (EmployeePrincipal) principal;
			return empPrincipal.getEmployee();
		}
		return null;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Find employee by name ") 
	@GetMapping(value = "/name")
	public List<Employee> findByName(@RequestParam(value = "name") String name) {
		return employeeRepo.findByFirstName(name);
	}
}
