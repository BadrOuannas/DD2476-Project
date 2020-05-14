13
https://raw.githubusercontent.com/CoboVault/cobo-vault-cold/master/app/src/main/java/com/cobo/cold/encryption/interception/InterceptManagerGroup.java
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

package com.cobo.cold.encryption.interception;

import androidx.annotation.Nullable;
import androidx.collection.SparseArrayCompat;

public class InterceptManagerGroup {
    private static final SparseArrayCompat<InterceptManager> sMap;

    static {
        sMap = new SparseArrayCompat<>();
        register(new Secp256k1SignIntercept());
    }

    @Nullable
    public static InterceptManager get(int id) {
        return sMap.get(id);
    }

    private static void register(InterceptManager interceptManager) {
        sMap.put(interceptManager.getId(), interceptManager);
    }
}