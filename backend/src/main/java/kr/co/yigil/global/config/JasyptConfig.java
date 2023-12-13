package kr.co.yigil.global.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class JasyptConfig {

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        encryptor.setConfig(createSimpleStringPBEConfig());
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        encryptor.setConfig(config);
        return encryptor;
    }

    private SimpleStringPBEConfig createSimpleStringPBEConfig() {
        String key = "yigil_jasypt_key";
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        setEncryptionConfigDetails(config, key);
        return config;
    }

    private void setEncryptionConfigDetails(final SimpleStringPBEConfig config, final String key) {
        config.setPassword(key);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations(1000);
        config.setPoolSize(1);
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
    }
}
