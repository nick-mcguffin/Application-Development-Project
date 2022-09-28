package com.wilma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wilma.entity.dto.USER_TYPE;
import com.wilma.entity.dto.UserDTO;
import com.wilma.entity.users.Partner;
import com.wilma.entity.users.UserAccount;
import com.wilma.repository.UserAccountRepository;

@Service
public class UserRegistrationService {

    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserAccount register(UserDTO userDTO) {
        // if(userDTO.getUserType().equalsIgnoreCase(USER_TYPE.EDUCATOR.name())) return createEducatorFromUserDTO(userDTO);
        if(userDTO.getUserType().equalsIgnoreCase(USER_TYPE.PARTNER.name())) return createPartnerFromUserDTO(userDTO);
        // else if(userDTO.getUserType().equalsIgnoreCase(USER_TYPE.STUDENT.name())) return createStudentFromUserDTO(userDTO);
        else throw new UnsupportedOperationException("User type '" + userDTO.getUserType() + "' could not be identified.\nSupported types are 'educator', 'partner', and 'student'");
    }

    // private Educator createEducatorFromUserDTO(UserDTO userDTO) {
    //     return new Educator();
    // }

    public Partner createPartnerFromUserDTO(UserDTO userDTO){
        
        Partner partner = new Partner (userDTO.getUserId(),
                        userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
                        null, false, false,
                        false, false, null,  userDTO.getBusinessName(),
                        userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber(), userDTO.getAbn());
            System.err.print("A new " + userDTO.getUserType() + " has been added to the database.");
            return userRepository.save(partner);
        }
    }



    // private Student createStudentFromUserDTO(UserDTO userDTO) {
    //     
    // }

