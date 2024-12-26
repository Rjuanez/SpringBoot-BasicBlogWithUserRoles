package com.tutorial.tutorialspringboot.Service;

import com.tutorial.tutorialspringboot.DTO.PublicationDTO;
import com.tutorial.tutorialspringboot.Entities.Publication;
import com.tutorial.tutorialspringboot.Exeption.ResourceNotFoundException;
import com.tutorial.tutorialspringboot.Repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PublicationServiceImp implements PublicationService {

    @Autowired
    private PublicationRepository publicationRepository;

    @Override
    public PublicationDTO createPublication(PublicationDTO publicationDTO) {
        // Convert DTO to Entity so we can save it
        Publication publication = mapToPublication(publicationDTO);

        Publication newPublication = publicationRepository.save(publication);

        // Convert entity to DTO to send the response back
        PublicationDTO responsePublication = mapToPublicationDTO(newPublication);

        return responsePublication;
    }

    @Override
    public List<PublicationDTO> getAllPublications(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Publication> publications = publicationRepository.findAll(pageable);

        List<Publication> publicationsList = publications.getContent();
        return publicationsList.stream().map(this::mapToPublicationDTO).collect(Collectors.toList());
    }

    @Override
    public PublicationDTO getPublicationById(long id) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        return mapToPublicationDTO(publication);
    }

    @Override
    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));

        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        Publication updatedPublication = publicationRepository.save(publication);

        return mapToPublicationDTO(updatedPublication);
    }

    @Override
    public void deletePublication(long id) {
        Publication publication = publicationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Publication", "id", id));
        publicationRepository.delete(publication);
    }

    private PublicationDTO mapToPublicationDTO(Publication publication) {
        PublicationDTO publicationDTO = new PublicationDTO();
        publicationDTO.setId(publication.getId());
        publicationDTO.setTitle(publication.getTitle());
        publicationDTO.setDescription(publication.getDescription());
        publicationDTO.setContent(publication.getContent());
        return publicationDTO;
    }

    private Publication mapToPublication(PublicationDTO publicationDTO) {
        Publication publication = new Publication();
        publication.setId(publicationDTO.getId());
        publication.setTitle(publicationDTO.getTitle());
        publication.setDescription(publicationDTO.getDescription());
        publication.setContent(publicationDTO.getContent());
        return publication;
    }




}
