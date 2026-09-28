package com.itb.inf2fm.pizzarianeymar.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, nullable = false)
    private String ddd;

    @Column(length = 15, nullable = false)
    private String numero;

    @Column(nullable = false)
    private boolean codStatus;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDdd() { return ddd; }
    public void setDdd(String ddd) { this.ddd = ddd; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public boolean isCodStatus() { return codStatus; }
    public void setCodStatus(boolean codStatus) { this.codStatus = codStatus; }
}
