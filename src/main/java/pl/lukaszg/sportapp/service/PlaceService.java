package pl.lukaszg.sportapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.lukaszg.sportapp.model.Place;
import pl.lukaszg.sportapp.model.PlaceRepository;
import pl.lukaszg.sportapp.service.exceptions.ItemNotFoundException;

@Service("placeService")
public class PlaceService {
    @Autowired
    PlaceRepository placeRepository;

    // 1. szukanie place po id
    public Place findPlaceById(Long id) {
        return placeRepository.findById(id).orElseThrow(() -> new ItemNotFoundException("Could not find place: " + id));
    }
}
