package com.arturokumura.vidacare.model;

import jakarta.persistence.*;

@Entity
@Table(name = "telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 11)
    private String numero;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    public Telefone(String numero) {
        this.numero = numero;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Telefone{");
        sb.append("id=").append(id);
        sb.append(", numero='").append(numero).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
