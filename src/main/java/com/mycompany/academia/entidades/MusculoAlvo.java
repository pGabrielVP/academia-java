/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.entidades;

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
    @NamedQuery(name = "MusculoAlvo.findById", query = "SELECT m FROM MusculoAlvo m WHERE m.id = :id"),
    @NamedQuery(name = "MusculoAlvo.findByAlvo", query = "SELECT m FROM MusculoAlvo m WHERE m.alvo = :alvo")})
public class MusculoAlvo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "alvo")
    private String alvo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musculoAlvo")
    private List<Exercicio> exercicioList;

    public MusculoAlvo() {
    }

    public MusculoAlvo(Integer id) {
        this.id = id;
    }

    public MusculoAlvo(Integer id, String alvo) {
        this.id = id;
        this.alvo = alvo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAlvo() {
        return alvo;
    }

    public void setAlvo(String alvo) {
        this.alvo = alvo;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MusculoAlvo)) {
            return false;
        }
        MusculoAlvo other = (MusculoAlvo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return id.toString();
    }

}
