package assignment.first.rezga.testingwifip2p.model;


import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDevice(Device device);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertMessage(Message message);

    @Query("select * from devices")
    List<Device> loadAllDevices();


    @Query("select * from messages where peerAddress = :peerAddr order by sendTime desc")
    List<Message> loadMessages(String peerAddr);

    @Query("delete from messages where peerAddress = :peerAddr")
    int deleteMessages(String peerAddr);

    @Query("delete from devices where mac = :macAddr")
    int deleteDevice(String macAddr);

    @Query("delete from messages")
    int deleteAllMessages();

    @Query("delete from devices")
    int deleteAllDevices();

}
