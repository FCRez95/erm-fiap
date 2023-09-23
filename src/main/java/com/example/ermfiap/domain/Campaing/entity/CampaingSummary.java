package com.example.ermfiap.domain.Campaing.entity;

public class CampaingSummary {
    private Double income;
    private Integer totalClicks;
    private Integer totalPaidClicks;

    public CampaingSummary() {
    }

    public CampaingSummary(Double income, Integer totalClicks, Integer totalPaidClicks) {
        this.income = income;
        this.totalClicks = totalClicks;
        this.totalPaidClicks = totalPaidClicks;
    }

    public Double getIncome() {
        return income;
    }

    public CampaingSummary setIncome(Double income) {
        this.income = income;
        return this;
    }

    public Integer getTotalClicks() {
        return totalClicks;
    }

    public CampaingSummary setTotalClicks(Integer totalClicks) {
        this.totalClicks = totalClicks;
        return this;
    }

    public Integer getTotalPaidClicks() {
        return totalPaidClicks;
    }

    public CampaingSummary setTotalPaidClicks(Integer totalPaidClicks) {
        this.totalPaidClicks = totalPaidClicks;
        return this;
    }

    @Override
    public String toString() {
        return "CampaingSummary{" +
                "income=" + income +
                ", totalClicks=" + totalClicks +
                ", totalPaidClicks=" + totalPaidClicks +
                '}';
    }
}