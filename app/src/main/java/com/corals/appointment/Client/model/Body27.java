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
 * Body27
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class Body27 {
  @SerializedName("create_datetime")
  private String createDatetime = null;

  @SerializedName("device_id")
  private String deviceId = null;

  @SerializedName("is_active")
  private Boolean isActive = null;

  @SerializedName("is_sent")
  private Boolean isSent = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("msg_text")
  private String msgText = null;

  @SerializedName("msg_title")
  private String msgTitle = null;

  @SerializedName("notify_reqid")
  private String notifyReqid = null;

  @SerializedName("send_datetime")
  private String sendDatetime = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public Body27 createDatetime(String createDatetime) {
    this.createDatetime = createDatetime;
    return this;
  }

   /**
   * Get createDatetime
   * @return createDatetime
  **/
  @Schema(description = "")
  public String getCreateDatetime() {
    return createDatetime;
  }

  public void setCreateDatetime(String createDatetime) {
    this.createDatetime = createDatetime;
  }

  public Body27 deviceId(String deviceId) {
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

  public Body27 isActive(Boolean isActive) {
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

  public Body27 isSent(Boolean isSent) {
    this.isSent = isSent;
    return this;
  }

   /**
   * Get isSent
   * @return isSent
  **/
 @Schema(description = "")
  public Boolean isIsSent() {
    return isSent;
  }

  public void setIsSent(Boolean isSent) {
    this.isSent = isSent;
  }

  public Body27 merId(String merId) {
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

  public Body27 msgText(String msgText) {
    this.msgText = msgText;
    return this;
  }

   /**
   * Get msgText
   * @return msgText
  **/
  @Schema(description = "")
  public String getMsgText() {
    return msgText;
  }

  public void setMsgText(String msgText) {
    this.msgText = msgText;
  }

  public Body27 msgTitle(String msgTitle) {
    this.msgTitle = msgTitle;
    return this;
  }

   /**
   * Get msgTitle
   * @return msgTitle
  **/
  @Schema(description = "")
  public String getMsgTitle() {
    return msgTitle;
  }

  public void setMsgTitle(String msgTitle) {
    this.msgTitle = msgTitle;
  }

  public Body27 notifyReqid(String notifyReqid) {
    this.notifyReqid = notifyReqid;
    return this;
  }

   /**
   * Get notifyReqid
   * @return notifyReqid
  **/
  @Schema(description = "")
  public String getNotifyReqid() {
    return notifyReqid;
  }

  public void setNotifyReqid(String notifyReqid) {
    this.notifyReqid = notifyReqid;
  }

  public Body27 sendDatetime(String sendDatetime) {
    this.sendDatetime = sendDatetime;
    return this;
  }

   /**
   * Get sendDatetime
   * @return sendDatetime
  **/
  @Schema(description = "")
  public String getSendDatetime() {
    return sendDatetime;
  }

  public void setSendDatetime(String sendDatetime) {
    this.sendDatetime = sendDatetime;
  }

  public Body27 sessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
    return this;
  }

   /**
   * Get sessiontoken
   * @return sessiontoken
  **/
  @Schema(description = "")
  public String getSessiontoken() {
    return sessiontoken;
  }

  public void setSessiontoken(String sessiontoken) {
    this.sessiontoken = sessiontoken;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body27 body27 = (Body27) o;
    return Objects.equals(this.createDatetime, body27.createDatetime) &&
        Objects.equals(this.deviceId, body27.deviceId) &&
        Objects.equals(this.isActive, body27.isActive) &&
        Objects.equals(this.isSent, body27.isSent) &&
        Objects.equals(this.merId, body27.merId) &&
        Objects.equals(this.msgText, body27.msgText) &&
        Objects.equals(this.msgTitle, body27.msgTitle) &&
        Objects.equals(this.notifyReqid, body27.notifyReqid) &&
        Objects.equals(this.sendDatetime, body27.sendDatetime) &&
        Objects.equals(this.sessiontoken, body27.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(createDatetime, deviceId, isActive, isSent, merId, msgText, msgTitle, notifyReqid, sendDatetime, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body27 {\n");
    
    sb.append("    createDatetime: ").append(toIndentedString(createDatetime)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    isActive: ").append(toIndentedString(isActive)).append("\n");
    sb.append("    isSent: ").append(toIndentedString(isSent)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    msgText: ").append(toIndentedString(msgText)).append("\n");
    sb.append("    msgTitle: ").append(toIndentedString(msgTitle)).append("\n");
    sb.append("    notifyReqid: ").append(toIndentedString(notifyReqid)).append("\n");
    sb.append("    sendDatetime: ").append(toIndentedString(sendDatetime)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
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
