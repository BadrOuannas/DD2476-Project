50
https://raw.githubusercontent.com/iqiyi/TaskManager/master/app/src/main/java/com/qiyi/tm/demo/test/DependantTest.java
package com.qiyi.tm.demo.test;

import com.qiyi.tm.demo.R;

import org.qiyi.basecore.taskmanager.Task;
import org.qiyi.basecore.taskmanager.other.TMLog;

public class DependantTest extends Test {
    @Override
    public void doTest() {
//        taskiii();
//        taskDDD();
        orDependant();

    }

    protected void assetThread(boolean ui) {
        // do nothing
    }

    /**
     * test task depend on wrong id : to tasks are of same id
     */
    private void taskiii() {
        Task task1 = getTask("task1", R.id.task_1, 100)
                .setCallBackOnFinished(new Task.TaskResultCallback() {
                    @Override
                    public void onResultCallback(Task task, Object var) {
                        TMLog.d(TAG, "XXX");
                    }
                });
        task1.postAsyncDelay(2010);


        Task task2 = getTask("task2", R.id.task_1, 200);
        task2.postUIDelay(2000);

        Task task3 = getTask("task3");
        task3.dependOn(task1.getTaskId(), task2.getTaskId());
        task3.executeSyncUI();
    }

    /**
     * direct task dependant
     */
    private void taskDDD() {
        Task task1 = getTask("task1");
        task1.postAsyncDelay(2010);
        Task task2 = getTask("task2");
        task2.postUIDelay(2000);
        Task task3 = getTask("task3");
        task3.dependOn(task1, task2);
        task3.executeSyncUI();
    }


    private void orDependant() {
        Task task1 = getTask("task11");
        task1.postAsyncDelay(2010);
        getTask("task22").delayAfter(200, task1);
        new Task("ordepend") {
            @Override
            public void doTask() {
                // your task
            }
        }.dependOn(R.id.task_1, R.id.task_2) // not running
                .orDependOn(R.id.task_3, R.id.task_4) // not running
                .orDelay(2000) // run after 2000ms
                .post();
    }
}
