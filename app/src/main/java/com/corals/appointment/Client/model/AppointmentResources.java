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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AppointmentResources
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-14T16:11:36.591Z")
public class AppointmentResources {
    @SerializedName("res_id")
    private String resId = null;

    @SerializedName("res_name")
    private String resName = null;

    @SerializedName("manageable_load")
    private String manageableLoad = null;

    @SerializedName("mer_id")
    private String merId = null;

    @SerializedName("mobile")
    private String mobile = null;

    @SerializedName("avail_days")
    private List<AvailDay> availDays = null;

    @SerializedName("same_buss_time")
    private Boolean sameBussTime = null;

    @SerializedName("is_active")
    private String isActive = null;

    @SerializedName("ser_res_maps")
    private List<MapServiceResourceBody> serResMaps = null;

    public AppointmentResources resId(String resId) {
        this.resId = resId;
        return this;
    }

    /**
     * Get resId
     * @return resId
     **/
    @Schema(description = "")
    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public AppointmentResources resName(String resName) {
        this.resName = resName;
        return this;
    }

    /**
     * Get resName
     * @return resName
     **/
    @Schema(description = "")
    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public AppointmentResources manageableLoad(String manageableLoad) {
        this.manageableLoad = manageableLoad;
        return this;
    }

    /**
     * Get manageableLoad
     * @return manageableLoad
     **/
    @Schema(description = "")
    public String getManageableLoad() {
        return manageableLoad;
    }

    public void setManageableLoad(String manageableLoad) {
        this.manageableLoad = manageableLoad;
    }

    public AppointmentResources merId(String merId) {
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

    public AppointmentResources mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    /**
     * Get mobile
     * @return mobile
     **/
    @Schema(description = "")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public AppointmentResources availDays(List<AvailDay> availDays) {
        this.availDays = availDays;
        return this;
    }

    public AppointmentResources addAvailDaysItem(AvailDay availDaysItem) {
        if (this.availDays == null) {
            this.availDays = new ArrayList<AvailDay>();
        }
        this.availDays.add(availDaysItem);
        return this;
    }

    /**
     * Get availDays
     * @return availDays
     **/
    @Schema(description = "")
    public List<AvailDay> getAvailDays() {
        return availDays;
    }

    public void setAvailDays(List<AvailDay> availDays) {
        this.availDays = availDays;
    }

    public AppointmentResources sameBussTime(Boolean sameBussTime) {
        this.sameBussTime = sameBussTime;
        return this;
    }

    /**
     * Get sameBussTime
     * @return sameBussTime
     **/
    @Schema(description = "")
    public Boolean isSameBussTime() {
        return sameBussTime;
    }

    public void setSameBussTime(Boolean sameBussTime) {
        this.sameBussTime = sameBussTime;
    }

    public AppointmentResources isActive(String isActive) {
        this.isActive = isActive;
        return this;
    }

    /**
     * Get isActive
     * @return isActive
     **/
    @Schema(description = "")
    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public AppointmentResources serResMaps(List<MapServiceResourceBody> serResMaps) {
        this.serResMaps = serResMaps;
        return this;
    }

    public AppointmentResources addSerResMapsItem(MapServiceResourceBody serResMapsItem) {
        if (this.serResMaps == null) {
            this.serResMaps = new ArrayList<MapServiceResourceBody>();
        }
        this.serResMaps.add(serResMapsItem);
        return this;
    }

    /**
     * Get serResMaps
     * @return serResMaps
     **/
    @Schema(description = "")
    public List<MapServiceResourceBody> getSerResMaps() {
        return serResMaps;
    }

    public void setSerResMaps(List<MapServiceResourceBody> serResMaps) {
        this.serResMaps = serResMaps;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AppointmentResources appointmentResources = (AppointmentResources) o;
        return Objects.equals(this.resId, appointmentResources.resId) &&
                Objects.equals(this.resName, appointmentResources.resName) &&
                Objects.equals(this.manageableLoad, appointmentResources.manageableLoad) &&
                Objects.equals(this.merId, appointmentResources.merId) &&
                Objects.equals(this.mobile, appointmentResources.mobile) &&
                Objects.equals(this.availDays, appointmentResources.availDays) &&
                Objects.equals(this.sameBussTime, appointmentResources.sameBussTime) &&
                Objects.equals(this.isActive, appointmentResources.isActive) &&
                Objects.equals(this.serResMaps, appointmentResources.serResMaps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resId, resName, manageableLoad, merId, mobile, availDays, sameBussTime, isActive, serResMaps);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class AppointmentResources {\n");

        sb.append("    resId: ").append(toIndentedString(resId)).append("\n");
        sb.append("    resName: ").append(toIndentedString(resName)).append("\n");
        sb.append("    manageableLoad: ").append(toIndentedString(manageableLoad)).append("\n");
        sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
        sb.append("    mobile: ").append(toIndentedString(mobile)).append("\n");
        sb.append("    availDays: ").append(toIndentedString(availDays)).append("\n");
        sb.append("    sameBussTime: ").append(toIndentedString(sameBussTime)).append("\n");
        sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
        sb.append("    serResMaps: ").append(toIndentedString(serResMaps)).append("\n");
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

