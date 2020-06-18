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
 * InlineResponse2003Outlets
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse2003Outlets {
  @SerializedName("geo_lat")
  private String geoLat = null;

  @SerializedName("geo_lon")
  private String geoLon = null;

  @SerializedName("outlet_add1")
  private String outletAdd1 = null;

  @SerializedName("outlet_add2")
  private String outletAdd2 = null;

  @SerializedName("outlet_add3")
  private String outletAdd3 = null;

  @SerializedName("outlet_id")
  private String outletId = null;

  @SerializedName("outlet_name")
  private String outletName = null;

  @SerializedName("outlet_reg_no")
  private String outletRegNo = null;

  @SerializedName("phone_no")
  private String phoneNo = null;

  @SerializedName("post_code")
  private String postCode = null;

  @SerializedName("qr_category_code")
  private String qrCategoryCode = null;

  @SerializedName("qr_display_name")
  private String qrDisplayName = null;

  @SerializedName("qr_mer_inf_node")
  private String qrMerInfNode = null;

  @SerializedName("qr_mer_inf_unique_cd")
  private String qrMerInfUniqueCd = null;

  @SerializedName("qr_txn_country")
  private String qrTxnCountry = null;

  @SerializedName("qr_txn_currency")
  private String qrTxnCurrency = null;

  public InlineResponse2003Outlets geoLat(String geoLat) {
    this.geoLat = geoLat;
    return this;
  }

   /**
   * Get geoLat
   * @return geoLat
  **/
  @Schema(description = "")
  public String getGeoLat() {
    return geoLat;
  }

  public void setGeoLat(String geoLat) {
    this.geoLat = geoLat;
  }

  public InlineResponse2003Outlets geoLon(String geoLon) {
    this.geoLon = geoLon;
    return this;
  }

   /**
   * Get geoLon
   * @return geoLon
  **/
  @Schema(description = "")
  public String getGeoLon() {
    return geoLon;
  }

  public void setGeoLon(String geoLon) {
    this.geoLon = geoLon;
  }

  public InlineResponse2003Outlets outletAdd1(String outletAdd1) {
    this.outletAdd1 = outletAdd1;
    return this;
  }

   /**
   * Get outletAdd1
   * @return outletAdd1
  **/
  @Schema(description = "")
  public String getOutletAdd1() {
    return outletAdd1;
  }

  public void setOutletAdd1(String outletAdd1) {
    this.outletAdd1 = outletAdd1;
  }

  public InlineResponse2003Outlets outletAdd2(String outletAdd2) {
    this.outletAdd2 = outletAdd2;
    return this;
  }

   /**
   * Get outletAdd2
   * @return outletAdd2
  **/
  @Schema(description = "")
  public String getOutletAdd2() {
    return outletAdd2;
  }

  public void setOutletAdd2(String outletAdd2) {
    this.outletAdd2 = outletAdd2;
  }

  public InlineResponse2003Outlets outletAdd3(String outletAdd3) {
    this.outletAdd3 = outletAdd3;
    return this;
  }

   /**
   * Get outletAdd3
   * @return outletAdd3
  **/
  @Schema(description = "")
  public String getOutletAdd3() {
    return outletAdd3;
  }

  public void setOutletAdd3(String outletAdd3) {
    this.outletAdd3 = outletAdd3;
  }

  public InlineResponse2003Outlets outletId(String outletId) {
    this.outletId = outletId;
    return this;
  }

   /**
   * Get outletId
   * @return outletId
  **/
  @Schema(description = "")
  public String getOutletId() {
    return outletId;
  }

  public void setOutletId(String outletId) {
    this.outletId = outletId;
  }

  public InlineResponse2003Outlets outletName(String outletName) {
    this.outletName = outletName;
    return this;
  }

   /**
   * Get outletName
   * @return outletName
  **/
  @Schema(description = "")
  public String getOutletName() {
    return outletName;
  }

  public void setOutletName(String outletName) {
    this.outletName = outletName;
  }

  public InlineResponse2003Outlets outletRegNo(String outletRegNo) {
    this.outletRegNo = outletRegNo;
    return this;
  }

   /**
   * Get outletRegNo
   * @return outletRegNo
  **/
  @Schema(description = "")
  public String getOutletRegNo() {
    return outletRegNo;
  }

  public void setOutletRegNo(String outletRegNo) {
    this.outletRegNo = outletRegNo;
  }

  public InlineResponse2003Outlets phoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
    return this;
  }

   /**
   * Get phoneNo
   * @return phoneNo
  **/
  @Schema(description = "")
  public String getPhoneNo() {
    return phoneNo;
  }

  public void setPhoneNo(String phoneNo) {
    this.phoneNo = phoneNo;
  }

  public InlineResponse2003Outlets postCode(String postCode) {
    this.postCode = postCode;
    return this;
  }

   /**
   * Get postCode
   * @return postCode
  **/
  @Schema(description = "")
  public String getPostCode() {
    return postCode;
  }

  public void setPostCode(String postCode) {
    this.postCode = postCode;
  }

  public InlineResponse2003Outlets qrCategoryCode(String qrCategoryCode) {
    this.qrCategoryCode = qrCategoryCode;
    return this;
  }

   /**
   * Get qrCategoryCode
   * @return qrCategoryCode
  **/
  @Schema(description = "")
  public String getQrCategoryCode() {
    return qrCategoryCode;
  }

  public void setQrCategoryCode(String qrCategoryCode) {
    this.qrCategoryCode = qrCategoryCode;
  }

  public InlineResponse2003Outlets qrDisplayName(String qrDisplayName) {
    this.qrDisplayName = qrDisplayName;
    return this;
  }

   /**
   * Get qrDisplayName
   * @return qrDisplayName
  **/
  @Schema(description = "")
  public String getQrDisplayName() {
    return qrDisplayName;
  }

  public void setQrDisplayName(String qrDisplayName) {
    this.qrDisplayName = qrDisplayName;
  }

  public InlineResponse2003Outlets qrMerInfNode(String qrMerInfNode) {
    this.qrMerInfNode = qrMerInfNode;
    return this;
  }

   /**
   * Get qrMerInfNode
   * @return qrMerInfNode
  **/
  @Schema(description = "")
  public String getQrMerInfNode() {
    return qrMerInfNode;
  }

  public void setQrMerInfNode(String qrMerInfNode) {
    this.qrMerInfNode = qrMerInfNode;
  }

  public InlineResponse2003Outlets qrMerInfUniqueCd(String qrMerInfUniqueCd) {
    this.qrMerInfUniqueCd = qrMerInfUniqueCd;
    return this;
  }

   /**
   * Get qrMerInfUniqueCd
   * @return qrMerInfUniqueCd
  **/
  @Schema(description = "")
  public String getQrMerInfUniqueCd() {
    return qrMerInfUniqueCd;
  }

  public void setQrMerInfUniqueCd(String qrMerInfUniqueCd) {
    this.qrMerInfUniqueCd = qrMerInfUniqueCd;
  }

  public InlineResponse2003Outlets qrTxnCountry(String qrTxnCountry) {
    this.qrTxnCountry = qrTxnCountry;
    return this;
  }

   /**
   * Get qrTxnCountry
   * @return qrTxnCountry
  **/
  @Schema(description = "")
  public String getQrTxnCountry() {
    return qrTxnCountry;
  }

  public void setQrTxnCountry(String qrTxnCountry) {
    this.qrTxnCountry = qrTxnCountry;
  }

  public InlineResponse2003Outlets qrTxnCurrency(String qrTxnCurrency) {
    this.qrTxnCurrency = qrTxnCurrency;
    return this;
  }

   /**
   * Get qrTxnCurrency
   * @return qrTxnCurrency
  **/
  @Schema(description = "")
  public String getQrTxnCurrency() {
    return qrTxnCurrency;
  }

  public void setQrTxnCurrency(String qrTxnCurrency) {
    this.qrTxnCurrency = qrTxnCurrency;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2003Outlets inlineResponse2003Outlets = (InlineResponse2003Outlets) o;
    return Objects.equals(this.geoLat, inlineResponse2003Outlets.geoLat) &&
        Objects.equals(this.geoLon, inlineResponse2003Outlets.geoLon) &&
        Objects.equals(this.outletAdd1, inlineResponse2003Outlets.outletAdd1) &&
        Objects.equals(this.outletAdd2, inlineResponse2003Outlets.outletAdd2) &&
        Objects.equals(this.outletAdd3, inlineResponse2003Outlets.outletAdd3) &&
        Objects.equals(this.outletId, inlineResponse2003Outlets.outletId) &&
        Objects.equals(this.outletName, inlineResponse2003Outlets.outletName) &&
        Objects.equals(this.outletRegNo, inlineResponse2003Outlets.outletRegNo) &&
        Objects.equals(this.phoneNo, inlineResponse2003Outlets.phoneNo) &&
        Objects.equals(this.postCode, inlineResponse2003Outlets.postCode) &&
        Objects.equals(this.qrCategoryCode, inlineResponse2003Outlets.qrCategoryCode) &&
        Objects.equals(this.qrDisplayName, inlineResponse2003Outlets.qrDisplayName) &&
        Objects.equals(this.qrMerInfNode, inlineResponse2003Outlets.qrMerInfNode) &&
        Objects.equals(this.qrMerInfUniqueCd, inlineResponse2003Outlets.qrMerInfUniqueCd) &&
        Objects.equals(this.qrTxnCountry, inlineResponse2003Outlets.qrTxnCountry) &&
        Objects.equals(this.qrTxnCurrency, inlineResponse2003Outlets.qrTxnCurrency);
  }

  @Override
  public int hashCode() {
    return Objects.hash(geoLat, geoLon, outletAdd1, outletAdd2, outletAdd3, outletId, outletName, outletRegNo, phoneNo, postCode, qrCategoryCode, qrDisplayName, qrMerInfNode, qrMerInfUniqueCd, qrTxnCountry, qrTxnCurrency);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2003Outlets {\n");
    
    sb.append("    geoLat: ").append(toIndentedString(geoLat)).append("\n");
    sb.append("    geoLon: ").append(toIndentedString(geoLon)).append("\n");
    sb.append("    outletAdd1: ").append(toIndentedString(outletAdd1)).append("\n");
    sb.append("    outletAdd2: ").append(toIndentedString(outletAdd2)).append("\n");
    sb.append("    outletAdd3: ").append(toIndentedString(outletAdd3)).append("\n");
    sb.append("    outletId: ").append(toIndentedString(outletId)).append("\n");
    sb.append("    outletName: ").append(toIndentedString(outletName)).append("\n");
    sb.append("    outletRegNo: ").append(toIndentedString(outletRegNo)).append("\n");
    sb.append("    phoneNo: ").append(toIndentedString(phoneNo)).append("\n");
    sb.append("    postCode: ").append(toIndentedString(postCode)).append("\n");
    sb.append("    qrCategoryCode: ").append(toIndentedString(qrCategoryCode)).append("\n");
    sb.append("    qrDisplayName: ").append(toIndentedString(qrDisplayName)).append("\n");
    sb.append("    qrMerInfNode: ").append(toIndentedString(qrMerInfNode)).append("\n");
    sb.append("    qrMerInfUniqueCd: ").append(toIndentedString(qrMerInfUniqueCd)).append("\n");
    sb.append("    qrTxnCountry: ").append(toIndentedString(qrTxnCountry)).append("\n");
    sb.append("    qrTxnCurrency: ").append(toIndentedString(qrTxnCurrency)).append("\n");
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

