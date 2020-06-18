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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * InlineResponse2008
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse2008 {
  @SerializedName("mer_qrs")
  private List<InlineResponse2008MerQrs> merQrs = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public InlineResponse2008 merQrs(List<InlineResponse2008MerQrs> merQrs) {
    this.merQrs = merQrs;
    return this;
  }

  public InlineResponse2008 addMerQrsItem(InlineResponse2008MerQrs merQrsItem) {
    if (this.merQrs == null) {
      this.merQrs = new ArrayList<InlineResponse2008MerQrs>();
    }
    this.merQrs.add(merQrsItem);
    return this;
  }

   /**
   * Get merQrs
   * @return merQrs
  **/
  @Schema(description = "")
  public List<InlineResponse2008MerQrs> getMerQrs() {
    return merQrs;
  }

  public void setMerQrs(List<InlineResponse2008MerQrs> merQrs) {
    this.merQrs = merQrs;
  }

  public InlineResponse2008 sessiontoken(String sessiontoken) {
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
    InlineResponse2008 inlineResponse2008 = (InlineResponse2008) o;
    return Objects.equals(this.merQrs, inlineResponse2008.merQrs) &&
        Objects.equals(this.sessiontoken, inlineResponse2008.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(merQrs, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2008 {\n");
    
    sb.append("    merQrs: ").append(toIndentedString(merQrs)).append("\n");
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
