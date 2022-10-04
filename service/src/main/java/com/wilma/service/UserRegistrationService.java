package com.wilma.service;

import com.wilma.entity.dto.USER_TYPE;
import com.wilma.entity.dto.UserDTO;
import com.wilma.entity.users.*;
import com.wilma.repository.ConfirmationTokenRepository;
import com.wilma.repository.RoleRepository;
import com.wilma.repository.UserAccountRepository;
import com.wilma.service.mail.MailerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Slf4j
@Service
public class UserRegistrationService {

    @Autowired
    private UserAccountRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private MailerImpl mailer;

    /**
     * Verifies a user account by the given token. If successful, the new user account will be enabled allowing the user to sign in.
     * @param confirmationToken A unique token that verifies a users ownership of the given email address
     * @return A model containing a response message and a view to display the message to the user
     */
    public ModelAndView confirmEmailVerification(String confirmationToken) {
        ModelAndView modelAndView = new ModelAndView();
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        System.out.println(confirmationToken);

        if (confirmationToken != null) {
            UserAccount user = userRepository.findByEmail(token.getUserAccount().getEmail());
            user.setEnabled(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setCredentialsNonExpired(true);
            userRepository.save(user);
            modelAndView.setViewName("account-verified");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    /**
     * Creates a new {@link Student}, {@link Partner}, or {@link Educator} based on the completed registration form
     * @param userDTO A data transfer object containing the registration form data
     * @return The resulting user account
     */
    public UserAccount register(UserDTO userDTO) {
        if (userDTO.getUserType().equalsIgnoreCase(USER_TYPE.EDUCATOR.name()))
            return createEducatorFromUserDTO(userDTO);
        else if (userDTO.getUserType().equalsIgnoreCase(USER_TYPE.PARTNER.name()))
            return createPartnerFromUserDTO(userDTO);
        else if (userDTO.getUserType().equalsIgnoreCase(USER_TYPE.STUDENT.name()))
            return createStudentFromUserDTO(userDTO);
        else
            throw new UnsupportedOperationException("User type '" + userDTO.getUserType() + "' could not be identified.\nSupported types are 'educator', 'partner', and 'student'");
    }

    /**
     * Creates a new Educator from the DTO
     * @param userDTO A data transfer object containing the registration form data
     * @return A new educator
     */
    private Educator createEducatorFromUserDTO(UserDTO userDTO) {
        UserAccount existingUser = userRepository.findByEmail(userDTO.getEmail());
        UserAccount existingUsername = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser != null) {
            System.err.println("A user with this email already exists");
            return null;
        } else if (existingUsername != null) {
            System.err.println("This username is already in use.");
            return null;
        } else {
            Educator educator = new Educator(userDTO.getUserId(),
                    userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
                    null, false, false,
                    false, false, Set.of(roleRepository.findByName("ADMIN")), null, userDTO.getDiscipline(), userDTO.getStaffId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber());
            log.info("A new {} has been added to the database", userDTO.getUserType());

            ConfirmationToken confirmationToken = new ConfirmationToken(educator);
            Thread newThread = new Thread(() -> mailer.sendConfirmationEmail(educator.getEmail(), confirmationToken));
            newThread.start();
            var result = userRepository.save(educator);
            confirmationTokenRepository.save(confirmationToken);
            return result;
        }
    }

    /**
     * Creates a new Partner from the DTO
     * @param userDTO A data transfer object containing the registration form data
     * @return a new partner
     */
    public Partner createPartnerFromUserDTO(UserDTO userDTO) {
        UserAccount existingUser = userRepository.findByEmail(userDTO.getEmail());
        UserAccount existingUsername = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser != null) {
            System.err.println("A user with this email already exists");
            return null;
        } else if (existingUsername != null) {
            System.err.println("This username is already in use.");
            return null;
        } else {
            Partner partner = new Partner(userDTO.getUserId(),
                    userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
                    null, false, false,
                    false, false, Set.of(roleRepository.findByName("PARTNER")), userDTO.getBusinessName(),
                    userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber(), userDTO.getAbn());
            log.info("A new {} has been added to the database", userDTO.getUserType());

            ConfirmationToken confirmationToken = new ConfirmationToken(partner);
            Thread newThread = new Thread(() -> mailer.sendConfirmationEmail(partner.getEmail(), confirmationToken));
            newThread.start();
            var result = userRepository.save(partner);
            confirmationTokenRepository.save(confirmationToken);
            return result;
        }
    }

    /**
     * Creates a new Student from the DTO
     * @param userDTO A data transfer object containing the registration form data
     * @return a new student
     */
    private Student createStudentFromUserDTO(UserDTO userDTO) {
        UserAccount existingUser = userRepository.findByEmail(userDTO.getEmail());
        UserAccount existingUsername = userRepository.findByUsername(userDTO.getUsername());
        if (existingUser != null) {
            System.err.println("A user with this email already exists");
            return null;
        } else if (existingUsername != null) {
            System.err.println("This username is already in use.");
            return null;
        } else {
            Student student = new Student(userDTO.getUserId(),
                    userDTO.getUsername(), passwordEncoder.encode(userDTO.getPassword()), userDTO.getEmail(),
                    null, false, false,
                    false, false, Set.of(roleRepository.findByName("STUDENT")), null, userDTO.getDiscipline(), userDTO.getStudentId(), userDTO.getFirstName(), userDTO.getLastName(), userDTO.getContactNumber());
            log.info("A new {} has been added to the database", userDTO.getUserType());

            ConfirmationToken confirmationToken = new ConfirmationToken(student);
            Thread newThread = new Thread(() -> mailer.sendConfirmationEmail(student.getEmail(), confirmationToken));
            newThread.start();
            var result = userRepository.save(student);
            confirmationTokenRepository.save(confirmationToken);
            return result;
        }
    }
}
