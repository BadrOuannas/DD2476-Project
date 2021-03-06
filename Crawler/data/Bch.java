13
https://raw.githubusercontent.com/CoboVault/cobo-vault-cold/master/coinlib/src/main/java/com/cobo/coinlib/coins/BCH/Bch.java
/*
 * Copyright (c) 2020 Cobo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * in the file COPYING.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cobo.coinlib.coins.BCH;

import com.cobo.coinlib.coins.AbsDeriver;
import com.cobo.coinlib.coins.BTC.Btc;
import com.cobo.coinlib.exception.InvalidTransactionException;
import com.cobo.coinlib.interfaces.Coin;

import org.bitcoinj.core.LegacyAddress;
import org.bitcoinj.crypto.DeterministicKey;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Bch extends Btc {
    public Bch(Coin impl) {
        super(impl);
    }

    @Override
    public String coinCode() {
        return "BCH";
    }

    public static class Tx extends Btc.Tx {
        public Tx(JSONObject metaData, String coinCode)
                throws JSONException, InvalidTransactionException {
            super(metaData, coinCode);
        }

        @Override
        protected void parseInput() throws JSONException, InvalidTransactionException {
            JSONArray inputs = metaData.getJSONArray("inputs");
            StringBuilder paths = new StringBuilder();
            for (int i = 0; i < inputs.length(); i++) {
                JSONObject input = inputs.getJSONObject(i);
                String path = input.getString("ownerKeyPath");
                checkHdPath(path, false);
                paths.append(path).append(SEPARATOR);
                int index = input.optInt("index");
                if (index == 0) {
                    input.put("index", 0);
                }
                inputAmount += input.getLong("value");
            }
            hdPath = paths.deleteCharAt(paths.length() - 1).toString();
        }
    }

    public static class Deriver extends AbsDeriver {

        @Override
        public String derive(String xPubKey, int changeIndex, int addrIndex) {
            DeterministicKey address = getAddrDeterministicKey(xPubKey, changeIndex, addrIndex);
            LegacyAddress addr = LegacyAddress.fromPubKeyHash(MAINNET, address.getPubKeyHash());
            return addr.toBase58();
        }

        @Override
        public String derive(String xPubKey) {
            return LegacyAddress.fromPubKeyHash(MAINNET,
                    getDeterministicKey(xPubKey).getPubKeyHash()).toBase58();
        }
    }
}
