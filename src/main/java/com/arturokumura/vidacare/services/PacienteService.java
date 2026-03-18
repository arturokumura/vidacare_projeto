package com.arturokumura.vidacare.services;

import com.arturokumura.vidacare.model.Paciente;
import com.arturokumura.vidacare.repositories.PacienteRepository;
import com.arturokumura.vidacare.services.exceptions.DatabaseException;
import com.arturokumura.vidacare.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {

    private final PacienteRepository repository;

    public PacienteService(PacienteRepository repository) {
        this.repository = repository;
    }

    public List<Paciente> findAll() {
        return repository.findAll();
    }

    public Paciente findById(Long id) {
         return repository.findById(id)
                 .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    public Paciente insert(Paciente obj) {
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

    public Paciente update(Long id, Paciente obj) {
            Paciente entity = repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException(id));
            updateData(entity, obj);
            return repository.save(entity);
    }

    private void updateData(Paciente entity, Paciente obj) {
        entity.setNome(obj.getNome());
        entity.setCpf(obj.getCpf());
        entity.setTelefones(obj.getTelefones());
    }
 }
