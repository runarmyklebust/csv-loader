package com.enonic.xp.loader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Map;

import com.google.common.io.ByteSource;

public class CSVDataLoader
    extends AbstractDataLoader
{
    @Override
    public void load( final GeoDataLoaderParams params )
    {
        final ByteSource source = params.getSource();

        boolean firstRow = true;

        try (InputStream in = source.openStream();
             InputStreamReader isr = new InputStreamReader( in, Charset.forName( "UTF-8" ) );
             BufferedReader br = new BufferedReader( isr );
        )
        {
            String line;

            while ( ( line = br.readLine() ) != null )
            {
                if ( firstRow && params.isHasHeaderRow() )
                {
                    firstRow = false;
                    continue;
                }

                processLine( line, params );
            }
        }
        catch ( IOException e )
        {

        }
    }

    private void processLine( final String line, final GeoDataLoaderParams params )
    {
        final Map<String, String> valueMap = params.getFormat().parse( line, params.isFailOnErrors() );
        params.getHandler().handle( valueMap );

    }
}
