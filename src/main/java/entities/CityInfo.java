package entities;

import dtos.CityInfoDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQuery(name = "CityInfo.deleteAllRows", query = "DELETE from CityInfo")
public class CityInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 4)
    private String zipcode;
    @Column(length=35)
    private String city;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cityInfo")
    private List<Address> addresslist;

    public CityInfo() {
    }

    public CityInfo(CityInfoDTO cityInfoDTO) {
        this.zipcode = cityInfoDTO.getZipcode();
        this.city = cityInfoDTO.getCity();
    }

    public CityInfo(String zipcode, String city) {
        this.zipcode = zipcode;
        this.city = city;
        this.addresslist = new ArrayList<>();
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

    public List<Address> getAddress() {
        return addresslist;
    }

    public void addAddress(Address address) {
        this.addresslist.add(address);
        if(address != null){
            address.setCityInfo(this);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}