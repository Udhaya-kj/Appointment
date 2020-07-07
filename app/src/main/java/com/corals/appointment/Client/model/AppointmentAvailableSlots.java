
package com.corals.appointment.Client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AppointmentAvailableSlots
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-27T07:28:09.397Z")
public class AppointmentAvailableSlots {
  @SerializedName("slot_no")
  private String slotNo = null;
  @SerializedName("ser_start_time")
  private String serStartTime = null;
  @SerializedName("ser_end_time")
  private String serEndTime = null;
  @SerializedName("ser_duration")
  private String serDuration = null;
  @SerializedName("allowed")
  private String allowed = null;
  @SerializedName("booked")
  private String booked = null;
  @SerializedName("tot_resources")
  private List<AppointmentResources> totResources = null;
  @SerializedName("booked_resources")
  private List<AppointmentResources> bookedResources = null;
  public AppointmentAvailableSlots slotNo(String slotNo) {
    this.slotNo = slotNo;
    return this;
  }
  /**
   * Get slotNo
   * @return slotNo
   **/
  @Schema(description = "")
  public String getSlotNo() {
    return slotNo;
  }
  public void setSlotNo(String slotNo) {
    this.slotNo = slotNo;
  }
  public AppointmentAvailableSlots serStartTime(String serStartTime) {
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
  public AppointmentAvailableSlots serEndTime(String serEndTime) {
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
  public AppointmentAvailableSlots serDuration(String serDuration) {
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
  public AppointmentAvailableSlots allowed(String allowed) {
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
  public AppointmentAvailableSlots booked(String booked) {
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
  public AppointmentAvailableSlots totResources(List<AppointmentResources> totResources) {
    this.totResources = totResources;
    return this;
  }
  public AppointmentAvailableSlots addTotResourcesItem(AppointmentResources totResourcesItem) {
    if (this.totResources == null) {
      this.totResources = new ArrayList<AppointmentResources>();
    }
    this.totResources.add(totResourcesItem);
    return this;
  }
  /**
   * Get totResources
   * @return totResources
   **/
  @Schema(description = "")
  public List<AppointmentResources> getTotResources() {
    return totResources;
  }
  public void setTotResources(List<AppointmentResources> totResources) {
    this.totResources = totResources;
  }
  public AppointmentAvailableSlots bookedResources(List<AppointmentResources> bookedResources) {
    this.bookedResources = bookedResources;
    return this;
  }
  public AppointmentAvailableSlots addBookedResourcesItem(AppointmentResources bookedResourcesItem) {
    if (this.bookedResources == null) {
      this.bookedResources = new ArrayList<AppointmentResources>();
    }
    this.bookedResources.add(bookedResourcesItem);
    return this;
  }
  /**
   * Get bookedResources
   * @return bookedResources
   **/
  @Schema(description = "")
  public List<AppointmentResources> getBookedResources() {
    return bookedResources;
  }
  public void setBookedResources(List<AppointmentResources> bookedResources) {
    this.bookedResources = bookedResources;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppointmentAvailableSlots appointmentAvailableSlots = (AppointmentAvailableSlots) o;
    return Objects.equals(this.slotNo, appointmentAvailableSlots.slotNo) &&
            Objects.equals(this.serStartTime, appointmentAvailableSlots.serStartTime) &&
            Objects.equals(this.serEndTime, appointmentAvailableSlots.serEndTime) &&
            Objects.equals(this.serDuration, appointmentAvailableSlots.serDuration) &&
            Objects.equals(this.allowed, appointmentAvailableSlots.allowed) &&
            Objects.equals(this.booked, appointmentAvailableSlots.booked) &&
            Objects.equals(this.totResources, appointmentAvailableSlots.totResources) &&
            Objects.equals(this.bookedResources, appointmentAvailableSlots.bookedResources);
  }
  @Override
  public int hashCode() {
    return Objects.hash(slotNo, serStartTime, serEndTime, serDuration, allowed, booked, totResources, bookedResources);
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppointmentAvailableSlots {\n");
    sb.append("    slotNo: ").append(toIndentedString(slotNo)).append("\n");
    sb.append("    serStartTime: ").append(toIndentedString(serStartTime)).append("\n");
    sb.append("    serEndTime: ").append(toIndentedString(serEndTime)).append("\n");
    sb.append("    serDuration: ").append(toIndentedString(serDuration)).append("\n");
    sb.append("    allowed: ").append(toIndentedString(allowed)).append("\n");
    sb.append("    booked: ").append(toIndentedString(booked)).append("\n");
    sb.append("    totResources: ").append(toIndentedString(totResources)).append("\n");
    sb.append("    bookedResources: ").append(toIndentedString(bookedResources)).append("\n");
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