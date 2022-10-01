package com.wilma.entity.users;

import com.wilma.entity.docs.UserDocument;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
public class Student extends UserAccount {

    @Column(name = "discipline")
    private String discipline;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "expected_graduation_date")
    private Date expectedGraduationDate;

    @Column(name = "student_id")
    private String studentId;

    @Column(name = "profile_image_id")
    private Integer profileImageId;

    public Student(String username, String discipline) {
        super(username);
        this.discipline = discipline;
    }

    public Student(Integer userId, String username, String password, String email, String bio, boolean credentialsNonExpired, boolean accountNonLocked, boolean accountNonExpired, boolean enabled, Set<Role> roles, Collection<UserDocument> userDocuments, String discipline, String studentId, Date expectedGraduationDate) {
        super(userId, username, password, email, bio, credentialsNonExpired, accountNonLocked, accountNonExpired, enabled, roles, userDocuments);
        this.discipline = discipline;
        this.studentId = studentId;
        this.expectedGraduationDate = expectedGraduationDate;
    }

}