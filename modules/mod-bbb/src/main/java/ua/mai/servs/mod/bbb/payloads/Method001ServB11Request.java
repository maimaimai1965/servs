package ua.mai.servs.mod.bbb.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Method001ServB11Request {
    @NotNull
    private String state;
//    @Valid
//    @NotNull
//    @Size(min = 1)
//    private List<RelatedPartyResource> relatedParty;
//    @Valid
//    @NotNull
//    @Size(min = 1)
//    private List<ServiceCharacteristicResource> serviceCharacteristic;
//    @Valid
//    @NotNull
//    private ServiceSpecificationResource serviceSpecification;
}
