package dtos;

import entities.Address;
import entities.Person;

import java.util.ArrayList;
import java.util.List;

public class AddressDTO {

    private Long id;
    private String street;
    private String additionalInfo;
<<<<<<< HEAD
=======
    private CityInfoDTO cityInfoDTO;
>>>>>>> main


    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.additionalInfo = address.getAdditionalInfo();
<<<<<<< HEAD
=======
        this.cityInfoDTO = new CityInfoDTO(address.getCityInfo());
>>>>>>> main
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

<<<<<<< HEAD
=======
    public CityInfoDTO getCityInfoDTO() {
        return cityInfoDTO;
    }

    public void setCityInfoDTO(CityInfoDTO cityInfoDTO) {
        this.cityInfoDTO = cityInfoDTO;
    }
>>>>>>> main
}
