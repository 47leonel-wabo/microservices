package com.wbt.orderservice.entity;

import com.wbt.orderservice.util.OrderStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue
    private Long id;
    private String productId;
    private Long userId;
    private Double amount;
    private OrderStatus status;

    public PurchaseOrder() {
    }

    public PurchaseOrder(String productId, Long userId, Double amount, OrderStatus status) {
        this.productId = productId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
    }

    @Override
    public String toString() {
        return "PurchaseOrder{" +
                "id=" + id +
                ", productId='" + productId + '\'' +
                ", userId=" + userId +
                ", amount=" + amount +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseOrder that = (PurchaseOrder) o;
        return Objects.equals(id, that.id) && Objects.equals(productId, that.productId) && Objects.equals(userId, that.userId) && Objects.equals(amount, that.amount) && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productId, userId, amount, status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
