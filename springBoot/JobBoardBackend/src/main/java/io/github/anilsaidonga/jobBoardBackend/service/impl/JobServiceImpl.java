package io.github.anilsaidonga.jobBoardBackend.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.anilsaidonga.jobBoardBackend.controller.Job;
import io.github.anilsaidonga.jobBoardBackend.service.JobService;

@Service
public class JobServiceImpl implements JobService {
	
	private List<Job> jobs = new ArrayList<>();
	private Long nextId = 1L;

	@Override
	public List<Job> findAll() {
		return jobs;
	}
	
	@Override
	public Boolean createJob(Job job) {
		job.setId(nextId++);
		return jobs.add(job);
	}

	@Override
	public Job getJobById(Long id) {
		for (Job job : jobs)
		{
			if (job.getId().equals(id))
				return job;
		}
		return null;
	}

	@Override
	public Boolean deleteJobById(Long id) {
		Iterator<Job> iterator = jobs.iterator();
		while (iterator.hasNext())
		{
			Job job = iterator.next();
			if (job.getId().equals(id))
			{
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public Boolean updateJobById(Long id, Job updateDetails) {
		Iterator<Job> iterator = jobs.iterator();
		while (iterator.hasNext())
		{
			Job job = iterator.next();
			if (job.getId().equals(id))
			{
				job.setTitle(updateDetails.getTitle());
				job.setDescription(updateDetails.getDescription());
				job.setMinSalary(updateDetails.getMinSalary());
				job.setMaxSalary(updateDetails.getMaxSalary());
				job.setLocation(updateDetails.getLocation());
				return true;
			}
		}
		return false;
	}

}
