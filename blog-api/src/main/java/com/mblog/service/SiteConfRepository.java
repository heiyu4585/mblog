package com.mblog.service;

import com.mblog.model.SiteConf;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteConfRepository extends JpaRepository<SiteConf, Integer> {

  SiteConf findByName(String name);
}
