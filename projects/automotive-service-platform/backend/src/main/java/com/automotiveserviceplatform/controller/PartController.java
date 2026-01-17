package com.automotiveserviceplatform.controller;

import com.automotiveserviceplatform.entity.Part;
import com.automotiveserviceplatform.payload.request.PartRequest;
import com.automotiveserviceplatform.payload.response.MessageResponse;
import com.automotiveserviceplatform.repository.PartRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/parts")
public class PartController {

    @Autowired
    PartRepository partRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addPart(@Valid @RequestBody PartRequest partRequest) {
        Part part = new Part();
        part.setName(partRequest.getName());
        part.setQuantity(partRequest.getQuantity());

        partRepository.save(part);

        return ResponseEntity.ok(new MessageResponse("Part added successfully!"));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MECHANIC')")
    public ResponseEntity<List<Part>> getAllParts() {
        return ResponseEntity.ok(partRepository.findAll());
    }
}
