package com.cts.skilltrkr.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="profile")
public class ProfileEntity {

    @Id
    private String associateId;

    @Column(name = "name")
    private String name;

    @Column(name = "emailId")
    private String emailId;

    @Column(name = "contactNo")
    private String contactNo;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProfileSkillEntity> skills;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "updatedDate")
    private Date updatedDate;
    
}