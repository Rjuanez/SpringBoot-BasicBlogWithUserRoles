package com.tutorial.tutorialspringboot.Controllers;

import com.tutorial.tutorialspringboot.DTO.PublicationDTO;
import com.tutorial.tutorialspringboot.DTO.ResponsePublication;
import com.tutorial.tutorialspringboot.Service.PublicationService;
import com.tutorial.tutorialspringboot.Util.AppConstants;
import jakarta.validation.Valid;
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
    public ResponseEntity<PublicationDTO> savePublication(@Valid @RequestBody PublicationDTO publicationDTO) {
        return new ResponseEntity<>(publicationService.createPublication(publicationDTO), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponsePublication getAllPublications(@RequestParam(value = "pageNo", defaultValue = AppConstants.PAGE_NUMBER_BY_DEFAULT, required = false) int pageNumber,
                                                  @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_BY_DEFAULT, required = false) int pageSize,
                                                  @RequestParam(value = "sortBy", defaultValue = AppConstants.ORDER_BY_DEFAULT, required = false) String sortBy,
                                                  @RequestParam(value = "sortDir", defaultValue = AppConstants.ORDER_DIR_BY_DEFAULT, required = false) String sortDir) {
        return publicationService.getAllPublications(pageNumber, pageSize, sortBy, sortDir);
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
