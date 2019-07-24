package assignment.first.rezga.testingwifip2p.model;


import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Messages")
public class Message {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String peerAddress;
    public String message;

    public boolean isOwnMessage;

    public Date sendTime;


}
