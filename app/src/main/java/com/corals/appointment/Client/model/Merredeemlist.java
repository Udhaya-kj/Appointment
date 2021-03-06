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
 * Merredeemlist
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class Merredeemlist {
  @SerializedName("cbpoints_expdt")
  private String cbpointsExpdt = null;

  @SerializedName("custpointsbal")
  private String custpointsbal = null;

  @SerializedName("redemption_list")
  private List<RedemptionList> redemptionList = null;

  public Merredeemlist cbpointsExpdt(String cbpointsExpdt) {
    this.cbpointsExpdt = cbpointsExpdt;
    return this;
  }

   /**
   * Get cbpointsExpdt
   * @return cbpointsExpdt
  **/
  @Schema(description = "")
  public String getCbpointsExpdt() {
    return cbpointsExpdt;
  }

  public void setCbpointsExpdt(String cbpointsExpdt) {
    this.cbpointsExpdt = cbpointsExpdt;
  }

  public Merredeemlist custpointsbal(String custpointsbal) {
    this.custpointsbal = custpointsbal;
    return this;
  }

   /**
   * Get custpointsbal
   * @return custpointsbal
  **/
  @Schema(description = "")
  public String getCustpointsbal() {
    return custpointsbal;
  }

  public void setCustpointsbal(String custpointsbal) {
    this.custpointsbal = custpointsbal;
  }

  public Merredeemlist redemptionList(List<RedemptionList> redemptionList) {
    this.redemptionList = redemptionList;
    return this;
  }

  public Merredeemlist addRedemptionListItem(RedemptionList redemptionListItem) {
    if (this.redemptionList == null) {
      this.redemptionList = new ArrayList<RedemptionList>();
    }
    this.redemptionList.add(redemptionListItem);
    return this;
  }

   /**
   * Get redemptionList
   * @return redemptionList
  **/
  @Schema(description = "")
  public List<RedemptionList> getRedemptionList() {
    return redemptionList;
  }

  public void setRedemptionList(List<RedemptionList> redemptionList) {
    this.redemptionList = redemptionList;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Merredeemlist merredeemlist = (Merredeemlist) o;
    return Objects.equals(this.cbpointsExpdt, merredeemlist.cbpointsExpdt) &&
        Objects.equals(this.custpointsbal, merredeemlist.custpointsbal) &&
        Objects.equals(this.redemptionList, merredeemlist.redemptionList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cbpointsExpdt, custpointsbal, redemptionList);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Merredeemlist {\n");
    
    sb.append("    cbpointsExpdt: ").append(toIndentedString(cbpointsExpdt)).append("\n");
    sb.append("    custpointsbal: ").append(toIndentedString(custpointsbal)).append("\n");
    sb.append("    redemptionList: ").append(toIndentedString(redemptionList)).append("\n");
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

