13
https://raw.githubusercontent.com/wix-incubator/rn-contact-tracing/master/lib/android/src/main/java/com/wix/specialble/db/ScanDao.java
package com.wix.specialble.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.wix.specialble.bt.Scan;

import java.util.List;


@Dao
public interface ScanDao {

    @Query("SELECT * FROM scans")
    List<Scan> getAllBLEScans();

    @Query("SELECT * FROM scans WHERE publicKey = :publicKey ORDER BY timestamp desc")
    List<Scan> getScansByKey(String publicKey);

    @Insert
    void insertAll(Scan... scans);

    @Update
    void update(Scan scan);

    @Insert
    void insert(Scan scan);

    @Delete
    void delete(Scan scan);

    @Query("DELETE FROM scans")
    public void clearAll();
}
