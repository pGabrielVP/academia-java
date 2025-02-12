/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academia.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "imagem_keyword")
@NamedQueries({
    @NamedQuery(name = "ImagemKeyword.findAll", query = "SELECT i FROM ImagemKeyword i"),
    @NamedQuery(name = "ImagemKeyword.findById", query = "SELECT i FROM ImagemKeyword i WHERE i.id = :id")})
public class ImagemKeyword implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "imagem", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Imagem imagem;
    @JoinColumn(name = "keyword", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Keywords keyword;

    public ImagemKeyword() {
    }

    public ImagemKeyword(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Imagem getImagem() {
        return imagem;
    }

    public void setImagem(Imagem imagem) {
        this.imagem = imagem;
    }

    public Keywords getKeyword() {
        return keyword;
    }

    public void setKeyword(Keywords keyword) {
        this.keyword = keyword;
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
        if (!(object instanceof ImagemKeyword)) {
            return false;
        }
        ImagemKeyword other = (ImagemKeyword) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.academia.entidades.ImagemKeyword[ id=" + id + " ]";
    }
    
}
