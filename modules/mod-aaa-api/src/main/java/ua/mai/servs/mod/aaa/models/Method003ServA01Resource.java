package ua.mai.servs.mod.aaa.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Method003ServA01Resource {
    private String name;
    private String state;
    private String desc;
//    private List<RelatedPartyResource> relatedParty;
//    private List<ServiceCharacteristicResource> serviceCharacteristic;
//    private ServiceSpecificationResource serviceSpecification;
}
