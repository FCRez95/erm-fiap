package com.example.ermfiap.domain.Campaing.entity;

import jakarta.persistence.*;

@Entity
public class CampaingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Long id;
    private Long idUser;
    private String name;
    private String product;
    private String clickAuther;

    public CampaingEntity() {
    }

    public CampaingEntity(Long id, Long idUser, String name, String product, String clickAuther) {
        this.id = id;
        this.idUser = idUser;
        this.name = name;
        this.product = product;
        this.clickAuther = clickAuther;
    }

    public Long getId() {
        return id;
    }

    public CampaingEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getIdUser() {
        return idUser;
    }

    public CampaingEntity setIdUser(Long idUser) {
        this.idUser = idUser;
        return this;
    }

    public String getName() {
        return name;
    }

    public CampaingEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getProduct() {
        return product;
    }

    public CampaingEntity setProduct(String product) {
        this.product = product;
        return this;
    }

    public String getClickAuther() {
        return clickAuther;
    }

    public CampaingEntity setClickAuther(String clickAuther) {
        this.clickAuther = clickAuther;
        return this;
    }
}

