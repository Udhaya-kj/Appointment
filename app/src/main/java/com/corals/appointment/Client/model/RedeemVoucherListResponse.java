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
 * RedeemVoucherListResponse
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class RedeemVoucherListResponse {
  @SerializedName("status_code")
  private String statusCode = null;

  @SerializedName("status_message")
  private String statusMessage = null;

  @SerializedName("redeem_vouchers")
  private List<RedeemVoucher> redeemVouchers = null;

  public RedeemVoucherListResponse statusCode(String statusCode) {
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

  public RedeemVoucherListResponse statusMessage(String statusMessage) {
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

  public RedeemVoucherListResponse redeemVouchers(List<RedeemVoucher> redeemVouchers) {
    this.redeemVouchers = redeemVouchers;
    return this;
  }

  public RedeemVoucherListResponse addRedeemVouchersItem(RedeemVoucher redeemVouchersItem) {
    if (this.redeemVouchers == null) {
      this.redeemVouchers = new ArrayList<RedeemVoucher>();
    }
    this.redeemVouchers.add(redeemVouchersItem);
    return this;
  }

   /**
   * Get redeemVouchers
   * @return redeemVouchers
  **/
  @Schema(description = "")
  public List<RedeemVoucher> getRedeemVouchers() {
    return redeemVouchers;
  }

  public void setRedeemVouchers(List<RedeemVoucher> redeemVouchers) {
    this.redeemVouchers = redeemVouchers;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RedeemVoucherListResponse redeemVoucherListResponse = (RedeemVoucherListResponse) o;
    return Objects.equals(this.statusCode, redeemVoucherListResponse.statusCode) &&
        Objects.equals(this.statusMessage, redeemVoucherListResponse.statusMessage) &&
        Objects.equals(this.redeemVouchers, redeemVoucherListResponse.redeemVouchers);
  }

  @Override
  public int hashCode() {
    return Objects.hash(statusCode, statusMessage, redeemVouchers);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class RedeemVoucherListResponse {\n");
    
    sb.append("    statusCode: ").append(toIndentedString(statusCode)).append("\n");
    sb.append("    statusMessage: ").append(toIndentedString(statusMessage)).append("\n");
    sb.append("    redeemVouchers: ").append(toIndentedString(redeemVouchers)).append("\n");
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

