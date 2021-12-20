package ua.mai.servs.exceptions;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResourceException {
    @Builder.Default
    private String error = "error.rbt.invalid.request";
    @SerializedName("error_description")
    private String errorDescription;
}
