
package com.corals.appointment.Client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Appointments
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-30T06:35:05.370Z")
public class Appointments {
    @SerializedName("customer_id")
    private String customerId = null;

    @SerializedName("mer_id")
    private String merId = null;

    @SerializedName("start_time")
    private String startTime = null;

    @SerializedName("end_time")
    private String endTime = null;

    @SerializedName("ser_id")
    private String serId = null;

    @SerializedName("res_id")
    private String resId = null;

    @SerializedName("appt_id")
    private String apptId = null;

    @SerializedName("allowed")
    private String allowed = null;

    @SerializedName("booked")
    private String booked = null;

    public Appointments customerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * Get customerId
     * @return customerId
     **/
    @Schema(description = "")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Appointments merId(String merId) {
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

    public Appointments startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get startTime
     * @return startTime
     **/
    @Schema(description = "")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Appointments endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get endTime
     * @return endTime
     **/
    @Schema(description = "")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Appointments serId(String serId) {
        this.serId = serId;
        return this;
    }

    /**
     * Get serId
     * @return serId
     **/
    @Schema(description = "")
    public String getSerId() {
        return serId;
    }

    public void setSerId(String serId) {
        this.serId = serId;
    }

    public Appointments resId(String resId) {
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

    public Appointments apptId(String apptId) {
        this.apptId = apptId;
        return this;
    }

    /**
     * Get apptId
     * @return apptId
     **/
    @Schema(description = "")
    public String getApptId() {
        return apptId;
    }

    public void setApptId(String apptId) {
        this.apptId = apptId;
    }

    public Appointments allowed(String allowed) {
        this.allowed = allowed;
        return this;
    }

    /**
     * Get allowed
     * @return allowed
     **/
    @Schema(description = "")
    public String getAllowed() {
        return allowed;
    }

    public void setAllowed(String allowed) {
        this.allowed = allowed;
    }

    public Appointments booked(String booked) {
        this.booked = booked;
        return this;
    }

    /**
     * Get booked
     * @return booked
     **/
    @Schema(description = "")
    public String getBooked() {
        return booked;
    }

    public void setBooked(String booked) {
        this.booked = booked;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointments appointments = (Appointments) o;
        return Objects.equals(this.customerId, appointments.customerId) &&
                Objects.equals(this.merId, appointments.merId) &&
                Objects.equals(this.startTime, appointments.startTime) &&
                Objects.equals(this.endTime, appointments.endTime) &&
                Objects.equals(this.serId, appointments.serId) &&
                Objects.equals(this.resId, appointments.resId) &&
                Objects.equals(this.apptId, appointments.apptId) &&
                Objects.equals(this.allowed, appointments.allowed) &&
                Objects.equals(this.booked, appointments.booked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, merId, startTime, endTime, serId, resId, apptId, allowed, booked);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Appointments {\n");

        sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
        sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
        sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
        sb.append("    serId: ").append(toIndentedString(serId)).append("\n");
        sb.append("    resId: ").append(toIndentedString(resId)).append("\n");
        sb.append("    apptId: ").append(toIndentedString(apptId)).append("\n");
        sb.append("    allowed: ").append(toIndentedString(allowed)).append("\n");
        sb.append("    booked: ").append(toIndentedString(booked)).append("\n");
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

