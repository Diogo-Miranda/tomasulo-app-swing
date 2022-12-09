package com.puc.tomasuloapp.factory;

import org.yaml.snakeyaml.Yaml;

import java.util.Map;

public class ConfigFactory {
    public Map<String, String> getConfigs() {
        var yaml = new Yaml();
        var inputStream = getClass()
                .getClassLoader()
                .getResourceAsStream("values.yaml");

        return yaml.load(inputStream);
    }

    public Integer getNumberRegisters() {
        return 10;
    }

}
