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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author paulo
 */
@Entity
@Table(name = "imagem")
@NamedQueries({
    @NamedQuery(name = "Imagem.findAll", query = "SELECT i FROM Imagem i"),
    @NamedQuery(name = "Imagem.findById", query = "SELECT i FROM Imagem i WHERE i.id = :id"),
    @NamedQuery(name = "Imagem.findByDescricao", query = "SELECT i FROM Imagem i WHERE i.descricao = :descricao")})
public class Imagem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Lob
    @Column(name = "imagem")
    private byte[] imagem;
    @Column(name = "descricao")
    private String descricao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "imagem")
    private List<ImagemKeyword> imagemKeywordList;
    @OneToMany(mappedBy = "antes")
    private List<Movimento> movimentoList;
    @OneToMany(mappedBy = "depois")
    private List<Movimento> movimentoList1;

    public Imagem() {
    }

    public Imagem(Integer id) {
        this.id = id;
    }

    public Imagem(Integer id, byte[] imagem) {
        this.id = id;
        this.imagem = imagem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<ImagemKeyword> getImagemKeywordList() {
        return imagemKeywordList;
    }

    public void setImagemKeywordList(List<ImagemKeyword> imagemKeywordList) {
        this.imagemKeywordList = imagemKeywordList;
    }

    public List<Movimento> getMovimentoList() {
        return movimentoList;
    }

    public void setMovimentoList(List<Movimento> movimentoList) {
        this.movimentoList = movimentoList;
    }

    public List<Movimento> getMovimentoList1() {
        return movimentoList1;
    }

    public void setMovimentoList1(List<Movimento> movimentoList1) {
        this.movimentoList1 = movimentoList1;
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
        if (!(object instanceof Imagem)) {
            return false;
        }
        Imagem other = (Imagem) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.academia.entidades.Imagem[ id=" + id + " ]";
    }
    
}
