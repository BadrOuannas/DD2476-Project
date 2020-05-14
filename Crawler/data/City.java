14
https://raw.githubusercontent.com/fawad1997/SpringWebAPI/master/src/main/java/com/restfulspring/apiexample/entity/City.java
package com.restfulspring.apiexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cityId")
    private int cityId;
    private String cityName;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "countryId")
//    private Country country;
}
