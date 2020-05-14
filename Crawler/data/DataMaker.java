50
https://raw.githubusercontent.com/iqiyi/TaskManager/master/TaskManager/src/main/java/org/qiyi/basecore/taskmanager/struct/DataMaker.java
/*
 *
 * Copyright (C) 2020 iQIYI (www.iqiyi.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.qiyi.basecore.taskmanager.struct;

import java.util.HashMap;

public class DataMaker {

    private HashMap<String, Object> map;

    public DataMaker() {
        map = new HashMap<>();
    }

    public DataMaker put(String key, Object value) {
        map.put(key, value);
        return this;
    }

    public HashMap<String, Object> get() {
        return map;
    }
}
