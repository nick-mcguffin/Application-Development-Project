package com.wilma.entity.forum.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.UUID;

/**
 * Remote client represents a mobile app, external web UI, or any other external service interacting with the platform via the REST API
 */
@Getter
@Setter
@Entity
@Table(name = "remote_client")
@NoArgsConstructor
public class RemoteClient {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "api_key")
    private final String apiKey = String.valueOf(UUID.randomUUID()).toUpperCase();

    public RemoteClient(String name) {
        this.name = name;
    }
}