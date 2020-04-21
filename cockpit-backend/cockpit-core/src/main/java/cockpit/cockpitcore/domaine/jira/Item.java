
package cockpit.cockpitcore.domaine.jira;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "field",
    "fieldtype",
    "fieldId",
    "from",
    "fromString",
    "to",
    "toString"
})
public class Item {

    @JsonProperty("field")
    private String field;
    @JsonProperty("fieldtype")
    private String fieldtype;
    @JsonProperty("fieldId")
    private String fieldId;
    @JsonProperty("from")
    private Object from;
    @JsonProperty("fromString")
    private Object fromString;
    @JsonProperty("to")
    private String to;
    @JsonProperty("toString")
    private String toString;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("field")
    public String getField() {
        return field;
    }

    @JsonProperty("field")
    public void setField(String field) {
        this.field = field;
    }

    @JsonProperty("fieldtype")
    public String getFieldtype() {
        return fieldtype;
    }

    @JsonProperty("fieldtype")
    public void setFieldtype(String fieldtype) {
        this.fieldtype = fieldtype;
    }

    @JsonProperty("fieldId")
    public String getFieldId() {
        return fieldId;
    }

    @JsonProperty("fieldId")
    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }

    @JsonProperty("from")
    public Object getFrom() {
        return from;
    }

    @JsonProperty("from")
    public void setFrom(Object from) {
        this.from = from;
    }

    @JsonProperty("fromString")
    public Object getFromString() {
        return fromString;
    }

    @JsonProperty("fromString")
    public void setFromString(Object fromString) {
        this.fromString = fromString;
    }

    @JsonProperty("to")
    public String getTo() {
        return to;
    }

    @JsonProperty("to")
    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty("toString")
    public String getToString() {
        return toString;
    }

    @JsonProperty("toString")
    public void setToString(String toString) {
        this.toString = toString;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

