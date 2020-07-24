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
 * ApptTransactionBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-24T15:07:27.543Z")
public class ApptTransactionBody {
  @SerializedName("mobile")
  private String mobile = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("mail")
  private String mail = null;

  @SerializedName("req_type")
  private String reqType = null;

  @SerializedName("session_token")
  private String sessionToken = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("device_id")
  private String deviceId = null;

  @SerializedName("ser_id")
  private String serId = null;

  @SerializedName("res_id")
  private String resId = null;

  @SerializedName("appt_id")
  private String apptId = null;

  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("start_time")
  private String startTime = null;

  @SerializedName("end_time")
  private String endTime = null;

  @SerializedName("start_slot")
  private String startSlot = null;

  @SerializedName("end_slot")
  private String endSlot = null;

  @SerializedName("is_full_day")
  private Boolean isFullDay = null;

  @SerializedName("date")
  private String date = null;

  @SerializedName("slot_no")
  private String slotNo = null;

  @SerializedName("merchant")
  private Merchantinfo merchant = null;

  @SerializedName("resource")
  private AppointmentResources resource = null;

  @SerializedName("service")
  private AppointmentService service = null;

  @SerializedName("ser_res_unavail")
  private List<ServiceResourceUnavailBody> serResUnavail = null;

  public ApptTransactionBody mobile(String mobile) {
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

  public ApptTransactionBody name(String name) {
    this.name = name;
    return this;
  }

  /**
   * Get name
   * @return name
   **/
  @Schema(description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public ApptTransactionBody mail(String mail) {
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

  public ApptTransactionBody reqType(String reqType) {
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

  public ApptTransactionBody sessionToken(String sessionToken) {
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

  public ApptTransactionBody merId(String merId) {
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

  public ApptTransactionBody deviceId(String deviceId) {
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

  public ApptTransactionBody serId(String serId) {
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

  public ApptTransactionBody resId(String resId) {
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

  public ApptTransactionBody apptId(String apptId) {
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

  public ApptTransactionBody custId(String custId) {
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

  public ApptTransactionBody startTime(String startTime) {
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

  public ApptTransactionBody endTime(String endTime) {
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

  public ApptTransactionBody startSlot(String startSlot) {
    this.startSlot = startSlot;
    return this;
  }

  /**
   * Get startSlot
   * @return startSlot
   **/
  @Schema(description = "")
  public String getStartSlot() {
    return startSlot;
  }

  public void setStartSlot(String startSlot) {
    this.startSlot = startSlot;
  }

  public ApptTransactionBody endSlot(String endSlot) {
    this.endSlot = endSlot;
    return this;
  }

  /**
   * Get endSlot
   * @return endSlot
   **/
  @Schema(description = "")
  public String getEndSlot() {
    return endSlot;
  }

  public void setEndSlot(String endSlot) {
    this.endSlot = endSlot;
  }

  public ApptTransactionBody isFullDay(Boolean isFullDay) {
    this.isFullDay = isFullDay;
    return this;
  }

  /**
   * Get isFullDay
   * @return isFullDay
   **/
  @Schema(description = "")
  public Boolean isIsFullDay() {
    return isFullDay;
  }

  public void setIsFullDay(Boolean isFullDay) {
    this.isFullDay = isFullDay;
  }

  public ApptTransactionBody date(String date) {
    this.date = date;
    return this;
  }

  /**
   * Get date
   * @return date
   **/
  @Schema(description = "")
  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public ApptTransactionBody slotNo(String slotNo) {
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

  public ApptTransactionBody merchant(Merchantinfo merchant) {
    this.merchant = merchant;
    return this;
  }

  /**
   * Get merchant
   * @return merchant
   **/
  @Schema(description = "")
  public Merchantinfo getMerchant() {
    return merchant;
  }

  public void setMerchant(Merchantinfo merchant) {
    this.merchant = merchant;
  }

  public ApptTransactionBody resource(AppointmentResources resource) {
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

  public ApptTransactionBody service(AppointmentService service) {
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

  public ApptTransactionBody serResUnavail(List<ServiceResourceUnavailBody> serResUnavail) {
    this.serResUnavail = serResUnavail;
    return this;
  }

  public ApptTransactionBody addSerResUnavailItem(ServiceResourceUnavailBody serResUnavailItem) {
    if (this.serResUnavail == null) {
      this.serResUnavail = new ArrayList<ServiceResourceUnavailBody>();
    }
    this.serResUnavail.add(serResUnavailItem);
    return this;
  }

  /**
   * Get serResUnavail
   * @return serResUnavail
   **/
  @Schema(description = "")
  public List<ServiceResourceUnavailBody> getSerResUnavail() {
    return serResUnavail;
  }

  public void setSerResUnavail(List<ServiceResourceUnavailBody> serResUnavail) {
    this.serResUnavail = serResUnavail;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ApptTransactionBody apptTransactionBody = (ApptTransactionBody) o;
    return Objects.equals(this.mobile, apptTransactionBody.mobile) &&
            Objects.equals(this.name, apptTransactionBody.name) &&
            Objects.equals(this.mail, apptTransactionBody.mail) &&
            Objects.equals(this.reqType, apptTransactionBody.reqType) &&
            Objects.equals(this.sessionToken, apptTransactionBody.sessionToken) &&
            Objects.equals(this.merId, apptTransactionBody.merId) &&
            Objects.equals(this.deviceId, apptTransactionBody.deviceId) &&
            Objects.equals(this.serId, apptTransactionBody.serId) &&
            Objects.equals(this.resId, apptTransactionBody.resId) &&
            Objects.equals(this.apptId, apptTransactionBody.apptId) &&
            Objects.equals(this.custId, apptTransactionBody.custId) &&
            Objects.equals(this.startTime, apptTransactionBody.startTime) &&
            Objects.equals(this.endTime, apptTransactionBody.endTime) &&
            Objects.equals(this.startSlot, apptTransactionBody.startSlot) &&
            Objects.equals(this.endSlot, apptTransactionBody.endSlot) &&
            Objects.equals(this.isFullDay, apptTransactionBody.isFullDay) &&
            Objects.equals(this.date, apptTransactionBody.date) &&
            Objects.equals(this.slotNo, apptTransactionBody.slotNo) &&
            Objects.equals(this.merchant, apptTransactionBody.merchant) &&
            Objects.equals(this.resource, apptTransactionBody.resource) &&
            Objects.equals(this.service, apptTransactionBody.service) &&
            Objects.equals(this.serResUnavail, apptTransactionBody.serResUnavail);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mobile, name, mail, reqType, sessionToken, merId, deviceId, serId, resId, apptId, custId, startTime, endTime, startSlot, endSlot, isFullDay, date, slotNo, merchant, resource, service, serResUnavail);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ApptTransactionBody {\n");

    sb.append("    mobile: ").append(toIndentedString(mobile)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    mail: ").append(toIndentedString(mail)).append("\n");
    sb.append("    reqType: ").append(toIndentedString(reqType)).append("\n");
    sb.append("    sessionToken: ").append(toIndentedString(sessionToken)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    serId: ").append(toIndentedString(serId)).append("\n");
    sb.append("    resId: ").append(toIndentedString(resId)).append("\n");
    sb.append("    apptId: ").append(toIndentedString(apptId)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
    sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
    sb.append("    startSlot: ").append(toIndentedString(startSlot)).append("\n");
    sb.append("    endSlot: ").append(toIndentedString(endSlot)).append("\n");
    sb.append("    isFullDay: ").append(toIndentedString(isFullDay)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    slotNo: ").append(toIndentedString(slotNo)).append("\n");
    sb.append("    merchant: ").append(toIndentedString(merchant)).append("\n");
    sb.append("    resource: ").append(toIndentedString(resource)).append("\n");
    sb.append("    service: ").append(toIndentedString(service)).append("\n");
    sb.append("    serResUnavail: ").append(toIndentedString(serResUnavail)).append("\n");
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

