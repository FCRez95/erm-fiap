package com.example.ermfiap.domain.Click.entity;

import jakarta.persistence.*;

@Entity
public class Click {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Long id;
    private Long idCampaing;
    private String siteOrigin;
    private Double income;
    private Boolean paidClick;
    private String clickToken;

    public Click() {
    }

    public Click(Long id, Long idCampaing, String siteOrigin) {
        this.id = id;
        this.idCampaing = idCampaing;
        this.siteOrigin = siteOrigin;
    }

    public Long getId() {
        return id;
    }

    public Click setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getIdCampaing() {
        return idCampaing;
    }

    public Click setIdCampaing(Long idCampaing) {
        this.idCampaing = idCampaing;
        return this;
    }

    public Double getIncome() {
        return income;
    }

    public Click setIncome(Double income) {
        this.income = income;
        return this;
    }

    public Boolean getPaidClick() {
        return paidClick;
    }

    public Click setPaidClick(Boolean paidClick) {
        this.paidClick = paidClick;
        return this;
    }

    public String getSiteOrigin() {
        return siteOrigin;
    }

    public Click setSiteOrigin(String siteOrigin) {
        this.siteOrigin = siteOrigin;
        return this;
    }

    public String getClickToken() {
        return clickToken;
    }

    public Click setClickToken(String clickToken) {
        this.clickToken = clickToken;
        return this;
    }

    @Override
    public String toString() {
        return "Click{" +
                "id=" + id +
                ", idCampaing=" + idCampaing +
                ", siteOrigin='" + siteOrigin + '\'' +
                ", income=" + income +
                ", paidClick=" + paidClick +
                ", clickToken='" + clickToken + '\'' +
                '}';
    }
}
