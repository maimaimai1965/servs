package ua.mai.servs.mod.aaa.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Method003ServA01Request {
    @NotNull
    private String name;
    @NotNull
    private String state;
    private String service;
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
