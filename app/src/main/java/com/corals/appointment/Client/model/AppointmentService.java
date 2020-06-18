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

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AppointmentService
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class AppointmentService {
  @SerializedName("ser_id")
  private String serId = null;

  @SerializedName("ser_description")
  private String serDescription = null;

  @SerializedName("slots_needed")
  private String slotsNeeded = null;

  @SerializedName("ser_start_time")
  private String serStartTime = null;

  @SerializedName("ser_end_time")
  private String serEndTime = null;

  @SerializedName("slot_before_nxt_appt")
  private String slotBeforeNxtAppt = null;

  @SerializedName("max_appt_allowed")
  private String maxApptAllowed = null;

  @SerializedName("is_active")
  private Boolean isActive = null;

  @SerializedName("ser_name")
  private String serName = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("ser_price")
  private String serPrice = null;

  @SerializedName("ser_duration")
  private String serDuration = null;

  public AppointmentService serId(String serId) {
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

  public AppointmentService serDescription(String serDescription) {
    this.serDescription = serDescription;
    return this;
  }

   /**
   * Get serDescription
   * @return serDescription
  **/
  @Schema(description = "")
  public String getSerDescription() {
    return serDescription;
  }

  public void setSerDescription(String serDescription) {
    this.serDescription = serDescription;
  }

  public AppointmentService slotsNeeded(String slotsNeeded) {
    this.slotsNeeded = slotsNeeded;
    return this;
  }

   /**
   * Get slotsNeeded
   * @return slotsNeeded
  **/
  @Schema(description = "")
  public String getSlotsNeeded() {
    return slotsNeeded;
  }

  public void setSlotsNeeded(String slotsNeeded) {
    this.slotsNeeded = slotsNeeded;
  }

  public AppointmentService serStartTime(String serStartTime) {
    this.serStartTime = serStartTime;
    return this;
  }

   /**
   * Get serStartTime
   * @return serStartTime
  **/
  @Schema(description = "")
  public String getSerStartTime() {
    return serStartTime;
  }

  public void setSerStartTime(String serStartTime) {
    this.serStartTime = serStartTime;
  }

  public AppointmentService serEndTime(String serEndTime) {
    this.serEndTime = serEndTime;
    return this;
  }

   /**
   * Get serEndTime
   * @return serEndTime
  **/
  @Schema(description = "")
  public String getSerEndTime() {
    return serEndTime;
  }

  public void setSerEndTime(String serEndTime) {
    this.serEndTime = serEndTime;
  }

  public AppointmentService slotBeforeNxtAppt(String slotBeforeNxtAppt) {
    this.slotBeforeNxtAppt = slotBeforeNxtAppt;
    return this;
  }

   /**
   * Get slotBeforeNxtAppt
   * @return slotBeforeNxtAppt
  **/
  @Schema(description = "")
  public String getSlotBeforeNxtAppt() {
    return slotBeforeNxtAppt;
  }

  public void setSlotBeforeNxtAppt(String slotBeforeNxtAppt) {
    this.slotBeforeNxtAppt = slotBeforeNxtAppt;
  }

  public AppointmentService maxApptAllowed(String maxApptAllowed) {
    this.maxApptAllowed = maxApptAllowed;
    return this;
  }

   /**
   * Get maxApptAllowed
   * @return maxApptAllowed
  **/
  @Schema(description = "")
  public String getMaxApptAllowed() {
    return maxApptAllowed;
  }

  public void setMaxApptAllowed(String maxApptAllowed) {
    this.maxApptAllowed = maxApptAllowed;
  }

  public AppointmentService isActive(Boolean isActive) {
    this.isActive = isActive;
    return this;
  }

   /**
   * Get isActive
   * @return isActive
  **/
  @Schema(description = "")
  public Boolean isIsActive() {
    return isActive;
  }

  public void setIsActive(Boolean isActive) {
    this.isActive = isActive;
  }

  public AppointmentService serName(String serName) {
    this.serName = serName;
    return this;
  }

   /**
   * Get serName
   * @return serName
  **/
  @Schema(description = "")
  public String getSerName() {
    return serName;
  }

  public void setSerName(String serName) {
    this.serName = serName;
  }

  public AppointmentService merId(String merId) {
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

  public AppointmentService serPrice(String serPrice) {
    this.serPrice = serPrice;
    return this;
  }

   /**
   * Get serPrice
   * @return serPrice
  **/
  @Schema(description = "")
  public String getSerPrice() {
    return serPrice;
  }

  public void setSerPrice(String serPrice) {
    this.serPrice = serPrice;
  }

  public AppointmentService serDuration(String serDuration) {
    this.serDuration = serDuration;
    return this;
  }

   /**
   * Get serDuration
   * @return serDuration
  **/
  @Schema(description = "")
  public String getSerDuration() {
    return serDuration;
  }

  public void setSerDuration(String serDuration) {
    this.serDuration = serDuration;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppointmentService appointmentService = (AppointmentService) o;
    return Objects.equals(this.serId, appointmentService.serId) &&
        Objects.equals(this.serDescription, appointmentService.serDescription) &&
        Objects.equals(this.slotsNeeded, appointmentService.slotsNeeded) &&
        Objects.equals(this.serStartTime, appointmentService.serStartTime) &&
        Objects.equals(this.serEndTime, appointmentService.serEndTime) &&
        Objects.equals(this.slotBeforeNxtAppt, appointmentService.slotBeforeNxtAppt) &&
        Objects.equals(this.maxApptAllowed, appointmentService.maxApptAllowed) &&
        Objects.equals(this.isActive, appointmentService.isActive) &&
        Objects.equals(this.serName, appointmentService.serName) &&
        Objects.equals(this.merId, appointmentService.merId) &&
        Objects.equals(this.serPrice, appointmentService.serPrice) &&
        Objects.equals(this.serDuration, appointmentService.serDuration);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serId, serDescription, slotsNeeded, serStartTime, serEndTime, slotBeforeNxtAppt, maxApptAllowed, isActive, serName, merId, serPrice, serDuration);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppointmentService {\n");
    
    sb.append("    serId: ").append(toIndentedString(serId)).append("\n");
    sb.append("    serDescription: ").append(toIndentedString(serDescription)).append("\n");
    sb.append("    slotsNeeded: ").append(toIndentedString(slotsNeeded)).append("\n");
    sb.append("    serStartTime: ").append(toIndentedString(serStartTime)).append("\n");
    sb.append("    serEndTime: ").append(toIndentedString(serEndTime)).append("\n");
    sb.append("    slotBeforeNxtAppt: ").append(toIndentedString(slotBeforeNxtAppt)).append("\n");
    sb.append("    maxApptAllowed: ").append(toIndentedString(maxApptAllowed)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    serName: ").append(toIndentedString(serName)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    serPrice: ").append(toIndentedString(serPrice)).append("\n");
    sb.append("    serDuration: ").append(toIndentedString(serDuration)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
