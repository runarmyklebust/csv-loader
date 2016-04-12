package com.enonic.xp.loader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.Test;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;

public class CSVDataLoaderTest
{

    @Test
    public void cvs_loader_test_1()
        throws Exception
    {
        final long start = System.currentTimeMillis();

        final ByteSource byteSource = getAsByteSource( "world_cities.csv" );

        new CSVDataLoader().load( GeoDataLoaderParams.create().
            format( CSVFormat.from( "city,city_ascii,lat,lng,pop,country,iso2,iso3,province" ) ).
            hasHeaderRow( true ).
            source( byteSource ).
            handler( new TestEntryHandler() ).
            build() );

        System.out.println( "Time used: " + ( System.currentTimeMillis() - start ) );
    }

    @Test
    public void cvs_loader_test_2()
        throws Exception
    {
        final long start = System.currentTimeMillis();

        final ByteSource byteSource = getAsByteSource( "cities_with_populations.txt" );

        final TestEntryHandler handler = new TestEntryHandler();
        new CSVDataLoader().load( GeoDataLoaderParams.create().
            format( CSVFormat.from( "Country,City,AccentCity,Region,Population,Latitude,Longitude" ) ).
            hasHeaderRow( false ).
            source( byteSource ).
            handler( handler ).
            build() );

        System.out.println( "Time used: " + ( System.currentTimeMillis() - start ) + " on " + handler.total + " entries" );
    }


    private class TestEntryHandler
        implements EntryHandler
    {
        long total = 0;

        @Override
        public void handle( final Map<String, String> values )
        {
            total++;
        }
    }


    private String getAsString( final String fileName )
        throws IOException
    {
        return new String( getAsByteSource( fileName ).read(), StandardCharsets.UTF_8 );
    }

    private ByteSource getAsByteSource( final String fileName )
        throws IOException
    {
        return ByteSource.wrap( ByteStreams.toByteArray( this.getClass().getResourceAsStream( fileName ) ) );
    }
}