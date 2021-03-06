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
 * Body26
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class Body26 {
  @SerializedName("device_id")
  private String deviceId = null;

  @SerializedName("is_sent")
  private Boolean isSent = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public Body26 deviceId(String deviceId) {
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

  public Body26 isSent(Boolean isSent) {
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

  public Body26 merId(String merId) {
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

  public Body26 sessiontoken(String sessiontoken) {
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
    Body26 body26 = (Body26) o;
    return Objects.equals(this.deviceId, body26.deviceId) &&
        Objects.equals(this.isSent, body26.isSent) &&
        Objects.equals(this.merId, body26.merId) &&
        Objects.equals(this.sessiontoken, body26.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deviceId, isSent, merId, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body26 {\n");
    
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    isSent: ").append(toIndentedString(isSent)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
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

