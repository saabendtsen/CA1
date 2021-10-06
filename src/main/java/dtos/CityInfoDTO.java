package dtos;

import entities.Address;
import entities.CityInfo;
import entities.Person;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class CityInfoDTO {
    private Long id;
    private String zipcode;
    private String city;

    public CityInfoDTO(Long id, String zipcode, String city) {
        this.id = id;
        this.zipcode = zipcode;
        this.city = city;
    }

    public CityInfoDTO(CityInfo c) {
        this.id = c.getId();
        this.zipcode = c.getZipcode();
        this.city = c.getCity();
    }

    public static List<CityInfoDTO> getDtos(List<CityInfo> c){
        List<CityInfoDTO> cdtos = new ArrayList();
        c.forEach(rm -> cdtos.add(new CityInfoDTO(rm)));
        return cdtos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}

