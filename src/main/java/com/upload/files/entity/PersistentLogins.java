package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Getter @Setter
@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id
    private String series;
    private String username;
    private String token;
    private Date lastUsed;

}
