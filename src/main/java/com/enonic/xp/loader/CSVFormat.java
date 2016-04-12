package com.enonic.xp.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class CSVFormat
    implements Format
{
    private final static Pattern pattern = Pattern.compile( "\\\"([^\\\"]*)\\\"|(?<=,|^)([^,]*)(?=,|$)" );

    private final List<String> format;

    @Override
    public Map<String, String> parse( final String input, final boolean failOnErrors )
    {
        final HashMap<String, String> valueMap = Maps.newHashMap();

        String[] splitted = input.split( ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)" );

        int i = 0;
        for ( final String value : splitted )
        {
            try
            {
                valueMap.put( format.get( i++ ), value );
            }
            catch ( Exception e )
            {
                if ( failOnErrors )
                {
                    throw new RuntimeException( "Failed to parse line " + input, e );
                }
                else
                {
                    System.out.println( "Warning: Failed to parse [" + input + "]" );
                }
            }
        }

        return valueMap;
    }

    public CSVFormat( final List<String> format )
    {
        this.format = format;
    }

    public static CSVFormat from( final String format )
    {
        final String[] split = format.split( "," );

        List<String> formatList = Lists.newLinkedList();

        for ( final String element : split )
        {
            formatList.add( element );
        }

        return new CSVFormat( formatList );
    }
}
