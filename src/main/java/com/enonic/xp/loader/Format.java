package com.enonic.xp.loader;

import java.util.Map;

public interface Format
{
    Map<String, String> parse( final String value, final boolean failOnErrors );
}
