package com.arturokumura.vidacare.model;


import jakarta.persistence.*;

@Entity
@Table(name = "endereço")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rua;
    private String bairro;
    private Integer numero;

    @OneToOne
    @JoinColumn (name = "paciente_id")
    private Paciente paciente;

    public Endereco() {
    }

    public Endereco( String rua, String bairro, Integer numero) {
        this.rua = rua;
        this.bairro = bairro;
        this.numero = numero;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Endereco{");
        sb.append("id=").append(id);
        sb.append(", rua='").append(rua).append('\'');
        sb.append(", bairro='").append(bairro).append('\'');
        sb.append(", numero=").append(numero);
        sb.append('}');
        return sb.toString();
    }
}