package com.ligerlearn.example.controller.dtos;

import com.ligerlearn.example.model.xml.Address;
import lombok.Builder;
import lombok.Data;

/**
 * Data transfer object for a Person entity.
 */
@Data @Builder
public class PersonDTO {
    private Long id;
    private String firstName;
    private String surname;
    private Address address;
}
