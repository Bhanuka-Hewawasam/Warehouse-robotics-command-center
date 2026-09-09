package com.warehouse.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * What the client sends us when creating a new order via POST /api/orders.
 * Kept separate from the Order entity so the API contract doesn't break
 * if we change internal database fields later.
 *
 * NOTE: unlike Robot's xPos/yPos, these field names do NOT hit the Jackson
 * two-capital-letters naming quirk (see RobotRequest.java), because "Pickup"
 * and "Drop" aren't immediately followed by "get" - getPickupX() decapitalizes
 * normally to "pickupX". No @JsonProperty needed here, but keep this in mind
 * any time a field name starts with a short acronym.
 */
public class OrderRequest {

    @NotBlank(message = "customerName is required")
    private String customerName;

    @NotBlank(message = "itemDescription is required")
    private String itemDescription;

    @NotNull(message = "pickupX is required")
    private Integer pickupX;

    @NotNull(message = "pickupY is required")
    private Integer pickupY;

    @NotNull(message = "dropX is required")
    private Integer dropX;

    @NotNull(message = "dropY is required")
    private Integer dropY;

    public OrderRequest() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Integer getPickupX() {
        return pickupX;
    }

    public void setPickupX(Integer pickupX) {
        this.pickupX = pickupX;
    }

    public Integer getPickupY() {
        return pickupY;
    }

    public void setPickupY(Integer pickupY) {
        this.pickupY = pickupY;
    }

    public Integer getDropX() {
        return dropX;
    }

    public void setDropX(Integer dropX) {
        this.dropX = dropX;
    }

    public Integer getDropY() {
        return dropY;
    }

    public void setDropY(Integer dropY) {
        this.dropY = dropY;
    }
}