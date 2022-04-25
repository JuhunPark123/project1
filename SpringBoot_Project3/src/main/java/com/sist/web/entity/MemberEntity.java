package com.sist.web.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "member")
@Setter
@Getter
public class MemberEntity {
	@Id
	private String id;
	private String password, username, tel;
}
