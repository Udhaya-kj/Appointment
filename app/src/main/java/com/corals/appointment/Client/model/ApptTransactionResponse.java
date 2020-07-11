package com.corals.appointment.Client.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ApptTransactionResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-10T07:26:14.541Z")
public class ApptTransactionResponse {
  @SerializedName("req_type")
  private String reqType = null;
  @SerializedName("status_code")
  private String statusCode = null;
  @SerializedName("status_message")
  private String statusMessage = null;
  @SerializedName("session_token")
  private String sessionToken = null;
  @SerializedName("mer_id")
  private String merId = null;
  @SerializedName("cust_id")
  private String custId = null;
  @SerializedName("device_id")
  private String deviceId = null;
  @SerializedName("ser_id")
  private String serId = null;
  @SerializedName("res_id")
  private String resId = null;
  @SerializedName("appt_id")
  private String apptId = null;
  @SerializedName("resource")
  private AppointmentResources resource = null;
  @SerializedName("resources")
  private List<AppointmentResources> resources = null;
  @SerializedName("service")
  private AppointmentService service = null;
  @SerializedName("services")
  private List<AppointmentService> services = null;
  public ApptTransactionResponse reqType(String reqType) {
    this.reqType = reqType;
    return this;
  }
  /**
   * Get reqType
   * @return reqType
   **/
  @Schema(description = "")
  public String getReqType() {
    return reqType;
  }
  public void setReqType(String reqType) {
    this.reqType = reqType;
  }
  public ApptTransactionResponse statusCode(String statusCode) {
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
  public ApptTransactionResponse statusMessage(String statusMessage) {
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
  public ApptTransactionResponse sessionToken(String sessionToken) {
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
  public ApptTransactionResponse merId(String merId) {
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
  public ApptTransactionResponse custId(String custId) {
    this.custId = custId;
    return this;
  }
  /**
   * Get custId
   * @return custId
   **/
  @Schema(description = "")
  public String getCustId() {
    return custId;
  }
  public void setCustId(String custId) {
    this.custId = custId;
  }
  public ApptTransactionResponse deviceId(String deviceId) {
    this.deviceId = deviceId;
    return this;
  }
  /**
   * Get deviceId
   * @return deviceId
   **/
  @Schema(description = "")
  public String getDeviceId() {
    return deviceId;
  }
  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }
  public ApptTransactionResponse serId(String serId) {
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
  public ApptTransactionResponse resId(String resId) {
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
  public ApptTransactionResponse apptId(String apptId) {
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
  public ApptTransactionResponse resource(AppointmentResources resource) {
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
  public ApptTransactionResponse resources(List<AppointmentResources> resources) {
    this.resources = resources;
    return this;
  }
  public ApptTransactionResponse addResourcesItem(AppointmentResources resourcesItem) {
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
  public ApptTransactionResponse service(AppointmentService service) {
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
  public ApptTransactionResponse services(List<AppointmentService> services) {
    this.services = services;
    return this;
  }
  public ApptTransactionResponse addServicesItem(AppointmentService servicesItem) {
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
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApptTransactionResponse apptTransactionResponse = (ApptTransactionResponse) o;
    return Objects.equals(this.reqType, apptTransactionResponse.reqType) &&
            Objects.equals(this.statusCode, apptTransactionResponse.statusCode) &&
            Objects.equals(this.statusMessage, apptTransactionResponse.statusMessage) &&
            Objects.equals(this.sessionToken, apptTransactionResponse.sessionToken) &&
            Objects.equals(this.merId, apptTransactionResponse.merId) &&
            Objects.equals(this.custId, apptTransactionResponse.custId) &&
            Objects.equals(this.deviceId, apptTransactionResponse.deviceId) &&
            Objects.equals(this.serId, apptTransactionResponse.serId) &&
            Objects.equals(this.resId, apptTransactionResponse.resId) &&
            Objects.equals(this.apptId, apptTransactionResponse.apptId) &&
            Objects.equals(this.resource, apptTransactionResponse.resource) &&
            Objects.equals(this.resources, apptTransactionResponse.resources) &&
            Objects.equals(this.service, apptTransactionResponse.service) &&
            Objects.equals(this.services, apptTransactionResponse.services);
  }
  @Override
  public int hashCode() {
    return Objects.hash(reqType, statusCode, statusMessage, sessionToken, merId, custId, deviceId, serId, resId, apptId, resource, resources, service, services);
  }
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApptTransactionResponse {\n");
    sb.append("    reqType: ").append(toIndentedString(reqType)).append("\n");
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    sessionToken: ").append(toIndentedString(sessionToken)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    serId: ").append(toIndentedString(serId)).append("\n");
    sb.append("    resId: ").append(toIndentedString(resId)).append("\n");
    sb.append("    apptId: ").append(toIndentedString(apptId)).append("\n");
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    resources: ").append(toIndentedString(resources)).append("\n");
    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
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