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
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * AppointmentAvailableSlots
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class AppointmentAvailableSlots {
  @SerializedName("num_of_slots")
  private String numOfSlots = null;

  @SerializedName("ser_start_time")
  private String serStartTime = null;

  @SerializedName("ser_end_time")
  private String serEndTime = null;

  @SerializedName("ser_duration")
  private String serDuration = null;

  @SerializedName("current_slot")
  private String currentSlot = null;

  @SerializedName("ser_slots")
  private List<AppointmentServiceSlot> serSlots = null;

  @SerializedName("resources")
  private List<AppointmentResources> resources = null;

  @SerializedName("services")
  private List<AppointmentService> services = null;

  public AppointmentAvailableSlots numOfSlots(String numOfSlots) {
    this.numOfSlots = numOfSlots;
    return this;
  }

   /**
   * Get numOfSlots
   * @return numOfSlots
  **/
   @Schema(description = "")
  public String getNumOfSlots() {
    return numOfSlots;
  }

  public void setNumOfSlots(String numOfSlots) {
    this.numOfSlots = numOfSlots;
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

  public AppointmentAvailableSlots currentSlot(String currentSlot) {
    this.currentSlot = currentSlot;
    return this;
  }

   /**
   * Get currentSlot
   * @return currentSlot
  **/
   @Schema(description = "")
  public String getCurrentSlot() {
    return currentSlot;
  }

  public void setCurrentSlot(String currentSlot) {
    this.currentSlot = currentSlot;
  }

  public AppointmentAvailableSlots serSlots(List<AppointmentServiceSlot> serSlots) {
    this.serSlots = serSlots;
    return this;
  }

  public AppointmentAvailableSlots addSerSlotsItem(AppointmentServiceSlot serSlotsItem) {
    if (this.serSlots == null) {
      this.serSlots = new ArrayList<AppointmentServiceSlot>();
    }
    this.serSlots.add(serSlotsItem);
    return this;
  }

   /**
   * Get serSlots
   * @return serSlots
  **/
   @Schema(description = "")
  public List<AppointmentServiceSlot> getSerSlots() {
    return serSlots;
  }

  public void setSerSlots(List<AppointmentServiceSlot> serSlots) {
    this.serSlots = serSlots;
  }

  public AppointmentAvailableSlots resources(List<AppointmentResources> resources) {
    this.resources = resources;
    return this;
  }

  public AppointmentAvailableSlots addResourcesItem(AppointmentResources resourcesItem) {
    if (this.resources == null) {
      this.resources = new ArrayList<AppointmentResources>();
    }
    this.resources.add(resourcesItem);
    return this;
  }

   /**
   * Get resources
   * @return resources
  **/
   @Schema(description = "")
  public List<AppointmentResources> getResources() {
    return resources;
  }

  public void setResources(List<AppointmentResources> resources) {
    this.resources = resources;
  }

  public AppointmentAvailableSlots services(List<AppointmentService> services) {
    this.services = services;
    return this;
  }

  public AppointmentAvailableSlots addServicesItem(AppointmentService servicesItem) {
    if (this.services == null) {
      this.services = new ArrayList<AppointmentService>();
    }
    this.services.add(servicesItem);
    return this;
  }

   /**
   * Get services
   * @return services
  **/
   @Schema(description = "")
  public List<AppointmentService> getServices() {
    return services;
  }

  public void setServices(List<AppointmentService> services) {
    this.services = services;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppointmentAvailableSlots appointmentAvailableSlots = (AppointmentAvailableSlots) o;
    return Objects.equals(this.numOfSlots, appointmentAvailableSlots.numOfSlots) &&
        Objects.equals(this.serStartTime, appointmentAvailableSlots.serStartTime) &&
        Objects.equals(this.serEndTime, appointmentAvailableSlots.serEndTime) &&
        Objects.equals(this.serDuration, appointmentAvailableSlots.serDuration) &&
        Objects.equals(this.currentSlot, appointmentAvailableSlots.currentSlot) &&
        Objects.equals(this.serSlots, appointmentAvailableSlots.serSlots) &&
        Objects.equals(this.resources, appointmentAvailableSlots.resources) &&
        Objects.equals(this.services, appointmentAvailableSlots.services);
  }

  @Override
  public int hashCode() {
    return Objects.hash(numOfSlots, serStartTime, serEndTime, serDuration, currentSlot, serSlots, resources, services);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppointmentAvailableSlots {\n");
    
    sb.append("    numOfSlots: ").append(toIndentedString(numOfSlots)).append("\n");
    sb.append("    serStartTime: ").append(toIndentedString(serStartTime)).append("\n");
    sb.append("    serEndTime: ").append(toIndentedString(serEndTime)).append("\n");
    sb.append("    serDuration: ").append(toIndentedString(serDuration)).append("\n");
    sb.append("    currentSlot: ").append(toIndentedString(currentSlot)).append("\n");
    sb.append("    serSlots: ").append(toIndentedString(serSlots)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
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

