package com.maxino.pandlk.htmlreportgenerator.requestdummyvalues;

/**
 * Created by pandlk on 11/16/2015.
 */
public class RequestContact {
    String contactId;
    String requestId;
    String lastName;
    String firstName;
    String phone;
    String extension;
    String altPhone;
    String homePhone;
    String fax;
    String emailAddress;
    String mobilePhone;
    String roleType;
    String status;
    String address;
    String createdAt;


    public RequestContact( String contactId,
            String requestId,
            String lastName,
            String firstName,
            String phone,
            String extension,
            String altPhone,
            String homePhone,
            String fax,
            String emailAddress,
            String mobilePhone,
            String roleType,
            String status,
            String address,
            String createdAt) {
        this.contactId=contactId;
        this.requestId=requestId;
        this.lastName=lastName;
        this.firstName=firstName;
        this.phone=phone;
        this.extension=extension;
        this.altPhone=altPhone;
        this.homePhone=homePhone;
        this.fax=fax;
        this.emailAddress=emailAddress;
        this.mobilePhone=mobilePhone;
        this.roleType=roleType;
        this.status=status;
        this.address=address;
        this.createdAt=createdAt;

    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getAltPhone() {
        return altPhone;
    }

    public void setAltPhone(String altPhone) {
        this.altPhone = altPhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
