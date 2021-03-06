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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;


/**
 * InlineResponse200
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse200 {
  @SerializedName("bizdisplayname")
  private String bizdisplayname = null;

  @SerializedName("cashcampaigns")
  private List<InlineResponse200Cashcampaigns> cashcampaigns = null;

  @SerializedName("currencysymbol")
  private String currencysymbol = null;

  @SerializedName("is_cb_enrolled")
  private String isCbEnrolled = null;

  @SerializedName("isadmin")
  private Boolean isadmin = null;

  @SerializedName("mer_country")
  private String merCountry = null;

  @SerializedName("mer_email_add")
  private String merEmailAdd = null;

  @SerializedName("mer_id")
  private String merId = null;

  @SerializedName("monthlypaydueday")
  private BigDecimal monthlypaydueday = null;

  @SerializedName("outlet_id")
  private String outletId = null;

  @SerializedName("outlets")
  private List<InlineResponse200Outlets> outlets = null;

  @SerializedName("paymentdueamt")
  private String paymentdueamt = null;

  @SerializedName("paymentremindpush")
  private Boolean paymentremindpush = null;

  @SerializedName("paymentremindpushtier")
  private String paymentremindpushtier = null;

  @SerializedName("unique_id")
  private String uniqueId = null;

  @SerializedName("role_type")
  private String roleType = null;

  @SerializedName("sessiontoken")
  private String sessiontoken = null;

  @SerializedName("tokenexpiry")
  private String tokenexpiry = null;

  @SerializedName("tot_customers")
  private String totCustomers = null;

  @SerializedName("wallet_balance")
  private String walletBalance = null;

  public InlineResponse200 bizdisplayname(String bizdisplayname) {
    this.bizdisplayname = bizdisplayname;
    return this;
  }

   /**
   * Get bizdisplayname
   * @return bizdisplayname
  **/
 @Schema(description = "")
  public String getBizdisplayname() {
    return bizdisplayname;
  }

  public void setBizdisplayname(String bizdisplayname) {
    this.bizdisplayname = bizdisplayname;
  }

  public InlineResponse200 cashcampaigns(List<InlineResponse200Cashcampaigns> cashcampaigns) {
    this.cashcampaigns = cashcampaigns;
    return this;
  }

  public InlineResponse200 addCashcampaignsItem(InlineResponse200Cashcampaigns cashcampaignsItem) {
    if (this.cashcampaigns == null) {
      this.cashcampaigns = new ArrayList<InlineResponse200Cashcampaigns>();
    }
    this.cashcampaigns.add(cashcampaignsItem);
    return this;
  }

   /**
   * Get cashcampaigns
   * @return cashcampaigns
  **/
 @Schema(description = "")
  public List<InlineResponse200Cashcampaigns> getCashcampaigns() {
    return cashcampaigns;
  }

  public void setCashcampaigns(List<InlineResponse200Cashcampaigns> cashcampaigns) {
    this.cashcampaigns = cashcampaigns;
  }

  public InlineResponse200 currencysymbol(String currencysymbol) {
    this.currencysymbol = currencysymbol;
    return this;
  }

   /**
   * Get currencysymbol
   * @return currencysymbol
  **/
 @Schema(description = "")
  public String getCurrencysymbol() {
    return currencysymbol;
  }

  public void setCurrencysymbol(String currencysymbol) {
    this.currencysymbol = currencysymbol;
  }

  public InlineResponse200 isCbEnrolled(String isCbEnrolled) {
    this.isCbEnrolled = isCbEnrolled;
    return this;
  }

   /**
   * Get isCbEnrolled
   * @return isCbEnrolled
  **/
 @Schema(description = "")
  public String getIsCbEnrolled() {
    return isCbEnrolled;
  }

  public void setIsCbEnrolled(String isCbEnrolled) {
    this.isCbEnrolled = isCbEnrolled;
  }

  public InlineResponse200 isadmin(Boolean isadmin) {
    this.isadmin = isadmin;
    return this;
  }

   /**
   * Get isadmin
   * @return isadmin
  **/
   @Schema(description = "")
  public Boolean isIsadmin() {
    return isadmin;
  }

  public void setIsadmin(Boolean isadmin) {
    this.isadmin = isadmin;
  }

  public InlineResponse200 merCountry(String merCountry) {
    this.merCountry = merCountry;
    return this;
  }

   /**
   * Get merCountry
   * @return merCountry
  **/
 @Schema(description = "")
  public String getMerCountry() {
    return merCountry;
  }

  public void setMerCountry(String merCountry) {
    this.merCountry = merCountry;
  }

  public InlineResponse200 merEmailAdd(String merEmailAdd) {
    this.merEmailAdd = merEmailAdd;
    return this;
  }

   /**
   * Get merEmailAdd
   * @return merEmailAdd
  **/
 @Schema(description = "")
  public String getMerEmailAdd() {
    return merEmailAdd;
  }

  public void setMerEmailAdd(String merEmailAdd) {
    this.merEmailAdd = merEmailAdd;
  }

  public InlineResponse200 merId(String merId) {
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

  public InlineResponse200 monthlypaydueday(BigDecimal monthlypaydueday) {
    this.monthlypaydueday = monthlypaydueday;
    return this;
  }

   /**
   * On or after this day each month, payment prompt to be displayed. See screen flow for prompt
   * @return monthlypaydueday
  **/
   @Schema(description = "")
  public BigDecimal getMonthlypaydueday() {
    return monthlypaydueday;
  }

  public void setMonthlypaydueday(BigDecimal monthlypaydueday) {
    this.monthlypaydueday = monthlypaydueday;
  }

  public InlineResponse200 outletId(String outletId) {
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

  public InlineResponse200 outlets(List<InlineResponse200Outlets> outlets) {
    this.outlets = outlets;
    return this;
  }

  public InlineResponse200 addOutletsItem(InlineResponse200Outlets outletsItem) {
    if (this.outlets == null) {
      this.outlets = new ArrayList<InlineResponse200Outlets>();
    }
    this.outlets.add(outletsItem);
    return this;
  }

   /**
   * Get outlets
   * @return outlets
  **/
 @Schema(description = "")
  public List<InlineResponse200Outlets> getOutlets() {
    return outlets;
  }

  public void setOutlets(List<InlineResponse200Outlets> outlets) {
    this.outlets = outlets;
  }

  public InlineResponse200 paymentdueamt(String paymentdueamt) {
    this.paymentdueamt = paymentdueamt;
    return this;
  }

   /**
   * mandatory on or after payment due date
   * @return paymentdueamt
  **/
   @Schema(description = "")
  public String getPaymentdueamt() {
    return paymentdueamt;
  }

  public void setPaymentdueamt(String paymentdueamt) {
    this.paymentdueamt = paymentdueamt;
  }

  public InlineResponse200 paymentremindpush(Boolean paymentremindpush) {
    this.paymentremindpush = paymentremindpush;
    return this;
  }

   /**
   * Get paymentremindpush
   * @return paymentremindpush
  **/
   @Schema(description = "")
  public Boolean isPaymentremindpush() {
    return paymentremindpush;
  }

  public void setPaymentremindpush(Boolean paymentremindpush) {
    this.paymentremindpush = paymentremindpush;
  }

  public InlineResponse200 paymentremindpushtier(String paymentremindpushtier) {
    this.paymentremindpushtier = paymentremindpushtier;
    return this;
  }

   /**
   * Get paymentremindpushtier
   * @return paymentremindpushtier
  **/
 @Schema(description = "")
  public String getPaymentremindpushtier() {
    return paymentremindpushtier;
  }

  public void setPaymentremindpushtier(String paymentremindpushtier) {
    this.paymentremindpushtier = paymentremindpushtier;
  }

  public InlineResponse200 uniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
    return this;
  }

   /**
   * Get uniqueId
   * @return uniqueId
  **/
 @Schema(description = "")
  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public InlineResponse200 roleType(String roleType) {
    this.roleType = roleType;
    return this;
  }

   /**
   * Get roleType
   * @return roleType
  **/
 @Schema(description = "")
  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
  }

  public InlineResponse200 sessiontoken(String sessiontoken) {
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

  public InlineResponse200 tokenexpiry(String tokenexpiry) {
    this.tokenexpiry = tokenexpiry;
    return this;
  }

   /**
   * Get tokenexpiry
   * @return tokenexpiry
  **/
 @Schema(description = "")
  public String getTokenexpiry() {
    return tokenexpiry;
  }

  public void setTokenexpiry(String tokenexpiry) {
    this.tokenexpiry = tokenexpiry;
  }

  public InlineResponse200 totCustomers(String totCustomers) {
    this.totCustomers = totCustomers;
    return this;
  }

   /**
   * Get totCustomers
   * @return totCustomers
  **/
 @Schema(description = "")
  public String getTotCustomers() {
    return totCustomers;
  }

  public void setTotCustomers(String totCustomers) {
    this.totCustomers = totCustomers;
  }

  public InlineResponse200 walletBalance(String walletBalance) {
    this.walletBalance = walletBalance;
    return this;
  }

   /**
   * Get walletBalance
   * @return walletBalance
  **/
 @Schema(description = "")
  public String getWalletBalance() {
    return walletBalance;
  }

  public void setWalletBalance(String walletBalance) {
    this.walletBalance = walletBalance;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse200 inlineResponse200 = (InlineResponse200) o;
    return Objects.equals(this.bizdisplayname, inlineResponse200.bizdisplayname) &&
        Objects.equals(this.cashcampaigns, inlineResponse200.cashcampaigns) &&
        Objects.equals(this.currencysymbol, inlineResponse200.currencysymbol) &&
        Objects.equals(this.isCbEnrolled, inlineResponse200.isCbEnrolled) &&
        Objects.equals(this.isadmin, inlineResponse200.isadmin) &&
        Objects.equals(this.merCountry, inlineResponse200.merCountry) &&
        Objects.equals(this.merEmailAdd, inlineResponse200.merEmailAdd) &&
        Objects.equals(this.merId, inlineResponse200.merId) &&
        Objects.equals(this.monthlypaydueday, inlineResponse200.monthlypaydueday) &&
        Objects.equals(this.outletId, inlineResponse200.outletId) &&
        Objects.equals(this.outlets, inlineResponse200.outlets) &&
        Objects.equals(this.paymentdueamt, inlineResponse200.paymentdueamt) &&
        Objects.equals(this.paymentremindpush, inlineResponse200.paymentremindpush) &&
        Objects.equals(this.paymentremindpushtier, inlineResponse200.paymentremindpushtier) &&
        Objects.equals(this.uniqueId, inlineResponse200.uniqueId) &&
        Objects.equals(this.roleType, inlineResponse200.roleType) &&
        Objects.equals(this.sessiontoken, inlineResponse200.sessiontoken) &&
        Objects.equals(this.tokenexpiry, inlineResponse200.tokenexpiry) &&
        Objects.equals(this.totCustomers, inlineResponse200.totCustomers) &&
        Objects.equals(this.walletBalance, inlineResponse200.walletBalance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(bizdisplayname, cashcampaigns, currencysymbol, isCbEnrolled, isadmin, merCountry, merEmailAdd, merId, monthlypaydueday, outletId, outlets, paymentdueamt, paymentremindpush, paymentremindpushtier, uniqueId, roleType, sessiontoken, tokenexpiry, totCustomers, walletBalance);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse200 {\n");
    
    sb.append("    bizdisplayname: ").append(toIndentedString(bizdisplayname)).append("\n");
    sb.append("    cashcampaigns: ").append(toIndentedString(cashcampaigns)).append("\n");
    sb.append("    currencysymbol: ").append(toIndentedString(currencysymbol)).append("\n");
    sb.append("    isCbEnrolled: ").append(toIndentedString(isCbEnrolled)).append("\n");
    sb.append("    isadmin: ").append(toIndentedString(isadmin)).append("\n");
    sb.append("    merCountry: ").append(toIndentedString(merCountry)).append("\n");
    sb.append("    merEmailAdd: ").append(toIndentedString(merEmailAdd)).append("\n");
    sb.append("    merId: ").append(toIndentedString(merId)).append("\n");
    sb.append("    monthlypaydueday: ").append(toIndentedString(monthlypaydueday)).append("\n");
    sb.append("    outletId: ").append(toIndentedString(outletId)).append("\n");
    sb.append("    outlets: ").append(toIndentedString(outlets)).append("\n");
    sb.append("    paymentdueamt: ").append(toIndentedString(paymentdueamt)).append("\n");
    sb.append("    paymentremindpush: ").append(toIndentedString(paymentremindpush)).append("\n");
    sb.append("    paymentremindpushtier: ").append(toIndentedString(paymentremindpushtier)).append("\n");
    sb.append("    uniqueId: ").append(toIndentedString(uniqueId)).append("\n");
    sb.append("    roleType: ").append(toIndentedString(roleType)).append("\n");
    sb.append("    sessiontoken: ").append(toIndentedString(sessiontoken)).append("\n");
    sb.append("    tokenexpiry: ").append(toIndentedString(tokenexpiry)).append("\n");
    sb.append("    totCustomers: ").append(toIndentedString(totCustomers)).append("\n");
    sb.append("    walletBalance: ").append(toIndentedString(walletBalance)).append("\n");
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

