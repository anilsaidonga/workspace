package io.github.anilsaidonga.jobBoardBackend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.RestController;

import io.github.anilsaidonga.jobBoardBackend.service.JobService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/jobs")
public class JobController {
	private JobService jobService;
	
	public JobController(JobService jobService) {
		super();
		this.jobService = jobService;
	}

	@GetMapping
	public ResponseEntity<List<Job>> findAll()
	{
		List<Job> jobs = jobService.findAll();
		return new ResponseEntity<>(jobs, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Job> findJobById(@PathVariable Long id)
	{
		Job job = jobService.getJobById(id);
		if (job != null)
			return new ResponseEntity<>(job, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<String> createJob(@RequestBody Job job)
	{
		Boolean res = jobService.createJob(job);
		if (res)
			return new ResponseEntity<>("job successfully created!", HttpStatus.CREATED);
		return new ResponseEntity<>("job creation failed", HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteJobById(@PathVariable Long id)
	{
		Boolean res = jobService.deleteJobById(id);
		if (res)
			return new ResponseEntity<>("job deleted successfully!", HttpStatus.OK);
		return new ResponseEntity<>("job deletion failed!", HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<String> updateJobById(@PathVariable Long id, @RequestBody Job updateDetails)
	{
		Boolean res = jobService.updateJobById(id, updateDetails);
		if (res)
			return new ResponseEntity<>("job updated successfully!", HttpStatus.OK);
		return new ResponseEntity<>("job updation failed!", HttpStatus.NOT_FOUND);
	}
	
}
