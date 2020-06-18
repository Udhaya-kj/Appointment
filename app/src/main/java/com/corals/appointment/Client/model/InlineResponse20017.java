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
 * InlineResponse20017
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-13T07:13:20.960Z")
public class InlineResponse20017 {
  @SerializedName("notify_status")
  private List<InlineResponse20017NotifyStatus> notifyStatus = null;

  public InlineResponse20017 notifyStatus(List<InlineResponse20017NotifyStatus> notifyStatus) {
    this.notifyStatus = notifyStatus;
    return this;
  }

  public InlineResponse20017 addNotifyStatusItem(InlineResponse20017NotifyStatus notifyStatusItem) {
    if (this.notifyStatus == null) {
      this.notifyStatus = new ArrayList<InlineResponse20017NotifyStatus>();
    }
    this.notifyStatus.add(notifyStatusItem);
    return this;
  }

   /**
   * Get notifyStatus
   * @return notifyStatus
  **/
  @Schema(description = "")
  public List<InlineResponse20017NotifyStatus> getNotifyStatus() {
    return notifyStatus;
  }

  public void setNotifyStatus(List<InlineResponse20017NotifyStatus> notifyStatus) {
    this.notifyStatus = notifyStatus;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    InlineResponse20017 inlineResponse20017 = (InlineResponse20017) o;
    return Objects.equals(this.notifyStatus, inlineResponse20017.notifyStatus);
  }

  @Override
  public int hashCode() {
    return Objects.hash(notifyStatus);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class InlineResponse20017 {\n");
    
    sb.append("    notifyStatus: ").append(toIndentedString(notifyStatus)).append("\n");
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
