7
https://raw.githubusercontent.com/sadv1r/ansible-vault-editor-idea-plugin/master/src/main/java/ru/sadv1r/ansible/vault/crypto/decoders/impl/CypherAES.java
package ru.sadv1r.ansible.vault.crypto.decoders.impl;

import ru.sadv1r.ansible.vault.crypto.VaultInfo;
import ru.sadv1r.ansible.vault.crypto.decoders.Cypher;

import java.io.IOException;

public class CypherAES implements Cypher {

    public static final String CYPHER_ID = "AES";

    public byte[] decrypt(byte[] data, String password) throws IOException {
        throw new IOException(CYPHER_ID + " is not implemented.");
    }

    public byte[] encrypt(byte[] data, String password) throws IOException {
        throw new IOException(CYPHER_ID + " is not implemented.");
    }

    public String infoLine() {
        return VaultInfo.vaultInfoForCypher(CYPHER_ID);
    }

}
