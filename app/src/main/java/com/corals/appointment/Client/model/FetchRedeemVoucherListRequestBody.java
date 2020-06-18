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

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * FetchRedeemVoucherListRequestBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class FetchRedeemVoucherListRequestBody {
  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("voucher_type")
  private String voucherType = null;

  @SerializedName("voucher_id")
  private String voucherId = null;

  @SerializedName("request_code")
  private String requestCode = null;

  @SerializedName("device_id")
  private String deviceId = null;

  @SerializedName("caller_type")
  private String callerType = null;

  @SerializedName("session_token")
  private String sessionToken = null;

  public FetchRedeemVoucherListRequestBody merId(String merId) {
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

  public FetchRedeemVoucherListRequestBody custId(String custId) {
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

  public FetchRedeemVoucherListRequestBody voucherType(String voucherType) {
    this.voucherType = voucherType;
    return this;
  }

   /**
   * Get voucherType
   * @return voucherType
  **/
 @Schema(description = "")
  public String getVoucherType() {
    return voucherType;
  }

  public void setVoucherType(String voucherType) {
    this.voucherType = voucherType;
  }

  public FetchRedeemVoucherListRequestBody voucherId(String voucherId) {
    this.voucherId = voucherId;
    return this;
  }

   /**
   * Get voucherId
   * @return voucherId
  **/
 @Schema(description = "")
  public String getVoucherId() {
    return voucherId;
  }

  public void setVoucherId(String voucherId) {
    this.voucherId = voucherId;
  }

  public FetchRedeemVoucherListRequestBody requestCode(String requestCode) {
    this.requestCode = requestCode;
    return this;
  }

   /**
   * Get requestCode
   * @return requestCode
  **/
 @Schema(description = "")
  public String getRequestCode() {
    return requestCode;
  }

  public void setRequestCode(String requestCode) {
    this.requestCode = requestCode;
  }

  public FetchRedeemVoucherListRequestBody deviceId(String deviceId) {
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

  public FetchRedeemVoucherListRequestBody callerType(String callerType) {
    this.callerType = callerType;
    return this;
  }

   /**
   * Get callerType
   * @return callerType
  **/
 @Schema(description = "")
  public String getCallerType() {
    return callerType;
  }

  public void setCallerType(String callerType) {
    this.callerType = callerType;
  }

  public FetchRedeemVoucherListRequestBody sessionToken(String sessionToken) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    FetchRedeemVoucherListRequestBody fetchRedeemVoucherListRequestBody = (FetchRedeemVoucherListRequestBody) o;
    return Objects.equals(this.merId, fetchRedeemVoucherListRequestBody.merId) &&
        Objects.equals(this.custId, fetchRedeemVoucherListRequestBody.custId) &&
        Objects.equals(this.voucherType, fetchRedeemVoucherListRequestBody.voucherType) &&
        Objects.equals(this.voucherId, fetchRedeemVoucherListRequestBody.voucherId) &&
        Objects.equals(this.requestCode, fetchRedeemVoucherListRequestBody.requestCode) &&
        Objects.equals(this.deviceId, fetchRedeemVoucherListRequestBody.deviceId) &&
        Objects.equals(this.callerType, fetchRedeemVoucherListRequestBody.callerType) &&
        Objects.equals(this.sessionToken, fetchRedeemVoucherListRequestBody.sessionToken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merId, custId, voucherType, voucherId, requestCode, deviceId, callerType, sessionToken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class FetchRedeemVoucherListRequestBody {\n");
    
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    voucherType: ").append(toIndentedString(voucherType)).append("\n");
    sb.append("    voucherId: ").append(toIndentedString(voucherId)).append("\n");
    sb.append("    requestCode: ").append(toIndentedString(requestCode)).append("\n");
    sb.append("    deviceId: ").append(toIndentedString(deviceId)).append("\n");
    sb.append("    callerType: ").append(toIndentedString(callerType)).append("\n");
    sb.append("    sessionToken: ").append(toIndentedString(sessionToken)).append("\n");
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

