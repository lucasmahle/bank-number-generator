package entity;

import application.Util;

public class History {
    public long id;
    public int number;
    public int type;
    public String getFormatted() {
    	return Util.formatQueueNumber(type, number);
    }
}
