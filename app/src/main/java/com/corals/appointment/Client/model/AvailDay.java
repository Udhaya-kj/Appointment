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
 * AvailDay
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-06-20T07:17:11.603Z")
public class AvailDay {
  @SerializedName("day")
  private String day = null;

  @SerializedName("timing")
  private List<TimeData> timing = null;

  public AvailDay day(String day) {
    this.day = day;
    return this;
  }

   /**
   * Get day
   * @return day
  **/
   @Schema(description = "")
  public String getDay() {
    return day;
  }

  public void setDay(String day) {
    this.day = day;
  }

  public AvailDay timing(List<TimeData> timing) {
    this.timing = timing;
    return this;
  }

  public AvailDay addTimingItem(TimeData timingItem) {
    if (this.timing == null) {
      this.timing = new ArrayList<TimeData>();
    }
    this.timing.add(timingItem);
    return this;
  }

   /**
   * Get timing
   * @return timing
  **/
   @Schema(description = "")
  public List<TimeData> getTiming() {
    return timing;
  }

  public void setTiming(List<TimeData> timing) {
    this.timing = timing;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    AvailDay availDay = (AvailDay) o;
    return Objects.equals(this.day, availDay.day) &&
        Objects.equals(this.timing, availDay.timing);
  }

  @Override
  public int hashCode() {
    return Objects.hash(day, timing);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class AvailDay {\n");
    
    sb.append("    day: ").append(toIndentedString(day)).append("\n");
    sb.append("    timing: ").append(toIndentedString(timing)).append("\n");
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

