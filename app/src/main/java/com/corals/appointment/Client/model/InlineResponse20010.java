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
 * InlineResponse20010
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse20010 {
  @SerializedName("cashcampaigns")
  private List<InlineResponse20010Cashcampaigns> cashcampaigns = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  public InlineResponse20010 cashcampaigns(List<InlineResponse20010Cashcampaigns> cashcampaigns) {
    this.cashcampaigns = cashcampaigns;
    return this;
  }

  public InlineResponse20010 addCashcampaignsItem(InlineResponse20010Cashcampaigns cashcampaignsItem) {
    if (this.cashcampaigns == null) {
      this.cashcampaigns = new ArrayList<InlineResponse20010Cashcampaigns>();
    }
    this.cashcampaigns.add(cashcampaignsItem);
    return this;
  }

   /**
   * Get cashcampaigns
   * @return cashcampaigns
  **/
  @Schema(description = "")
  public List<InlineResponse20010Cashcampaigns> getCashcampaigns() {
    return cashcampaigns;
  }

  public void setCashcampaigns(List<InlineResponse20010Cashcampaigns> cashcampaigns) {
    this.cashcampaigns = cashcampaigns;
  }

  public InlineResponse20010 sessiontoken(String sessiontoken) {
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
    InlineResponse20010 inlineResponse20010 = (InlineResponse20010) o;
    return Objects.equals(this.cashcampaigns, inlineResponse20010.cashcampaigns) &&
        Objects.equals(this.sessiontoken, inlineResponse20010.sessiontoken);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cashcampaigns, sessiontoken);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20010 {\n");
    
    sb.append("    cashcampaigns: ").append(toIndentedString(cashcampaigns)).append("\n");
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

