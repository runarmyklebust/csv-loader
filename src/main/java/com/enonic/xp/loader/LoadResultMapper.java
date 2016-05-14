package com.enonic.xp.loader;

import com.enonic.xp.script.serializer.MapGenerator;
import com.enonic.xp.script.serializer.MapSerializable;

public class LoadResultMapper
    implements MapSerializable
{
    private long timeUsed;

    private int successful;


    public LoadResultMapper( final long timeUsed, final int successful )
    {
        this.timeUsed = timeUsed;
        this.successful = successful;
    }

    @Override
    public void serialize( final MapGenerator gen )
    {
        gen.value( "timeUsed", timeUsed );
        gen.value( "successful", successful );
        gen.end();
    }
}
