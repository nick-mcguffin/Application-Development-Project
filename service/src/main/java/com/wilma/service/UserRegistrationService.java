package com.wilma.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.wilma.entity.dto.USER_TYPE;
import com.wilma.entity.dto.UserDTO;
import com.wilma.entity.users.Educator;
import com.wilma.entity.users.Partner;
import com.wilma.entity.users.Student;
import com.wilma.entity.users.UserAccount;
import com.wilma.repository.UserAccountRepository;

@Slf4j
@Service
public class UserRegistrationService {

    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserAccount register(UserDTO userDTO) {
        if(userDTO.getUserType().equalsIgnoreCase(USER_TYPE.EDUCATOR.name())) return createEducatorFromUserDTO(userDTO);
        else if(userDTO.getUserType().equalsIgnoreCase(USER_TYPE.PARTNER.name())) return createPartnerFromUserDTO(userDTO);
        else if(userDTO.getUserType().equalsIgnoreCase(USER_TYPE.STUDENT.name())) return createStudentFromUserDTO(userDTO);
        else throw new UnsupportedOperationException("User type '" + userDTO.getUserType() + "' could not be identified.\nSupported types are 'educator', 'partner', and 'student'");
    }

    private Educator createEducatorFromUserDTO(UserDTO userDTO) {
        Educator educator = new Educator(userDTO.getUserId(),
        userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
        null, false, false,
        false, false, null, null, userDTO.getDiscipline(), userDTO.getStaffId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber()); 
log.info("A new {} has been added to the database", userDTO.getUserType());
        return userRepository.save(educator);
    }

    public Partner createPartnerFromUserDTO(UserDTO userDTO){
        Partner partner = new Partner (userDTO.getUserId(),
                        userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
                        null, false, false,
                        false, false, null,  userDTO.getBusinessName(),
                        userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber(), userDTO.getAbn());
log.info("A new {} has been added to the database", userDTO.getUserType());
            return userRepository.save(partner);
        }

    private Student createStudentFromUserDTO(UserDTO userDTO) {
        Student student = new Student(userDTO.getUserId(),
        userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
        null, false, false,
        false, false, null, null, userDTO.getDiscipline(), userDTO.getStudentId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber());
log.info("A new {} has been added to the database", userDTO.getUserType());
            return userRepository.save(student);
    }
}
