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
import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Body15
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class Body15 {
  @SerializedName("callertype")
  private String callertype = null;

  @SerializedName("customerid")
  private String customerid = null;

  @SerializedName("deviceid")
  private String deviceid = null;

  @SerializedName("fromdate")
  private String fromdate = null;

  @SerializedName("iscbredeemlist")
  private Boolean iscbredeemlist = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("mobile_number")
  private String mobileNumber = null;

  @SerializedName("redeem_id")
  private String redeemId = null;

  @SerializedName("restrict_count")
  private BigDecimal restrictCount = null;

  @SerializedName("restrictbycustomer")
  private Boolean restrictbycustomer = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  @SerializedName("todate")
  private String todate = null;

  public Body15 callertype(String callertype) {
    this.callertype = callertype;
    return this;
  }

   /**
   * Get callertype
   * @return callertype
  **/
   @Schema(description = "")
  public String getCallertype() {
    return callertype;
  }

  public void setCallertype(String callertype) {
    this.callertype = callertype;
  }

  public Body15 customerid(String customerid) {
    this.customerid = customerid;
    return this;
  }

   /**
   * Get customerid
   * @return customerid
  **/
   @Schema(description = "")
  public String getCustomerid() {
    return customerid;
  }

  public void setCustomerid(String customerid) {
    this.customerid = customerid;
  }

  public Body15 deviceid(String deviceid) {
    this.deviceid = deviceid;
    return this;
  }

   /**
   * Get deviceid
   * @return deviceid
  **/
   @Schema(description = "")
  public String getDeviceid() {
    return deviceid;
  }

  public void setDeviceid(String deviceid) {
    this.deviceid = deviceid;
  }

  public Body15 fromdate(String fromdate) {
    this.fromdate = fromdate;
    return this;
  }

   /**
   * Get fromdate
   * @return fromdate
  **/
   @Schema(description = "")
  public String getFromdate() {
    return fromdate;
  }

  public void setFromdate(String fromdate) {
    this.fromdate = fromdate;
  }

  public Body15 iscbredeemlist(Boolean iscbredeemlist) {
    this.iscbredeemlist = iscbredeemlist;
    return this;
  }

   /**
   * Get iscbredeemlist
   * @return iscbredeemlist
  **/
   @Schema(description = "")
  public Boolean isIscbredeemlist() {
    return iscbredeemlist;
  }

  public void setIscbredeemlist(Boolean iscbredeemlist) {
    this.iscbredeemlist = iscbredeemlist;
  }

  public Body15 merId(String merId) {
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

  public Body15 mobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
    return this;
  }

   /**
   * Get mobileNumber
   * @return mobileNumber
  **/
   @Schema(description = "")
  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public Body15 redeemId(String redeemId) {
    this.redeemId = redeemId;
    return this;
  }

   /**
   * Get redeemId
   * @return redeemId
  **/
   @Schema(description = "")
  public String getRedeemId() {
    return redeemId;
  }

  public void setRedeemId(String redeemId) {
    this.redeemId = redeemId;
  }

  public Body15 restrictCount(BigDecimal restrictCount) {
    this.restrictCount = restrictCount;
    return this;
  }

   /**
   * Get restrictCount
   * @return restrictCount
  **/
   @Schema(description = "")
  public BigDecimal getRestrictCount() {
    return restrictCount;
  }

  public void setRestrictCount(BigDecimal restrictCount) {
    this.restrictCount = restrictCount;
  }

  public Body15 restrictbycustomer(Boolean restrictbycustomer) {
    this.restrictbycustomer = restrictbycustomer;
    return this;
  }

   /**
   * Get restrictbycustomer
   * @return restrictbycustomer
  **/
   @Schema(description = "")
  public Boolean isRestrictbycustomer() {
    return restrictbycustomer;
  }

  public void setRestrictbycustomer(Boolean restrictbycustomer) {
    this.restrictbycustomer = restrictbycustomer;
  }

  public Body15 sessiontoken(String sessiontoken) {
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

  public Body15 todate(String todate) {
    this.todate = todate;
    return this;
  }

   /**
   * Get todate
   * @return todate
  **/
   @Schema(description = "")
  public String getTodate() {
    return todate;
  }

  public void setTodate(String todate) {
    this.todate = todate;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Body15 body15 = (Body15) o;
    return Objects.equals(this.callertype, body15.callertype) &&
        Objects.equals(this.customerid, body15.customerid) &&
        Objects.equals(this.deviceid, body15.deviceid) &&
        Objects.equals(this.fromdate, body15.fromdate) &&
        Objects.equals(this.iscbredeemlist, body15.iscbredeemlist) &&
        Objects.equals(this.merId, body15.merId) &&
        Objects.equals(this.mobileNumber, body15.mobileNumber) &&
        Objects.equals(this.redeemId, body15.redeemId) &&
        Objects.equals(this.restrictCount, body15.restrictCount) &&
        Objects.equals(this.restrictbycustomer, body15.restrictbycustomer) &&
        Objects.equals(this.sessiontoken, body15.sessiontoken) &&
        Objects.equals(this.todate, body15.todate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(callertype, customerid, deviceid, fromdate, iscbredeemlist, merId, mobileNumber, redeemId, restrictCount, restrictbycustomer, sessiontoken, todate);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Body15 {\n");
    
    sb.append("    callertype: ").append(toIndentedString(callertype)).append("\n");
    sb.append("    customerid: ").append(toIndentedString(customerid)).append("\n");
    sb.append("    deviceid: ").append(toIndentedString(deviceid)).append("\n");
    sb.append("    fromdate: ").append(toIndentedString(fromdate)).append("\n");
    sb.append("    iscbredeemlist: ").append(toIndentedString(iscbredeemlist)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    mobileNumber: ").append(toIndentedString(mobileNumber)).append("\n");
    sb.append("    redeemId: ").append(toIndentedString(redeemId)).append("\n");
    sb.append("    restrictCount: ").append(toIndentedString(restrictCount)).append("\n");
    sb.append("    restrictbycustomer: ").append(toIndentedString(restrictbycustomer)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
    sb.append("    todate: ").append(toIndentedString(todate)).append("\n");
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
