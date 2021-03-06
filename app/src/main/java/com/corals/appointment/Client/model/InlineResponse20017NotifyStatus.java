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
 * InlineResponse20017NotifyStatus
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse20017NotifyStatus {
  @SerializedName("cust_id")
  private String custId = null;

  @SerializedName("error_msg")
  private String errorMsg = null;

  @SerializedName("notify_id")
  private String notifyId = null;

  @SerializedName("sent_status")
  private String sentStatus = null;

  public InlineResponse20017NotifyStatus custId(String custId) {
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

  public InlineResponse20017NotifyStatus errorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
    return this;
  }

   /**
   * Get errorMsg
   * @return errorMsg
  **/
  @Schema(description = "")
  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }

  public InlineResponse20017NotifyStatus notifyId(String notifyId) {
    this.notifyId = notifyId;
    return this;
  }

   /**
   * Get notifyId
   * @return notifyId
  **/
  @Schema(description = "")
  public String getNotifyId() {
    return notifyId;
  }

  public void setNotifyId(String notifyId) {
    this.notifyId = notifyId;
  }

  public InlineResponse20017NotifyStatus sentStatus(String sentStatus) {
    this.sentStatus = sentStatus;
    return this;
  }

   /**
   * Get sentStatus
   * @return sentStatus
  **/
  @Schema(description = "")
  public String getSentStatus() {
    return sentStatus;
  }

  public void setSentStatus(String sentStatus) {
    this.sentStatus = sentStatus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse20017NotifyStatus inlineResponse20017NotifyStatus = (InlineResponse20017NotifyStatus) o;
    return Objects.equals(this.custId, inlineResponse20017NotifyStatus.custId) &&
        Objects.equals(this.errorMsg, inlineResponse20017NotifyStatus.errorMsg) &&
        Objects.equals(this.notifyId, inlineResponse20017NotifyStatus.notifyId) &&
        Objects.equals(this.sentStatus, inlineResponse20017NotifyStatus.sentStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(custId, errorMsg, notifyId, sentStatus);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20017NotifyStatus {\n");
    
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
    sb.append("    errorMsg: ").append(toIndentedString(errorMsg)).append("\n");
    sb.append("    notifyId: ").append(toIndentedString(notifyId)).append("\n");
    sb.append("    sentStatus: ").append(toIndentedString(sentStatus)).append("\n");
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

