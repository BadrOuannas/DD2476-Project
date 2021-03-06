7
https://raw.githubusercontent.com/sadv1r/ansible-vault-editor-idea-plugin/master/src/test/java/ru/sadv1r/ansible/vault/VaultHandlerTest.java
package ru.sadv1r.ansible.vault;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("ConstantConditions")
public class VaultHandlerTest {

    private static final String TEST_PASSWORD = "password";
    private static final String TEST_WRONG_PASSWORD = "wrong_password";
    private static final String DECODED_VAULT = "userLogin: admin\n" +
            "userPass: pass123\n" +
            "\n" +
            "db:\n" +
            "    pass: qwerty";

    @Rule
    public final ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testStreamValidVault() throws IOException {
        InputStream encodedStream = getClass().getClassLoader().getResourceAsStream("test-vault.yml");
        String encryptedValue = IOUtils.toString(encodedStream, StandardCharsets.UTF_8);
        byte[] decrypt = VaultHandler.decrypt(encryptedValue, TEST_PASSWORD);
        assertEquals(DECODED_VAULT, new String(decrypt));
    }

    @Test
    public void testStreamInvalidVault() throws IOException {
        exceptionRule.expect(IOException.class);
        exceptionRule.expectMessage("HMAC Digest doesn't match - possibly it's the wrong password.");
        InputStream encodedStream = getClass().getClassLoader().getResourceAsStream("test-vault.yml");
        String encryptedValue = IOUtils.toString(encodedStream, StandardCharsets.UTF_8);
        VaultHandler.decrypt(encryptedValue, TEST_WRONG_PASSWORD);
    }

}
