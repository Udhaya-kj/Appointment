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
 * InlineResponse2005
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse2005 {
  @SerializedName("otp_identifier")
  private String otpIdentifier = null;

  @SerializedName("request_result")
  private Boolean requestResult = null;

  @SerializedName("request_type")
  private String requestType = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public InlineResponse2005 otpIdentifier(String otpIdentifier) {
    this.otpIdentifier = otpIdentifier;
    return this;
  }

   /**
   * Get otpIdentifier
   * @return otpIdentifier
  **/
  @Schema(description = "")
  public String getOtpIdentifier() {
    return otpIdentifier;
  }

  public void setOtpIdentifier(String otpIdentifier) {
    this.otpIdentifier = otpIdentifier;
  }

  public InlineResponse2005 requestResult(Boolean requestResult) {
    this.requestResult = requestResult;
    return this;
  }

   /**
   * send true if request is fullfilled
   * @return requestResult
  **/
   @Schema(description = "")
  public Boolean isRequestResult() {
    return requestResult;
  }

  public void setRequestResult(Boolean requestResult) {
    this.requestResult = requestResult;
  }

  public InlineResponse2005 requestType(String requestType) {
    this.requestType = requestType;
    return this;
  }

   /**
   * Get requestType
   * @return requestType
  **/
  @Schema(description = "")
  public String getRequestType() {
    return requestType;
  }

  public void setRequestType(String requestType) {
    this.requestType = requestType;
  }

  public InlineResponse2005 sessiontoken(String sessiontoken) {
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
    InlineResponse2005 inlineResponse2005 = (InlineResponse2005) o;
    return Objects.equals(this.otpIdentifier, inlineResponse2005.otpIdentifier) &&
        Objects.equals(this.requestResult, inlineResponse2005.requestResult) &&
        Objects.equals(this.requestType, inlineResponse2005.requestType) &&
        Objects.equals(this.sessiontoken, inlineResponse2005.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(otpIdentifier, requestResult, requestType, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2005 {\n");
    
    sb.append("    otpIdentifier: ").append(toIndentedString(otpIdentifier)).append("\n");
    sb.append("    requestResult: ").append(toIndentedString(requestResult)).append("\n");
    sb.append("    requestType: ").append(toIndentedString(requestType)).append("\n");
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

