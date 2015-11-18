package com.maxino.pandlk.htmlreportgenerator.requestdummyvalues;

/**
 * Created by pandlk on 11/16/2015.
 */
public class Request {

    String requestId;
    String placeId;
    String globalName;
    String origin;
    String contractId;
    String contractVersion;
    String placeIdToBill;
    String status;
    String personIdOwner;
    String type;
    String priority;
    String currency;
    String localCode;
    String addressId;
    String team;
    String workType;
    String projectManager;
    String orderResponsible;
    String orderDate;
    String inquiryDate;
    String plannedStart;
    String plannedEnd;
    String createdAt;

    public Request(String requestId,
                   String placeId,
                   String globalName,
                   String origin,
                   String contractId,
                   String contractVersion,
                   String placeIdToBill,
                   String status,
                   String personIdOwner,
                   String type,
                   String priority,
                   String currency,
                   String localCode,
                   String addressId,
                   String team,
                   String workType,
                   String projectManager,
                   String orderResponsible,
                   String orderDate,
                   String inquiryDate,
                   String plannedStart,
                   String plannedEnd,
                   String createdAt) {
        this.requestId = requestId;
        this.placeId = placeId;
        this.globalName = globalName;
        this.origin = origin;
        this.contractId = contractId;
        this.contractVersion = contractVersion;
        this.placeIdToBill = placeIdToBill;
        this.status = status;
        this.personIdOwner = personIdOwner;
        this.type = type;
        this.priority = priority;
        this.currency = currency;
        this.localCode = localCode;
        this.addressId = addressId;
        this.team = team;
        this.workType = workType;
        this.projectManager = projectManager;
        this.orderResponsible = orderResponsible;
        this.orderDate = orderDate;
        this.inquiryDate = inquiryDate;
        this.plannedStart = plannedStart;
        this.plannedEnd = plannedEnd;
        this.createdAt = createdAt;

    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getGlobalName() {
        return globalName;
    }

    public void setGlobalName(String globalName) {
        this.globalName = globalName;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getContractVersion() {
        return contractVersion;
    }

    public void setContractVersion(String contractVersion) {
        this.contractVersion = contractVersion;
    }

    public String getPlaceIdToBill() {
        return placeIdToBill;
    }

    public void setPlaceIdToBill(String placeIdToBill) {
        this.placeIdToBill = placeIdToBill;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPersonIdOwner() {
        return personIdOwner;
    }

    public void setPersonIdOwner(String personIdOwner) {
        this.personIdOwner = personIdOwner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLocalCode() {
        return localCode;
    }

    public void setLocalCode(String localCode) {
        this.localCode = localCode;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getOrderResponsible() {
        return orderResponsible;
    }

    public void setOrderResponsible(String orderResponsible) {
        this.orderResponsible = orderResponsible;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(String inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public String getPlannedStart() {
        return plannedStart;
    }

    public void setPlannedStart(String plannedStart) {
        this.plannedStart = plannedStart;
    }

    public String getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(String plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
