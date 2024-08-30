package com.mblog.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import java.io.Serializable;

@Data
@Entity // This tells Hibernate to make a table out of this class
public class SiteConf implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name; // 关于我
  private String title;

  @Lob
  @Column(length = 100000)
  private String content;

  @Lob
  @Column(length = 100000)
  private String html;
}
