package com.mblog.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@Table(name = "about")
@Entity // This tells Hibernate to make a table out of this class
public class About {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	private String content;
	private String title;
}
