package com.ktk.domain.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long userId;
	
	@Column(nullable = false)
    private String name;
	
	@Column(nullable = false)
    private String password;
	
	@Column(nullable = false)
    private String email;
	
    private Timestamp created;
    private boolean enabled;
}
