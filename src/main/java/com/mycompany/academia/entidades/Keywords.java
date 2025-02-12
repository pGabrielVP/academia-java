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
@Table(name = "keywords")
@NamedQueries({
    @NamedQuery(name = "Keywords.findAll", query = "SELECT k FROM Keywords k"),
    @NamedQuery(name = "Keywords.findById", query = "SELECT k FROM Keywords k WHERE k.id = :id"),
    @NamedQuery(name = "Keywords.findByKeyword", query = "SELECT k FROM Keywords k WHERE k.keyword = :keyword")})
public class Keywords implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "keyword")
    private String keyword;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "keyword")
    private List<ImagemKeyword> imagemKeywordList;

    public Keywords() {
    }

    public Keywords(Integer id) {
        this.id = id;
    }

    public Keywords(Integer id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<ImagemKeyword> getImagemKeywordList() {
        return imagemKeywordList;
    }

    public void setImagemKeywordList(List<ImagemKeyword> imagemKeywordList) {
        this.imagemKeywordList = imagemKeywordList;
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
        if (!(object instanceof Keywords)) {
            return false;
        }
        Keywords other = (Keywords) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.academia.entidades.Keywords[ id=" + id + " ]";
    }
    
}
