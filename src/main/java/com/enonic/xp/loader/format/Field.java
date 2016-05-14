package com.enonic.xp.loader.format;

public class Field
{

    private final String name;

    private final String alias;

    private final boolean skip;

    private Field( final Builder builder )
    {
        name = builder.name;
        alias = builder.alias;
        skip = builder.skip;
    }

    public String getName()
    {
        return name;
    }

    public String getAlias()
    {
        return alias;
    }

    public boolean isSkip()
    {
        return skip;
    }

    public static Builder create()
    {
        return new Builder();
    }

    public static final class Builder
    {
        private String name;

        private String alias;

        private boolean skip;

        private Builder()
        {
        }

        public Builder name( final String val )
        {
            name = val;
            return this;
        }

        public Builder alias( final String val )
        {
            alias = val;
            return this;
        }

        public Builder skip( final boolean val )
        {
            skip = val;
            return this;
        }

        public Field build()
        {
            return new Field( this );
        }
    }
}
