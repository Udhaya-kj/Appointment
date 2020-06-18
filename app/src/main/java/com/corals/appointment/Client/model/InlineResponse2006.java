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
 * InlineResponse2006
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse2006 {
  @SerializedName("outlet_id")
  private String outletId = null;

  @SerializedName("pay_amt")
  private String payAmt = null;

  @SerializedName("request_result")
  private Boolean requestResult = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  @SerializedName("txn_ref_no")
  private String txnRefNo = null;

  @SerializedName("wallet_bal")
  private String walletBal = null;

  public InlineResponse2006 outletId(String outletId) {
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

  public InlineResponse2006 payAmt(String payAmt) {
    this.payAmt = payAmt;
    return this;
  }

   /**
   * Get payAmt
   * @return payAmt
  **/
  @Schema(description = "")
  public String getPayAmt() {
    return payAmt;
  }

  public void setPayAmt(String payAmt) {
    this.payAmt = payAmt;
  }

  public InlineResponse2006 requestResult(Boolean requestResult) {
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

  public InlineResponse2006 sessiontoken(String sessiontoken) {
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

  public InlineResponse2006 txnRefNo(String txnRefNo) {
    this.txnRefNo = txnRefNo;
    return this;
  }

   /**
   * tbl_cust_txn_ledger
   * @return txnRefNo
  **/
   @Schema(description = "")
  public String getTxnRefNo() {
    return txnRefNo;
  }

  public void setTxnRefNo(String txnRefNo) {
    this.txnRefNo = txnRefNo;
  }

  public InlineResponse2006 walletBal(String walletBal) {
    this.walletBal = walletBal;
    return this;
  }

   /**
   * tbl_cust_txn_ledger
   * @return walletBal
  **/
   @Schema(description = "")
  public String getWalletBal() {
    return walletBal;
  }

  public void setWalletBal(String walletBal) {
    this.walletBal = walletBal;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse2006 inlineResponse2006 = (InlineResponse2006) o;
    return Objects.equals(this.outletId, inlineResponse2006.outletId) &&
        Objects.equals(this.payAmt, inlineResponse2006.payAmt) &&
        Objects.equals(this.requestResult, inlineResponse2006.requestResult) &&
        Objects.equals(this.sessiontoken, inlineResponse2006.sessiontoken) &&
        Objects.equals(this.txnRefNo, inlineResponse2006.txnRefNo) &&
        Objects.equals(this.walletBal, inlineResponse2006.walletBal);
  }

  @Override
  public int hashCode() {
    return Objects.hash(outletId, payAmt, requestResult, sessiontoken, txnRefNo, walletBal);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse2006 {\n");
    
    sb.append("    outletId: ").append(toIndentedString(outletId)).append("\n");
    sb.append("    payAmt: ").append(toIndentedString(payAmt)).append("\n");
    sb.append("    requestResult: ").append(toIndentedString(requestResult)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
    sb.append("    txnRefNo: ").append(toIndentedString(txnRefNo)).append("\n");
    sb.append("    walletBal: ").append(toIndentedString(walletBal)).append("\n");
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

