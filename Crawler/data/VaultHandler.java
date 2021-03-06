7
https://raw.githubusercontent.com/sadv1r/ansible-vault-editor-idea-plugin/master/src/main/java/ru/sadv1r/ansible/vault/VaultHandler.java
package ru.sadv1r.ansible.vault;

import org.jetbrains.annotations.NotNull;
import ru.sadv1r.ansible.vault.crypto.CypherFactory;
import ru.sadv1r.ansible.vault.crypto.Util;
import ru.sadv1r.ansible.vault.crypto.VaultInfo;
import ru.sadv1r.ansible.vault.crypto.decoders.Cypher;
import ru.sadv1r.ansible.vault.crypto.decoders.impl.CypherAES256;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class VaultHandler {

    private static final String DEFAULT_CYPHER = CypherAES256.CYPHER_ID;
    private static final String LINE_BREAK = "\n";

    public static byte[] encrypt(byte[] cleartext, String password) throws IOException {
        return encrypt(cleartext, password, DEFAULT_CYPHER);
    }

    public static byte[] encrypt(byte[] cleartext, String password, String cypher) throws IOException {
        @NotNull Optional<Cypher> cypherInstance = CypherFactory.getCypher(cypher);
        if (!cypherInstance.isPresent()) {
            throw new IOException("Unsupported vault cypher");
        }

        byte[] vaultData = cypherInstance.get().encrypt(cleartext, password);
        String vaultDataString = new String(vaultData);
        String vaultPackage = cypherInstance.get().infoLine() + "\n" + vaultDataString;
        return vaultPackage.getBytes();
    }

    public static byte[] decrypt(String encrypted, String password) throws IOException {
        final int firstLineBreakIndex = encrypted.indexOf(LINE_BREAK);

        final String infoLinePart = encrypted.substring(0, firstLineBreakIndex);
        final VaultInfo vaultInfo = new VaultInfo(infoLinePart);

        final String vaultDataPart = encrypted.substring(firstLineBreakIndex + 1);
        final byte[] encryptedData = getVaultData(vaultDataPart);

        return vaultInfo.getCypher().decrypt(encryptedData, password);
    }

    private static byte[] getVaultData(String vaultData) {
        final String rawData = removeLineBreaks(vaultData);
        return Util.unhex(rawData);
    }

    @NotNull
    private static String removeLineBreaks(final String string) {
        final String[] lines = string.split(LINE_BREAK);
        return String.join("", Arrays.asList(lines));
    }

}
