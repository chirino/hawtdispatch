/**
 * Copyright (C) 2012 FuseSource, Inc.
 * http://fusesource.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.fusesource.hawtdispatch.internal.util;

import java.util.concurrent.CountDownLatch;

import org.fusesource.hawtdispatch.DispatchQueue;

/**
 * 
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public class QueueSupport {

    static public void dispatchApply(DispatchQueue queue, int itterations, final Runnable runnable) throws InterruptedException {
        final CountDownLatch done = new CountDownLatch(itterations);
        Runnable wrapper = new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } finally {
                    done.countDown();
                }
            }
        };
        for( int i=0; i < itterations; i++ ) { 
            queue.execute(wrapper);
        }
        done.await();
    }
    
}
