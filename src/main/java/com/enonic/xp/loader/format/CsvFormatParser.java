package com.enonic.xp.loader.format;

import java.util.List;

import com.google.common.collect.Lists;

import com.enonic.xp.loader.FieldUtil;

public class CsvFormatParser
{
    public static Format parse( final String header )
    {
        final String[] split = header.split( "," );

        List<Field> formatList = Lists.newLinkedList();

        for ( final String element : split )
        {
            final String fieldName = FieldUtil.clean( element );
            formatList.add( Field.create().
                name( fieldName ).
                alias( fieldName ).
                skip( false ).
                build() );
        }

        return new Format( formatList );
    }
}