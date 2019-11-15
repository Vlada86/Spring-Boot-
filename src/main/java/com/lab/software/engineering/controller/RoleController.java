package com.lab.software.engineering.controller;

import java.util.List;
import java.util.Optional;

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
import com.lab.software.engineering.model.Role;
import com.lab.software.engineering.repository.RoleRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "RoleControllerAPI", produces = MediaType.APPLICATION_JSON_VALUE)
@RequestMapping(path = "/role", produces = MediaType.APPLICATION_JSON_VALUE)
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;

	@Secured({ "ROLE_ADMIN", "ROLE_USER" }) // testirano i radi
	@ApiOperation("Find all role")
	@GetMapping
	public List<Role> findAll() {
		return roleRepository.findAll();
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Create role")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public Role createRole(@RequestBody Role role) {
		return roleRepository.save(role);
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Find role by specific id")
	@GetMapping(path = "/{id}")
	public Optional<Role> findRoleById(@PathVariable(value = "id") long id) {
		return roleRepository.findById(id);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_USER" })
	@ApiOperation("Update role") // testirano i radi
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Role updateRole(@PathVariable Long id, @RequestBody Role roleRequest) {
		Role role = roleRepository.findById(roleRequest.getId())
				.orElseThrow(() -> new BadRequestException("Role for provided id not found"));
		if (roleRequest.getName() != null) {
			role.setName(roleRequest.getName());
		}
		return roleRepository.save(role);
	}

	@Secured({ "ROLE_ADMIN" }) // testirano i radi
	@ApiOperation("Delete  role")
	@DeleteMapping(value = "/{id}")
	public void deleteRole(@PathVariable Long id) {
		if (id != null) {
			Optional<Role> role = roleRepository.findById(id);
			if (role != null) {
				roleRepository.deleteById(id);
			}
		}

	}
}
