2
https://raw.githubusercontent.com/oliverselinger/failsafe-executor/master/src/main/java/os/failsafe/executor/Tasks.java
/*******************************************************************************
 * MIT License
 *
 * Copyright (c) 2020 Oliver Selinger
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
package os.failsafe.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tasks {

    public static Task of(String name, Consumer<String> task) {
        return new Task() {

            private final List<ExecutionEndedListener> listeners = new ArrayList<>();

            @Override
            public String getName() {
                return name;
            }

            @Override
            public void execute(String parameter) {
                task.accept(parameter);
            }

            @Override
            public Instance instance(String parameter) {
                return new Instance(name, parameter);
            }

            @Override
            public void subscribe(ExecutionEndedListener listener) {
                listeners.add(listener);
            }

            @Override
            public void unsubscribe(ExecutionEndedListener listener) {
                listeners.remove(listener);
            }

            @Override
            public void notifyListeners(String taskId) {
                listeners.forEach(listener -> listener.executed(name, taskId));
            }
        };
    }
}
