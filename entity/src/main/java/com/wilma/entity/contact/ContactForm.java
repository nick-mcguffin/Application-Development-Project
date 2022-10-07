package com.wilma.entity.contact;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ContactForm {
    
    @Id
    private Integer id;
    private String name;
    private String email;
    private String details;
}
