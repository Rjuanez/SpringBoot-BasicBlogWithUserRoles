package com.tutorial.tutorialspringboot.Controllers;

import com.tutorial.tutorialspringboot.DTO.PublicationDTO;
import com.tutorial.tutorialspringboot.DTO.ResponsePublication;
import com.tutorial.tutorialspringboot.Service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/publication")
public class PublicationController {

    @Autowired
    private PublicationService publicationService;

    @PostMapping
    public ResponseEntity<PublicationDTO> savePublication(@RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<>(publicationService.createPublication(publicationDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponsePublication getAllPublications(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNumber, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize ) {
        return publicationService.getAllPublications(pageNumber, pageSize);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicationDTO> getPublicationById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(publicationService.getPublicationById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublicationDTO> updatePublication(@PathVariable(name = "id") long id, @RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<>(publicationService.updatePublication(publicationDTO, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePublication(@PathVariable(name = "id") long id) {
        publicationService.deletePublication(id);
        return new ResponseEntity<>("Publication deleted", HttpStatus.OK);
    }
}
