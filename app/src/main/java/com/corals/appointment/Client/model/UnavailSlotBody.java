
package com.corals.appointment.Client.model;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * UnavailSlotBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-01T09:27:04.227Z")
public class UnavailSlotBody {
    @SerializedName("slot_no")
    private String slotNo = null;

    @SerializedName("start_time")
    private String startTime = null;

    @SerializedName("end_time")
    private String endTime = null;

    public UnavailSlotBody slotNo(String slotNo) {
        this.slotNo = slotNo;
        return this;
    }

    /**
     * Get slotNo
     * @return slotNo
     **/
    @Schema(description = "")
    public String getSlotNo() {
        return slotNo;
    }

    public void setSlotNo(String slotNo) {
        this.slotNo = slotNo;
    }

    public UnavailSlotBody startTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get startTime
     * @return startTime
     **/
    @Schema(description = "")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public UnavailSlotBody endTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get endTime
     * @return endTime
     **/
    @Schema(description = "")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UnavailSlotBody unavailSlotBody = (UnavailSlotBody) o;
        return Objects.equals(this.slotNo, unavailSlotBody.slotNo) &&
                Objects.equals(this.startTime, unavailSlotBody.startTime) &&
                Objects.equals(this.endTime, unavailSlotBody.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(slotNo, startTime, endTime);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class UnavailSlotBody {\n");

        sb.append("    slotNo: ").append(toIndentedString(slotNo)).append("\n");
        sb.append("    startTime: ").append(toIndentedString(startTime)).append("\n");
        sb.append("    endTime: ").append(toIndentedString(endTime)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }

}

