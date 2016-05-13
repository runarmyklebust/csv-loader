package com.enonic.xp.loader;

import java.util.Map;

import com.google.common.io.ByteSource;

import com.enonic.xp.data.PropertyTree;
import com.enonic.xp.node.CreateNodeParams;
import com.enonic.xp.node.Node;
import com.enonic.xp.node.NodePath;
import com.enonic.xp.node.NodeService;
import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;
import com.enonic.xp.util.GeoPoint;

public class CSVLoaderBean
    implements ScriptBean
{
    private NodeService nodeService;

    public LoadResult load( final ByteSource source, final String format, final boolean hasHeader )
    {
        final CSVDataLoader csvDataLoader = new CSVDataLoader();

        final TestEntryHandler testEntryHandler = new TestEntryHandler( this.nodeService );

        final long start = System.currentTimeMillis();

        csvDataLoader.load( LoaderParams.create().
            format( CSVFormat.from( format ) ).
            failOnErrors( true ).
            hasHeaderRow( hasHeader ).
            handler( testEntryHandler ).
            source( source ).
            build() );

        final long timeUsed = System.currentTimeMillis() - start;

        return new LoadResult( timeUsed, testEntryHandler.total );
    }

    private class TestEntryHandler
        implements EntryHandler
    {
        private NodeService nodeService;

        private int total = 0;

        public TestEntryHandler( final NodeService nodeService )
        {
            this.nodeService = nodeService;
        }

        @Override
        public void handle( final Map<String, String> values )
        {
            final PropertyTree data = new PropertyTree();
            data.setString( "streetName", values.get( "GATENAVN" ) );
            data.setString( "houseNum", values.get( "HUSNUMMER" ) );
            data.setString( "poNum", values.get("POSTNUMMER"));
            data.setString( "municipality", values.get("KOMMUNE"));
            data.setString( "fylke", values.get("FYLKE"));

            //data.setGeoPoint( "location", GeoPoint.from( values.get( "LATITUDE" ) + "," + values.get( "LONGITUDE" ) ) );

            final Node node = this.nodeService.create( CreateNodeParams.create().
                name( values.get( "LOKASJONS_ID" ) ).
                parent( NodePath.ROOT ).
                data( data ).
                build() );

            total++;
        }
    }


    @Override
    public void initialize( final BeanContext context )
    {
        this.nodeService = context.getService( NodeService.class ).get();
    }
}