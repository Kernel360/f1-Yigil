package kr.co.yigil.global.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import kr.co.yigil.login.application.strategy.KakaoLoginStrategy;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = JasyptConfig.class)
@TestPropertySource(locations = "classpath:config.properties")
public class JasyptConfigTest {

    @Value("${Jasypt-Secret-Key}")
    private String key;

    private final JasyptConfig jasyptConfig = new JasyptConfig();

    @DisplayName("StringEncryptor의 빈이 잘 등록되는지")
    @Test
    void testStringEncryptorAsBean() {
        jasyptConfig.setKey(key);
        StringEncryptor encryptor = jasyptConfig.stringEncryptor();

        assertNotNull(encryptor);
        assertInstanceOf(PooledPBEStringEncryptor.class, encryptor);
        PooledPBEStringEncryptor pooledPBEStringEncryptor= (PooledPBEStringEncryptor) encryptor;
        assertNotNull(pooledPBEStringEncryptor);
    }

    @DisplayName("SimpleStringPBEConfig가 잘 구성되는지")
    @Test
    void testSimpleStringPBEConfig() {
        SimpleStringPBEConfig config = jasyptConfig.createSimpleStringPBEConfig();

        assertEquals("PBEWithMD5AndDES", config.getAlgorithm());
        assertEquals(1000, config.getKeyObtentionIterations());
        assertEquals(1, config.getPoolSize());
        assertEquals("SunJCE", config.getProviderName());
        assertEquals("base64", config.getStringOutputType());
    }
}
