package com.crud.theatre.service;

import com.crud.theatre.domain.Actor;
import com.crud.theatre.exception.ActorNotFoundException;
import com.crud.theatre.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<Actor> findAll(){
        return actorRepository.findAll();
    }

    public Actor findById(long actorId) throws ActorNotFoundException {
        return actorRepository.findById(actorId).orElseThrow(ActorNotFoundException::new);
    }

    public void delete(long actorId){
        actorRepository.deleteById(actorId);
    }

    public void save(Actor actor){
        actorRepository.save(actor);
    }
}
