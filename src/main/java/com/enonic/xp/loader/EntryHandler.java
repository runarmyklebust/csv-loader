package com.enonic.xp.loader;

import java.util.Map;

public interface EntryHandler
{
    void handle( Map<String, String> values );

    int getTotal();

}
