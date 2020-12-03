package com.example.quartz.domain.dao;

import com.example.quartz.domain.model.Settings;

import java.util.List;

public interface SettingDao {
    List<Settings> getAllSettings();

    void updateRow(Long id);
}
