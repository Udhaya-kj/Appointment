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
 * InlineResponse200Cashcampaigns
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse200Cashcampaigns {
  @SerializedName("campaignid")
  private String campaignid = null;

  @SerializedName("topupamt")
  private String topupamt = null;

  @SerializedName("topupbonus")
  private String topupbonus = null;

  public InlineResponse200Cashcampaigns campaignid(String campaignid) {
    this.campaignid = campaignid;
    return this;
  }

   /**
   * Get campaignid
   * @return campaignid
  **/
 @Schema(description = "")
  public String getCampaignid() {
    return campaignid;
  }

  public void setCampaignid(String campaignid) {
    this.campaignid = campaignid;
  }

  public InlineResponse200Cashcampaigns topupamt(String topupamt) {
    this.topupamt = topupamt;
    return this;
  }

   /**
   * Get topupamt
   * @return topupamt
  **/
 @Schema(description = "")
  public String getTopupamt() {
    return topupamt;
  }

  public void setTopupamt(String topupamt) {
    this.topupamt = topupamt;
  }

  public InlineResponse200Cashcampaigns topupbonus(String topupbonus) {
    this.topupbonus = topupbonus;
    return this;
  }

   /**
   * Get topupbonus
   * @return topupbonus
  **/
 @Schema(description = "")
  public String getTopupbonus() {
    return topupbonus;
  }

  public void setTopupbonus(String topupbonus) {
    this.topupbonus = topupbonus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200Cashcampaigns inlineResponse200Cashcampaigns = (InlineResponse200Cashcampaigns) o;
    return Objects.equals(this.campaignid, inlineResponse200Cashcampaigns.campaignid) &&
        Objects.equals(this.topupamt, inlineResponse200Cashcampaigns.topupamt) &&
        Objects.equals(this.topupbonus, inlineResponse200Cashcampaigns.topupbonus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(campaignid, topupamt, topupbonus);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200Cashcampaigns {\n");
    
    sb.append("    campaignid: ").append(toIndentedString(campaignid)).append("\n");
    sb.append("    topupamt: ").append(toIndentedString(topupamt)).append("\n");
    sb.append("    topupbonus: ").append(toIndentedString(topupbonus)).append("\n");
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

