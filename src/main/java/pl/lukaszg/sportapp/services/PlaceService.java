package pl.lukaszg.sportapp.services;

import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Place;
import pl.lukaszg.sportapp.repositories.PlaceRepository;
import pl.lukaszg.sportapp.services.exceptions.ItemNotFoundException;

@Service("placeService")
public class PlaceService {
    private final PlaceRepository placeRepository;

    public PlaceService(PlaceRepository placeRepository) {
        this.placeRepository = placeRepository;
    }

    // 1. szukanie place po id
    public Place findPlaceById(Long id) {
        return placeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find place: " + id));
    }

    public

}
