package com.wilma.entity.docs;

import com.wilma.entity.users.UserAccount;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "documents")
public class Document implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "upload_date")
    private Date uploadDate;

    @Column(name = "filename")
    private String filename;

    @Column(name = "file_path", length = 1024)
    private String filepath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserAccount userAccount;

}