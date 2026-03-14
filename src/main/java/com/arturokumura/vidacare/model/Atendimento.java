package com.arturokumura.vidacare.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "atendimento")
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String data_atendimento;

    @ManyToOne
    @JoinColumn(name = "medico_id")
    @JsonIgnore
    private Medico medico;

    @ManyToOne
    @JoinColumn (name = "paciente_id")
    private Paciente paciente;

    public Atendimento(String data_atendimento) {
        this.data_atendimento = data_atendimento;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getData_atendimento() {
        return data_atendimento;
    }

    public void setData_atendimento(String data_atendimento) {
        this.data_atendimento = data_atendimento;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Atendimento{");
        sb.append("id=").append(id);
        sb.append(", data_atendimento='").append(data_atendimento).append('\'');
        sb.append(", medico=").append(medico);
        sb.append(", paciente=").append(paciente);
        sb.append('}');
        return sb.toString();
    }
}