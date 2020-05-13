7
https://raw.githubusercontent.com/chenjk-520/keventbus/master/keventbus/src/main/java/com/util/keventbus/util/ErrorDialogConfig.java
/*
 * Copyright (C) 2012-2016 Markus Junginger, greenrobot (http://greenrobot.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.util.keventbus.util;

import android.content.res.Resources;
import android.util.Log;

import com.util.keventbus.KeventBus;

public class ErrorDialogConfig {
    final Resources resources;
    final int defaultTitleId;
    final int defaultErrorMsgId;
    final ExceptionToResourceMapping mapping;

    KeventBus eventBus;
    boolean logExceptions = true;
    String tagForLoggingExceptions;
    int defaultDialogIconId;
    Class<?> defaultEventTypeOnDialogClosed;

    public ErrorDialogConfig(Resources resources, int defaultTitleId, int defaultMsgId) {
        this.resources = resources;
        this.defaultTitleId = defaultTitleId;
        this.defaultErrorMsgId = defaultMsgId;
        mapping = new ExceptionToResourceMapping();
    }

    public ErrorDialogConfig addMapping(Class<? extends Throwable> clazz, int msgId) {
        mapping.addMapping(clazz, msgId);
        return this;
    }

    public int getMessageIdForThrowable(final Throwable throwable) {
        Integer resId = mapping.mapThrowable(throwable);
        if (resId != null) {
            return resId;
        } else {
            Log.d(KeventBus.TAG, "No specific message ressource ID found for " + throwable);
            return defaultErrorMsgId;
        }
    }

    public void setDefaultDialogIconId(int defaultDialogIconId) {
        this.defaultDialogIconId = defaultDialogIconId;
    }

    public void setDefaultEventTypeOnDialogClosed(Class<?> defaultEventTypeOnDialogClosed) {
        this.defaultEventTypeOnDialogClosed = defaultEventTypeOnDialogClosed;
    }

    public void disableExceptionLogging() {
        logExceptions = false;
    }

    public void setTagForLoggingExceptions(String tagForLoggingExceptions) {
        this.tagForLoggingExceptions = tagForLoggingExceptions;
    }

    public void setEventBus(KeventBus eventBus) {
        this.eventBus = eventBus;
    }

    /** eventBus!=null ? eventBus: KeventBus.getDefault() */
    KeventBus getEventBus() {
        return eventBus!=null ? eventBus: KeventBus.getDefault();
    }
}