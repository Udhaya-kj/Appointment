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
 * AddCustomerResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class AddCustomerResponse {
  @SerializedName("status_code")
  private String statusCode = null;

  @SerializedName("status_msg")
  private String statusMsg = null;

  @SerializedName("cust_id")
  private String custId = null;

  public AddCustomerResponse statusCode(String statusCode) {
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

  public AddCustomerResponse statusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
    return this;
  }

   /**
   * Get statusMsg
   * @return statusMsg
  **/
   @Schema(description = "")
  public String getStatusMsg() {
    return statusMsg;
  }

  public void setStatusMsg(String statusMsg) {
    this.statusMsg = statusMsg;
  }

  public AddCustomerResponse custId(String custId) {
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


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AddCustomerResponse addCustomerResponse = (AddCustomerResponse) o;
    return Objects.equals(this.statusCode, addCustomerResponse.statusCode) &&
        Objects.equals(this.statusMsg, addCustomerResponse.statusMsg) &&
        Objects.equals(this.custId, addCustomerResponse.custId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMsg, custId);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AddCustomerResponse {\n");
    
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMsg: ").append(toIndentedString(statusMsg)).append("\n");
    sb.append("    custId: ").append(toIndentedString(custId)).append("\n");
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
