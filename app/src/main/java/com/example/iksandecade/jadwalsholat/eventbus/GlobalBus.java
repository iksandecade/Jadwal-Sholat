package com.example.iksandecade.jadwalsholat.eventbus;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by iksandecade on 24/02/17.
 */

public class GlobalBus {

    private static EventBus eventBus;

    public static EventBus getEventBus() {
        if (eventBus == null)
            eventBus = EventBus.getDefault();

        return eventBus;
    }
}
