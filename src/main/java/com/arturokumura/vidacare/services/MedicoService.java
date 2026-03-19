package com.arturokumura.vidacare.services;

import com.arturokumura.vidacare.model.Medico;
import com.arturokumura.vidacare.repositories.MedicoRepository;
import com.arturokumura.vidacare.services.exceptions.DatabaseException;
import com.arturokumura.vidacare.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {

    private final MedicoRepository repository;

    public MedicoService (MedicoRepository repository) {
        this.repository = repository;
    }

    public List<Medico> findAll() {
        return repository.findAll();
    }

    public Medico findById (Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public Medico insert(Medico obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e ) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public Medico update(Long id, Medico obj) {
        Medico entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));
        updateDate(entity, obj);
        return  repository.save(entity);
    }

    private void updateDate(Medico entity, Medico obj) {
        if (obj.getNome() != null) entity.setNome(obj.getNome());
        if (obj.getCrm() != null) entity.setCrm(obj.getCrm());
    }
}
