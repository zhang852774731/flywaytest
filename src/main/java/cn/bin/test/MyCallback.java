package cn.bin.test;

import org.flywaydb.core.api.callback.Callback;
import org.flywaydb.core.api.callback.Context;
import org.flywaydb.core.api.callback.Event;

/**
 * Created by zhangbin on 2018/7/24.
 */
public class MyCallback implements Callback{
    public boolean supports(Event event, Context context) {
        return true;
    }

    public boolean canHandleInTransaction(Event event, Context context) {
        return true;
    }

    public void handle(Event event, Context context) {
        System.out.println(event.getId());
    }
}
