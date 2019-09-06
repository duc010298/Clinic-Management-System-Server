package com.github.duc010298.cms.repository;

import com.github.duc010298.cms.entity.AppUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUserEntity, Integer> {
    AppUserEntity findByUserName(String userName);
}