package com.tutorial.tutorialspringboot.Service;

import com.tutorial.tutorialspringboot.DTO.PublicationDTO;
import com.tutorial.tutorialspringboot.Entities.Publication;
import com.tutorial.tutorialspringboot.DTO.ResponsePublication;
import com.tutorial.tutorialspringboot.Exeption.ResourceNotFoundException;
import com.tutorial.tutorialspringboot.Repositories.PublicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public ResponsePublication getAllPublications(int pageNumber, int pageSize, String sortBy, String sortDir) {
        // Generamos la paginacion y se lo pasamos al repositorio !! Pageable tiene que ser de tipo: import org.springframework.data.domain.Pageable;
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Publication> publications = publicationRepository.findAll(pageable);

        // Hacemos get content de las publicaciones pageadas, y lo convertimos a una lista de DTOs
        List<PublicationDTO> content = publications.getContent().stream().map(this::mapToPublicationDTO).toList();

        ResponsePublication publicationResponse = new ResponsePublication();
        publicationResponse.setContent(content);
        publicationResponse.setPageNumber(publications.getNumber());
        publicationResponse.setPageSize(publications.getSize());
        publicationResponse.setTotalElements(publications.getTotalElements());
        publicationResponse.setTotalPages(publications.getTotalPages());
        publicationResponse.setLast(publications.isLast());

        return publicationResponse;
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
