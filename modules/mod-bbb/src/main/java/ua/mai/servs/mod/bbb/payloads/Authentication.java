package ua.mai.servs.mod.bbb.payloads;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Authentication {
    @SerializedName(value = "access_token", alternate = {"accessToken"})
    private String accessToken;
    @SerializedName(value = "token_type", alternate = {"tokenType"})
    private String tokenType;
    @SerializedName(value = "expires_in", alternate = {"expiresIn"})
    private Long expiresIn;
    private String scope;
    private String tenant;
    private String jti;
}
