package com.tutorial.tutorialspringboot.Service;

import com.tutorial.tutorialspringboot.DTO.PublicationDTO;

import java.util.List;

public interface PublicationService {

    public PublicationDTO createPublication(PublicationDTO publicationDTO);

    public List<PublicationDTO> getAllPublications();

    public PublicationDTO getPublicationById(long id);

    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);

    public void deletePublication(long id);
}
