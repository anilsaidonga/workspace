package io.github.anilsaidonga.jobBoardBackend.service;

import java.util.List;

import io.github.anilsaidonga.jobBoardBackend.controller.Job;

public interface JobService {
	List<Job> findAll();
	Boolean createJob(Job job);
	Job getJobById(Long id);
	Boolean deleteJobById(Long id);
	Boolean updateJobById(Long id, Job updateDetails);
}
