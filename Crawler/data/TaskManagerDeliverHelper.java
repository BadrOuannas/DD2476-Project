50
https://raw.githubusercontent.com/iqiyi/TaskManager/master/TaskManager/src/main/java/org/qiyi/basecore/taskmanager/deliver/TaskManagerDeliverHelper.java
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
package org.qiyi.basecore.taskmanager.deliver;

import java.util.UUID;

/**
 * for some historical reason : this class is used to trace some very important logs
 * during TM running. These logs are kept in buffer , add will deliver to the cloud under
 * a certain situation.
 */
public class TaskManagerDeliverHelper {
    private static final String TAG = "TM_TaskManagerDeliverHelper";
    private static ITracker logTracker;
    public static final int DELIVER_LOG_NORMAL = 1;
    public static final int DELIVER_LOG_CRITICAL = 2;

    //2020.3.16: sTaskManagerParallel hard code true
    public static void init(ITracker tracker) {
        logTracker = tracker;
        track(UUID.randomUUID());
    }


    public static void track(Object... messages) {
        if (logTracker != null) {
            logTracker.track(messages);
        }
    }

    public static void trackCritical(Object... messages) {
        if (logTracker != null) {
            logTracker.trackCritical(messages);
        }
    }

    public static void printDump() {
        if (logTracker != null) {
            logTracker.printDump();
        }
    }

    public static void deliver(int type) {
        if (logTracker != null) {
            logTracker.deliver(type);
        }
    }

}
