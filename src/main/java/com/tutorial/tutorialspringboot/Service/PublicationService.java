package com.tutorial.tutorialspringboot.Service;

import com.tutorial.tutorialspringboot.DTO.PublicationDTO;
import com.tutorial.tutorialspringboot.DTO.ResponsePublication;


public interface PublicationService {

    public PublicationDTO createPublication(PublicationDTO publicationDTO);

    public ResponsePublication getAllPublications(int pageNumber, int pageSize);

    public PublicationDTO getPublicationById(long id);

    public PublicationDTO updatePublication(PublicationDTO publicationDTO, long id);

    public void deletePublication(long id);
}
