package com.arturokumura.vidacare.services;

import com.arturokumura.vidacare.model.Atendimento;
import com.arturokumura.vidacare.model.Medico;
import com.arturokumura.vidacare.model.Paciente;
import com.arturokumura.vidacare.repositories.AtendimentoRepository;
import com.arturokumura.vidacare.repositories.MedicoRepository;
import com.arturokumura.vidacare.repositories.PacienteRepository;
import com.arturokumura.vidacare.services.exceptions.DatabaseException;
import com.arturokumura.vidacare.services.exceptions.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AtendimentoService {

    private final AtendimentoRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;

    public AtendimentoService(AtendimentoRepository repository,
                              MedicoRepository medicoRepository,
                              PacienteRepository pacienteRepository) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
    }


    public List<Atendimento> findAll(){
        return repository.findAll();
    }

    public Atendimento findById (Long id) {
        return  repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public Atendimento insert(Atendimento obj) {

        validateAndSetRelations(obj);

        if (repository.existsByMedicoIdAndDataAtendimento(
                obj.getMedico().getId(),
                obj.getDataAtendimento())) {

            throw new DatabaseException("Médico já possui atendimento nesse horário");
        }

        return repository.save(obj);
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e ) {
            throw new ResourceNotFoundException(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Erro de integridade");
        }
    }

public Atendimento update (Long id, Atendimento obj) {
        Atendimento entity = repository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(id));
        updateData(entity, obj);
        return repository.save(entity);
}

private void updateData(Atendimento entity, Atendimento obj) {
    if (obj.getDataAtendimento() != null)
        entity.setDataAtendimento(obj.getDataAtendimento());

    if (obj.getMedico() != null)
        entity.setMedico(obj.getMedico());

    if (obj.getPaciente() != null)
        entity.setPaciente(obj.getPaciente());
}

    //  Validar relações
    private void validateAndSetRelations(Atendimento obj) {

        if (obj.getMedico() == null || obj.getMedico().getId() == null) {
            throw new DatabaseException("Médico é obrigatório");
        }

        if (obj.getPaciente() == null || obj.getPaciente().getId() == null) {
            throw new DatabaseException("Paciente é obrigatório");
        }

        Medico medico = medicoRepository.findById(obj.getMedico().getId())
                .orElseThrow(() -> new ResourceNotFoundException(obj.getMedico().getId()));

        Paciente paciente = pacienteRepository.findById(obj.getPaciente().getId())
                .orElseThrow(() -> new ResourceNotFoundException(obj.getPaciente().getId()));

        obj.setMedico(medico);
        obj.setPaciente(paciente);
    }

    // Validar horário do médico
    private void validateScheduleConflict(Long medicoId, LocalDateTime data) {

        if (repository.existsByMedicoIdAndDataAtendimento(medicoId, data)) {
            throw new DatabaseException("Médico já possui atendimento nesse horário");
        }
    }

    public List<Atendimento> findByMedicoId(Long medicoId) {
        return repository.findByMedicoId(medicoId);
    }

    public List<Atendimento> findByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId);
    }

    public List<Atendimento> findByDataBetween(LocalDateTime inicio, LocalDateTime fim) {
        return repository.findByDataAtendimentoBetween(inicio, fim);
    }
}

