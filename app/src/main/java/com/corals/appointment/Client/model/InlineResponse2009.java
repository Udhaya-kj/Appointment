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
 * InlineResponse2009
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse2009 {
  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("outlets")
  private InlineResponse2009Outlets outlets = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public InlineResponse2009 merId(String merId) {
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

  public InlineResponse2009 outlets(InlineResponse2009Outlets outlets) {
    this.outlets = outlets;
    return this;
  }

   /**
   * Get outlets
   * @return outlets
  **/
  @Schema(description = "")
  public InlineResponse2009Outlets getOutlets() {
    return outlets;
  }

  public void setOutlets(InlineResponse2009Outlets outlets) {
    this.outlets = outlets;
  }

  public InlineResponse2009 sessiontoken(String sessiontoken) {
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
    InlineResponse2009 inlineResponse2009 = (InlineResponse2009) o;
    return Objects.equals(this.merId, inlineResponse2009.merId) &&
        Objects.equals(this.outlets, inlineResponse2009.outlets) &&
        Objects.equals(this.sessiontoken, inlineResponse2009.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merId, outlets, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2009 {\n");
    
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    outlets: ").append(toIndentedString(outlets)).append("\n");
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

