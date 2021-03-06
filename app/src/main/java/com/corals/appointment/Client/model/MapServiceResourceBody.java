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

import java.io.Serializable;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * MapServiceResourceBody
 */
@javax.annotation.Generated(value = "io.swagger.codegen.languages.JavaClientCodegen", date = "2020-07-11T05:19:50.492Z")
public class MapServiceResourceBody implements Serializable {
    @SerializedName("manageable_load")
    private String manageableLoad = null;
    @SerializedName("ser_id")
    private String serId = null;
    @SerializedName("res_id")
    private String resId = null;
    @SerializedName("delete")
    private Boolean delete = null;
    public MapServiceResourceBody manageableLoad(String manageableLoad) {
        this.manageableLoad = manageableLoad;
        return this;
    }
    /**
     * Get manageableLoad
     * @return manageableLoad
     **/
    @Schema(description = "")
    public String getManageableLoad() {
        return manageableLoad;
    }
    public void setManageableLoad(String manageableLoad) {
        this.manageableLoad = manageableLoad;
    }
    public MapServiceResourceBody serId(String serId) {
        this.serId = serId;
        return this;
    }
    /**
     * Get serId
     * @return serId
     **/
    @Schema(description = "")
    public String getSerId() {
        return serId;
    }
    public void setSerId(String serId) {
        this.serId = serId;
    }
    public MapServiceResourceBody resId(String resId) {
        this.resId = resId;
        return this;
    }
    /**
     * Get resId
     * @return resId
     **/
    @Schema(description = "")
    public String getResId() {
        return resId;
    }
    public void setResId(String resId) {
        this.resId = resId;
    }
    public MapServiceResourceBody delete(Boolean delete) {
        this.delete = delete;
        return this;
    }
    /**
     * Get delete
     * @return delete
     **/
    @Schema(description = "")
    public Boolean isDelete() {
        return delete;
    }
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MapServiceResourceBody mapServiceResourceBody = (MapServiceResourceBody) o;
        return Objects.equals(this.manageableLoad, mapServiceResourceBody.manageableLoad) &&
                Objects.equals(this.serId, mapServiceResourceBody.serId) &&
                Objects.equals(this.resId, mapServiceResourceBody.resId) &&
                Objects.equals(this.delete, mapServiceResourceBody.delete);
    }
    @Override
    public int hashCode() {
        return Objects.hash(manageableLoad, serId, resId, delete);
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class MapServiceResourceBody {\n");
        sb.append("    manageableLoad: ").append(toIndentedString(manageableLoad)).append("\n");
        sb.append("    serId: ").append(toIndentedString(serId)).append("\n");
        sb.append("    resId: ").append(toIndentedString(resId)).append("\n");
        sb.append("    delete: ").append(toIndentedString(delete)).append("\n");
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