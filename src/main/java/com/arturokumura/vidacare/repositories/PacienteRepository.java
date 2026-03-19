package com.arturokumura.vidacare.repositories;

import com.arturokumura.vidacare.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository < Paciente, Long > {
    //  Verificar se já existe CPF
    boolean existsByCpf(String cpf);

    //  Buscar por CPF
    Paciente findByCpf(String cpf);
}
