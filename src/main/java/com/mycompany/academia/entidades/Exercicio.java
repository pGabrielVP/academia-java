/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author paulo
 */
@Entity
@Table(name = "exercicio")
@NamedQueries({
    @NamedQuery(name = "Exercicio.findAll", query = "SELECT e FROM Exercicio e"),
    @NamedQuery(name = "Exercicio.findByIdExercicio", query = "SELECT e FROM Exercicio e WHERE e.idExercicio = :idExercicio"),
    @NamedQuery(name = "Exercicio.findByNomeExercicio", query = "SELECT e FROM Exercicio e WHERE e.nomeExercicio = :nomeExercicio"),
    @NamedQuery(name = "Exercicio.findByImagem", query = "SELECT e FROM Exercicio e WHERE e.imagem = :imagem"),
    @NamedQuery(name = "Exercicio.findByMusculoAlvo", query = "SELECT e FROM Exercicio e WHERE e.musculoAlvo = :musculoAlvo")})
public class Exercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_exercicio")
    private Integer idExercicio;
    @Basic(optional = false)
    @Column(name = "nome_exercicio")
    private String nomeExercicio;
    @Basic(optional = false)
    @Lob
    @Column(name = "imagem")
    private byte[] imagem;
    @JoinColumn(name = "musculo_alvo", referencedColumnName = "id_alvo")
    @ManyToOne(optional = false)
    private MusculoAlvo musculoAlvo;

    public Exercicio() {
    }

    public Exercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public Exercicio(Integer idExercicio, String nomeExercicio, byte[] imagem) {
        this.idExercicio = idExercicio;
        this.nomeExercicio = nomeExercicio;
        this.imagem = imagem;
    }

    public Integer getIdExercicio() {
        return idExercicio;
    }

    public void setIdExercicio(Integer idExercicio) {
        this.idExercicio = idExercicio;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public MusculoAlvo getMusculoAlvo() {
        return musculoAlvo;
    }

    public void setMusculoAlvo(MusculoAlvo musculoAlvo) {
        this.musculoAlvo = musculoAlvo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idExercicio != null ? idExercicio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exercicio)) {
            return false;
        }
        Exercicio other = (Exercicio) object;
        if ((this.idExercicio == null && other.idExercicio != null) || (this.idExercicio != null && !this.idExercicio.equals(other.idExercicio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idExercicio.toString();
    }
    
}
