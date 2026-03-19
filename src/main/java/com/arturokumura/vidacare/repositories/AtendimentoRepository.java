package com.arturokumura.vidacare.repositories;

import com.arturokumura.vidacare.model.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository <Atendimento, Long > {

    boolean existsByMedicoIdAndDataAtendimento(Long medicoId, LocalDateTime dataAtendimento);
    List<Atendimento> findByMedicoId(Long medicoId);

    List<Atendimento> findByPacienteId(Long pacienteId);

    List<Atendimento> findByDataAtendimentoBetween(LocalDateTime inicio, LocalDateTime fim);
}
