23
https://raw.githubusercontent.com/mrchengwenlong/NettyIM/master/im_lib/src/main/java/com/takiku/im_lib/internal/Internal.java
package com.takiku.im_lib.internal;

import com.takiku.im_lib.client.IMClient;
import com.takiku.im_lib.entity.base.Address;
import com.takiku.im_lib.internal.connection.ConnectionPool;
import com.takiku.im_lib.internal.connection.RealConnection;
import com.takiku.im_lib.internal.connection.StreamAllocation;

public abstract class Internal {

  public static void initializeInstanceForTests() {
    // Needed in tests to ensure that the instance is actually pointing to something.
    new IMClient();
  }
  public static Internal instance;

  public abstract RealConnection get(ConnectionPool pool, Address address,
                                     StreamAllocation streamAllocation);

  public abstract void deduplicate(
          ConnectionPool pool);

  public abstract void put(ConnectionPool pool, RealConnection connection);




}
