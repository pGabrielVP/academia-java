/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.model.entidades;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author paulo
 */
@Entity
@Table(name = "musculo_alvo")
@NamedQueries({
    @NamedQuery(name = "MusculoAlvo.findAll", query = "SELECT m FROM MusculoAlvo m"),
    @NamedQuery(name = "MusculoAlvo.findByIdAlvo", query = "SELECT m FROM MusculoAlvo m WHERE m.idAlvo = :idAlvo"),
    @NamedQuery(name = "MusculoAlvo.findByNomeAlvo", query = "SELECT m FROM MusculoAlvo m WHERE m.nomeAlvo = :nomeAlvo")})
public class MusculoAlvo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alvo")
    private Integer idAlvo;
    @Basic(optional = false)
    @Column(name = "nome_alvo")
    private String nomeAlvo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musculoAlvo")
    private List<Exercicio> exercicioList;

    public MusculoAlvo() {
    }

    public MusculoAlvo(Integer idAlvo) {
        this.idAlvo = idAlvo;
    }

    public MusculoAlvo(Integer idAlvo, String nomeAlvo) {
        this.idAlvo = idAlvo;
        this.nomeAlvo = nomeAlvo;
    }

    public Integer getIdAlvo() {
        return idAlvo;
    }

    public void setIdAlvo(Integer idAlvo) {
        this.idAlvo = idAlvo;
    }

    public String getNomeAlvo() {
        return nomeAlvo;
    }

    public void setNomeAlvo(String nomeAlvo) {
        this.nomeAlvo = nomeAlvo;
    }

    public List<Exercicio> getExercicioList() {
        return exercicioList;
    }

    public void setExercicioList(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAlvo != null ? idAlvo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusculoAlvo)) {
            return false;
        }
        MusculoAlvo other = (MusculoAlvo) object;
        if ((this.idAlvo == null && other.idAlvo != null) || (this.idAlvo != null && !this.idAlvo.equals(other.idAlvo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idAlvo.toString();
    }
    
}
