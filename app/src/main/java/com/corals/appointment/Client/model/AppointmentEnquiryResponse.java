package com.corals.appointment.Client.model;
import java.util.Objects;
import com.google.gson.annotations.SerializedName;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-24T14:51:36.154Z")
public class AppointmentEnquiryResponse {
  @SerializedName("status_code")
  private String statusCode = null;
  @SerializedName("status_message")
  private String statusMessage = null;
  @SerializedName("session_token")
  private String sessionToken = null;
  @SerializedName("avail_appointments")
  private List<AppointmentAvailableSlots> availAppointments = null;
  @SerializedName("resource")
  private AppointmentResources resource = null;
  @SerializedName("resources")
  private List<AppointmentResources> resources = null;
  @SerializedName("service")
  private AppointmentService service = null;
  @SerializedName("services")
  private List<AppointmentService> services = null;
  @SerializedName("customers")
  private List<InlineResponse20013Customersrec> customers = null;
  @SerializedName("appointments")
  private List<Appointments> appointments = null;
  public AppointmentEnquiryResponse statusCode(String statusCode) {
    this.statusCode = statusCode;
    return this;
  }
  /**
   * Get statusCode
   * @return statusCode
   **/
  @Schema(description = "")
  public String getStatusCode() {
    return statusCode;
  }
  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }
  public AppointmentEnquiryResponse statusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
    return this;
  }
  /**
   * Get statusMessage
   * @return statusMessage
   **/
  @Schema(description = "")
  public String getStatusMessage() {
    return statusMessage;
  }
  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }
  public AppointmentEnquiryResponse sessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
    return this;
  }
  /**
   * Get sessionToken
   * @return sessionToken
   **/
  @Schema(description = "")
  public String getSessionToken() {
    return sessionToken;
  }
  public void setSessionToken(String sessionToken) {
    this.sessionToken = sessionToken;
  }
  public AppointmentEnquiryResponse availAppointments(List<AppointmentAvailableSlots> availAppointments) {
    this.availAppointments = availAppointments;
    return this;
  }
  public AppointmentEnquiryResponse addAvailAppointmentsItem(AppointmentAvailableSlots availAppointmentsItem) {
    if (this.availAppointments == null) {
      this.availAppointments = new ArrayList<AppointmentAvailableSlots>();
    }
    this.availAppointments.add(availAppointmentsItem);
    return this;
  }
  /**
   * Get availAppointments
   * @return availAppointments
   **/
  @Schema(description = "")
  public List<AppointmentAvailableSlots> getAvailAppointments() {
    return availAppointments;
  }
  public void setAvailAppointments(List<AppointmentAvailableSlots> availAppointments) {
    this.availAppointments = availAppointments;
  }
  public AppointmentEnquiryResponse resource(AppointmentResources resource) {
    this.resource = resource;
    return this;
  }
  /**
   * Get resource
   * @return resource
   **/
  @Schema(description = "")
  public AppointmentResources getResource() {
    return resource;
  }
  public void setResource(AppointmentResources resource) {
    this.resource = resource;
  }
  public AppointmentEnquiryResponse resources(List<AppointmentResources> resources) {
    this.resources = resources;
    return this;
  }
  public AppointmentEnquiryResponse addResourcesItem(AppointmentResources resourcesItem) {
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
  public AppointmentEnquiryResponse service(AppointmentService service) {
    this.service = service;
    return this;
  }
  /**
   * Get service
   * @return service
   **/
  @Schema(description = "")
  public AppointmentService getService() {
    return service;
  }
  public void setService(AppointmentService service) {
    this.service = service;
  }
  public AppointmentEnquiryResponse services(List<AppointmentService> services) {
    this.services = services;
    return this;
  }
  public AppointmentEnquiryResponse addServicesItem(AppointmentService servicesItem) {
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
  public AppointmentEnquiryResponse customers(List<InlineResponse20013Customersrec> customers) {
    this.customers = customers;
    return this;
  }
  public AppointmentEnquiryResponse addCustomersItem(InlineResponse20013Customersrec customersItem) {
    if (this.customers == null) {
      this.customers = new ArrayList<InlineResponse20013Customersrec>();
    }
    this.customers.add(customersItem);
    return this;
  }
  /**
   * Get customers
   * @return customers
   **/
  @Schema(description = "")
  public List<InlineResponse20013Customersrec> getCustomers() {
    return customers;
  }
  public void setCustomers(List<InlineResponse20013Customersrec> customers) {
    this.customers = customers;
  }
  public AppointmentEnquiryResponse appointments(List<Appointments> appointments) {
    this.appointments = appointments;
    return this;
  }
  public AppointmentEnquiryResponse addAppointmentsItem(Appointments appointmentsItem) {
    if (this.appointments == null) {
      this.appointments = new ArrayList<Appointments>();
    }
    this.appointments.add(appointmentsItem);
    return this;
  }
  /**
   * Get appointments
   * @return appointments
   **/
  @Schema(description = "")
  public List<Appointments> getAppointments() {
    return appointments;
  }
  public void setAppointments(List<Appointments> appointments) {
    this.appointments = appointments;
  }
  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AppointmentEnquiryResponse appointmentEnquiryResponse = (AppointmentEnquiryResponse) o;
    return Objects.equals(this.statusCode, appointmentEnquiryResponse.statusCode) &&
            Objects.equals(this.statusMessage, appointmentEnquiryResponse.statusMessage) &&
            Objects.equals(this.sessionToken, appointmentEnquiryResponse.sessionToken) &&
            Objects.equals(this.availAppointments, appointmentEnquiryResponse.availAppointments) &&
            Objects.equals(this.resource, appointmentEnquiryResponse.resource) &&
            Objects.equals(this.resources, appointmentEnquiryResponse.resources) &&
            Objects.equals(this.service, appointmentEnquiryResponse.service) &&
            Objects.equals(this.services, appointmentEnquiryResponse.services) &&
            Objects.equals(this.customers, appointmentEnquiryResponse.customers) &&
            Objects.equals(this.appointments, appointmentEnquiryResponse.appointments);
  }
  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMessage, sessionToken, availAppointments, resource, resources, service, services, customers, appointments);
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AppointmentEnquiryResponse {\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    sessionToken: ").append(toIndentedString(sessionToken)).append("\n");
    sb.append("    availAppointments: ").append(toIndentedString(availAppointments)).append("\n");
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
    sb.append("    customers: ").append(toIndentedString(customers)).append("\n");
    sb.append("    appointments: ").append(toIndentedString(appointments)).append("\n");
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