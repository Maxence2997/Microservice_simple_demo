package idv.laborLab.sharedLibrary.miscellaneous.objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String country;
    private String zip;
    private String zipExt;
}
