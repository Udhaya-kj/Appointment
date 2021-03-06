/*
 * Corals app
 * Corals openAPI file. Current Develop
 *
 * OpenAPI spec version: 1.0.1
 * Contact: gp2wins-corals@yahoo.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package com.corals.appointment.Client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Merchantinfo
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-24T15:07:27.543Z")
public class Merchantinfo {
    @SerializedName("mer_id")
    private String merId = null;

    @SerializedName("display_name")
    private String displayName = null;

    @SerializedName("mail")
    private String mail = null;

    @SerializedName("icon")
    private String icon = null;

    @SerializedName("description")
    private String description = null;

    @SerializedName("country")
    private String country = null;

    @SerializedName("currency")
    private String currency = null;

    @SerializedName("contact_name")
    private String contactName = null;

    @SerializedName("contact_mobile")
    private String contactMobile = null;

    @SerializedName("legal_name")
    private String legalName = null;

    @SerializedName("register_no")
    private String registerNo = null;

    @SerializedName("c_template")
    private String cTemplate = null;

    @SerializedName("r_template")
    private String rTemplate = null;

    @SerializedName("cl_template")
    private String clTemplate = null;

    @SerializedName("max_loading_days")
    private String maxLoadingDays = null;

    @SerializedName("changes_allowed")
    private String changesAllowed = null;

    @SerializedName("max_appts_allowed")
    private String maxApptsAllowed = null;

    public Merchantinfo merId(String merId) {
        this.merId = merId;
        return this;
    }

    /**
     * Get merId
     * @return merId
     **/
    @Schema(description = "")
    public String getMerId() {
        return merId;
    }

    public void setMerId(String merId) {
        this.merId = merId;
    }

    public Merchantinfo displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    /**
     * Get displayName
     * @return displayName
     **/
    @Schema(description = "")
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Merchantinfo mail(String mail) {
        this.mail = mail;
        return this;
    }

    /**
     * Get mail
     * @return mail
     **/
    @Schema(description = "")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Merchantinfo icon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Get icon
     * @return icon
     **/
    @Schema(description = "")
    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Merchantinfo description(String description) {
        this.description = description;
        return this;
    }

    /**
     * Get description
     * @return description
     **/
    @Schema(description = "")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Merchantinfo country(String country) {
        this.country = country;
        return this;
    }

    /**
     * Get country
     * @return country
     **/
    @Schema(description = "")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Merchantinfo currency(String currency) {
        this.currency = currency;
        return this;
    }

    /**
     * Get currency
     * @return currency
     **/
    @Schema(description = "")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Merchantinfo contactName(String contactName) {
        this.contactName = contactName;
        return this;
    }

    /**
     * Get contactName
     * @return contactName
     **/
    @Schema(description = "")
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Merchantinfo contactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
        return this;
    }

    /**
     * Get contactMobile
     * @return contactMobile
     **/
    @Schema(description = "")
    public String getContactMobile() {
        return contactMobile;
    }

    public void setContactMobile(String contactMobile) {
        this.contactMobile = contactMobile;
    }

    public Merchantinfo legalName(String legalName) {
        this.legalName = legalName;
        return this;
    }

    /**
     * Get legalName
     * @return legalName
     **/
    @Schema(description = "")
    public String getLegalName() {
        return legalName;
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName;
    }

    public Merchantinfo registerNo(String registerNo) {
        this.registerNo = registerNo;
        return this;
    }

    /**
     * Get registerNo
     * @return registerNo
     **/
    @Schema(description = "")
    public String getRegisterNo() {
        return registerNo;
    }

    public void setRegisterNo(String registerNo) {
        this.registerNo = registerNo;
    }

    public Merchantinfo cTemplate(String cTemplate) {
        this.cTemplate = cTemplate;
        return this;
    }

    /**
     * Get cTemplate
     * @return cTemplate
     **/
    @Schema(description = "")
    public String getCTemplate() {
        return cTemplate;
    }

    public void setCTemplate(String cTemplate) {
        this.cTemplate = cTemplate;
    }

    public Merchantinfo rTemplate(String rTemplate) {
        this.rTemplate = rTemplate;
        return this;
    }

    /**
     * Get rTemplate
     * @return rTemplate
     **/
    @Schema(description = "")
    public String getRTemplate() {
        return rTemplate;
    }

    public void setRTemplate(String rTemplate) {
        this.rTemplate = rTemplate;
    }

    public Merchantinfo clTemplate(String clTemplate) {
        this.clTemplate = clTemplate;
        return this;
    }

    /**
     * Get clTemplate
     * @return clTemplate
     **/
    @Schema(description = "")
    public String getClTemplate() {
        return clTemplate;
    }

    public void setClTemplate(String clTemplate) {
        this.clTemplate = clTemplate;
    }

    public Merchantinfo maxLoadingDays(String maxLoadingDays) {
        this.maxLoadingDays = maxLoadingDays;
        return this;
    }

    /**
     * Get maxLoadingDays
     * @return maxLoadingDays
     **/
    @Schema(description = "")
    public String getMaxLoadingDays() {
        return maxLoadingDays;
    }

    public void setMaxLoadingDays(String maxLoadingDays) {
        this.maxLoadingDays = maxLoadingDays;
    }

    public Merchantinfo changesAllowed(String changesAllowed) {
        this.changesAllowed = changesAllowed;
        return this;
    }

    /**
     * Get changesAllowed
     * @return changesAllowed
     **/
    @Schema(description = "")
    public String getChangesAllowed() {
        return changesAllowed;
    }

    public void setChangesAllowed(String changesAllowed) {
        this.changesAllowed = changesAllowed;
    }

    public Merchantinfo maxApptsAllowed(String maxApptsAllowed) {
        this.maxApptsAllowed = maxApptsAllowed;
        return this;
    }

    /**
     * Get maxApptsAllowed
     * @return maxApptsAllowed
     **/
    @Schema(description = "")
    public String getMaxApptsAllowed() {
        return maxApptsAllowed;
    }

    public void setMaxApptsAllowed(String maxApptsAllowed) {
        this.maxApptsAllowed = maxApptsAllowed;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Merchantinfo merchantinfo = (Merchantinfo) o;
        return Objects.equals(this.merId, merchantinfo.merId) &&
                Objects.equals(this.displayName, merchantinfo.displayName) &&
                Objects.equals(this.mail, merchantinfo.mail) &&
                Objects.equals(this.icon, merchantinfo.icon) &&
                Objects.equals(this.description, merchantinfo.description) &&
                Objects.equals(this.country, merchantinfo.country) &&
                Objects.equals(this.currency, merchantinfo.currency) &&
                Objects.equals(this.contactName, merchantinfo.contactName) &&
                Objects.equals(this.contactMobile, merchantinfo.contactMobile) &&
                Objects.equals(this.legalName, merchantinfo.legalName) &&
                Objects.equals(this.registerNo, merchantinfo.registerNo) &&
                Objects.equals(this.cTemplate, merchantinfo.cTemplate) &&
                Objects.equals(this.rTemplate, merchantinfo.rTemplate) &&
                Objects.equals(this.clTemplate, merchantinfo.clTemplate) &&
                Objects.equals(this.maxLoadingDays, merchantinfo.maxLoadingDays) &&
                Objects.equals(this.changesAllowed, merchantinfo.changesAllowed) &&
                Objects.equals(this.maxApptsAllowed, merchantinfo.maxApptsAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(merId, displayName, mail, icon, description, country, currency, contactName, contactMobile, legalName, registerNo, cTemplate, rTemplate, clTemplate, maxLoadingDays, changesAllowed, maxApptsAllowed);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Merchantinfo {\n");

        sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
        sb.append("    displayName: ").append(toIndentedString(displayName)).append("\n");
        sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
        sb.append("    icon: ").append(toIndentedString(icon)).append("\n");
        sb.append("    description: ").append(toIndentedString(description)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    currency: ").append(toIndentedString(currency)).append("\n");
        sb.append("    contactName: ").append(toIndentedString(contactName)).append("\n");
        sb.append("    contactMobile: ").append(toIndentedString(contactMobile)).append("\n");
        sb.append("    legalName: ").append(toIndentedString(legalName)).append("\n");
        sb.append("    registerNo: ").append(toIndentedString(registerNo)).append("\n");
        sb.append("    cTemplate: ").append(toIndentedString(cTemplate)).append("\n");
        sb.append("    rTemplate: ").append(toIndentedString(rTemplate)).append("\n");
        sb.append("    clTemplate: ").append(toIndentedString(clTemplate)).append("\n");
        sb.append("    maxLoadingDays: ").append(toIndentedString(maxLoadingDays)).append("\n");
        sb.append("    changesAllowed: ").append(toIndentedString(changesAllowed)).append("\n");
        sb.append("    maxApptsAllowed: ").append(toIndentedString(maxApptsAllowed)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

