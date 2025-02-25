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
    @NamedQuery(name = "Exercicio.findByExercicioId", query = "SELECT e FROM Exercicio e WHERE e.exercicioId = :exercicioId"),
    @NamedQuery(name = "Exercicio.findByNomeExercicio", query = "SELECT e FROM Exercicio e WHERE e.nomeExercicio = :nomeExercicio"),
    @NamedQuery(name = "Exercicio.findByImagem", query = "SELECT e FROM Exercicio e WHERE e.imagem = :imagem")})
public class Exercicio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "exercicio_id")
    private Integer exercicioId;
    @Basic(optional = false)
    @Column(name = "nome_exercicio")
    private String nomeExercicio;
    @Basic(optional = false)
    @Column(name = "imagem")
    private String imagem;
    @JoinColumn(name = "equipamento_necessario", referencedColumnName = "id_equipamento")
    @ManyToOne(optional = false)
    private Equipamento equipamentoNecessario;
    @JoinColumn(name = "musculo_alvo", referencedColumnName = "id_alvo")
    @ManyToOne(optional = false)
    private MusculoAlvo musculoAlvo;

    public Exercicio() {
    }

    public Exercicio(Integer exercicioId) {
        this.exercicioId = exercicioId;
    }

    public Exercicio(Integer exercicioId, String nomeExercicio, String imagem) {
        this.exercicioId = exercicioId;
        this.nomeExercicio = nomeExercicio;
        this.imagem = imagem;
    }

    public Integer getExercicioId() {
        return exercicioId;
    }

    public void setExercicioId(Integer exercicioId) {
        this.exercicioId = exercicioId;
    }

    public String getNomeExercicio() {
        return nomeExercicio;
    }

    public void setNomeExercicio(String nomeExercicio) {
        this.nomeExercicio = nomeExercicio;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Equipamento getEquipamentoNecessario() {
        return equipamentoNecessario;
    }

    public void setEquipamentoNecessario(Equipamento equipamentoNecessario) {
        this.equipamentoNecessario = equipamentoNecessario;
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
        hash += (exercicioId != null ? exercicioId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Exercicio)) {
            return false;
        }
        Exercicio other = (Exercicio) object;
        if ((this.exercicioId == null && other.exercicioId != null) || (this.exercicioId != null && !this.exercicioId.equals(other.exercicioId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return exercicioId.toString();
    }

}
