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
import com.google.gson.stream.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body23
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class Body23 {
  @SerializedName("device_id")
  private String deviceId = null;

  @SerializedName("is_broadcast")
  private Boolean isBroadcast = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("notify_message")
  private String notifyMessage = null;

  @SerializedName("notify_title")
  private String notifyTitle = null;

  @SerializedName("notify_image_url")
  private String notifyImageUrl = null;

  @SerializedName("message_type")
  private String messageType = null;

  @SerializedName("is_inbox_display")
  private Boolean isInboxDisplay = null;

  @SerializedName("notify_to")
  private List<MerchantnotificationsendNotifyTo> notifyTo = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public Body23 deviceId(String deviceId) {
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

  public Body23 isBroadcast(Boolean isBroadcast) {
    this.isBroadcast = isBroadcast;
    return this;
  }

   /**
   * Get isBroadcast
   * @return isBroadcast
  **/
     @Schema(description = "")
  public Boolean isIsBroadcast() {
    return isBroadcast;
  }

  public void setIsBroadcast(Boolean isBroadcast) {
    this.isBroadcast = isBroadcast;
  }

  public Body23 merId(String merId) {
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

  public Body23 notifyMessage(String notifyMessage) {
    this.notifyMessage = notifyMessage;
    return this;
  }

   /**
   * Get notifyMessage
   * @return notifyMessage
  **/
     @Schema(description = "")
  public String getNotifyMessage() {
    return notifyMessage;
  }

  public void setNotifyMessage(String notifyMessage) {
    this.notifyMessage = notifyMessage;
  }

  public Body23 notifyTitle(String notifyTitle) {
    this.notifyTitle = notifyTitle;
    return this;
  }

   /**
   * Get notifyTitle
   * @return notifyTitle
  **/
     @Schema(description = "")
  public String getNotifyTitle() {
    return notifyTitle;
  }

  public void setNotifyTitle(String notifyTitle) {
    this.notifyTitle = notifyTitle;
  }

  public Body23 notifyImageUrl(String notifyImageUrl) {
    this.notifyImageUrl = notifyImageUrl;
    return this;
  }

   /**
   * Get notifyImageUrl
   * @return notifyImageUrl
  **/
     @Schema(description = "")
  public String getNotifyImageUrl() {
    return notifyImageUrl;
  }

  public void setNotifyImageUrl(String notifyImageUrl) {
    this.notifyImageUrl = notifyImageUrl;
  }

  public Body23 messageType(String messageType) {
    this.messageType = messageType;
    return this;
  }

   /**
   * Get messageType
   * @return messageType
  **/
     @Schema(description = "")
  public String getMessageType() {
    return messageType;
  }

  public void setMessageType(String messageType) {
    this.messageType = messageType;
  }

  public Body23 isInboxDisplay(Boolean isInboxDisplay) {
    this.isInboxDisplay = isInboxDisplay;
    return this;
  }

   /**
   * Get isInboxDisplay
   * @return isInboxDisplay
  **/
   @Schema(description = "")
  public Boolean isIsInboxDisplay() {
    return isInboxDisplay;
  }

  public void setIsInboxDisplay(Boolean isInboxDisplay) {
    this.isInboxDisplay = isInboxDisplay;
  }

  public Body23 notifyTo(List<MerchantnotificationsendNotifyTo> notifyTo) {
    this.notifyTo = notifyTo;
    return this;
  }

  public Body23 addNotifyToItem(MerchantnotificationsendNotifyTo notifyToItem) {
    if (this.notifyTo == null) {
      this.notifyTo = new ArrayList<MerchantnotificationsendNotifyTo>();
    }
    this.notifyTo.add(notifyToItem);
    return this;
  }

   /**
   * Get notifyTo
   * @return notifyTo
  **/
     @Schema(description = "")
  public List<MerchantnotificationsendNotifyTo> getNotifyTo() {
    return notifyTo;
  }

  public void setNotifyTo(List<MerchantnotificationsendNotifyTo> notifyTo) {
    this.notifyTo = notifyTo;
  }

  public Body23 sessiontoken(String sessiontoken) {
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
    Body23 body23 = (Body23) o;
    return Objects.equals(this.deviceId, body23.deviceId) &&
        Objects.equals(this.isBroadcast, body23.isBroadcast) &&
        Objects.equals(this.merId, body23.merId) &&
        Objects.equals(this.notifyMessage, body23.notifyMessage) &&
        Objects.equals(this.notifyTitle, body23.notifyTitle) &&
        Objects.equals(this.notifyImageUrl, body23.notifyImageUrl) &&
        Objects.equals(this.messageType, body23.messageType) &&
        Objects.equals(this.isInboxDisplay, body23.isInboxDisplay) &&
        Objects.equals(this.notifyTo, body23.notifyTo) &&
        Objects.equals(this.sessiontoken, body23.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, isBroadcast, merId, notifyMessage, notifyTitle, notifyImageUrl, messageType, isInboxDisplay, notifyTo, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body23 {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    isBroadcast: ").append(toIndentedString(isBroadcast)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    notifyMessage: ").append(toIndentedString(notifyMessage)).append("\n");
    sb.append("    notifyTitle: ").append(toIndentedString(notifyTitle)).append("\n");
    sb.append("    notifyImageUrl: ").append(toIndentedString(notifyImageUrl)).append("\n");
    sb.append("    messageType: ").append(toIndentedString(messageType)).append("\n");
    sb.append("    isInboxDisplay: ").append(toIndentedString(isInboxDisplay)).append("\n");
    sb.append("    notifyTo: ").append(toIndentedString(notifyTo)).append("\n");
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

