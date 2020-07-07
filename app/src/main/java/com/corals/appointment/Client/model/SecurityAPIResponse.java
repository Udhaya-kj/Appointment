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
 * SecurityAPIResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-03T07:40:49.972Z")
public class SecurityAPIResponse {
  @SerializedName("status_code")
  private String statusCode = null;

  @SerializedName("status_message")
  private String statusMessage = null;

  @SerializedName("session_token")
  private String sessionToken = null;

  @SerializedName("user_id")
  private String userId = null;

  @SerializedName("mer_cur_symbol")
  private String merCurSymbol = null;

  @SerializedName("country_code")
  private String countryCode = null;

  @SerializedName("biz_display_name")
  private String bizDisplayName = null;

  public SecurityAPIResponse statusCode(String statusCode) {
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

  public SecurityAPIResponse statusMessage(String statusMessage) {
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

  public SecurityAPIResponse sessionToken(String sessionToken) {
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

  public SecurityAPIResponse userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * @return userId
   **/
  @Schema(description = "")
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public SecurityAPIResponse merCurSymbol(String merCurSymbol) {
    this.merCurSymbol = merCurSymbol;
    return this;
  }

  /**
   * Get merCurSymbol
   * @return merCurSymbol
   **/
  @Schema(description = "")
  public String getMerCurSymbol() {
    return merCurSymbol;
  }

  public void setMerCurSymbol(String merCurSymbol) {
    this.merCurSymbol = merCurSymbol;
  }

  public SecurityAPIResponse countryCode(String countryCode) {
    this.countryCode = countryCode;
    return this;
  }

  /**
   * Get countryCode
   * @return countryCode
   **/
  @Schema(description = "")
  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public SecurityAPIResponse bizDisplayName(String bizDisplayName) {
    this.bizDisplayName = bizDisplayName;
    return this;
  }

  /**
   * Get bizDisplayName
   * @return bizDisplayName
   **/
  @Schema(description = "")
  public String getBizDisplayName() {
    return bizDisplayName;
  }

  public void setBizDisplayName(String bizDisplayName) {
    this.bizDisplayName = bizDisplayName;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SecurityAPIResponse securityAPIResponse = (SecurityAPIResponse) o;
    return Objects.equals(this.statusCode, securityAPIResponse.statusCode) &&
            Objects.equals(this.statusMessage, securityAPIResponse.statusMessage) &&
            Objects.equals(this.sessionToken, securityAPIResponse.sessionToken) &&
            Objects.equals(this.userId, securityAPIResponse.userId) &&
            Objects.equals(this.merCurSymbol, securityAPIResponse.merCurSymbol) &&
            Objects.equals(this.countryCode, securityAPIResponse.countryCode) &&
            Objects.equals(this.bizDisplayName, securityAPIResponse.bizDisplayName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMessage, sessionToken, userId, merCurSymbol, countryCode, bizDisplayName);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class SecurityAPIResponse {\n");

    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    sessionToken: ").append(toIndentedString(sessionToken)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
    sb.append("    merCurSymbol: ").append(toIndentedString(merCurSymbol)).append("\n");
    sb.append("    countryCode: ").append(toIndentedString(countryCode)).append("\n");
    sb.append("    bizDisplayName: ").append(toIndentedString(bizDisplayName)).append("\n");
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

