/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.deltaspike.core.impl.config;

import org.apache.deltaspike.core.impl.util.JndiUtils;
import org.apache.deltaspike.core.spi.config.ConfigSource;

import javax.enterprise.inject.Typed;

/**
 * {@link ConfigSource} which uses JNDI for the lookup
 */
@Typed()
class LocalJndiConfigSource extends ConfigSource
{
    private static final String BASE_NAME = "java:comp/env/deltaspike/";

    /**
     * {@inheritDoc}
     */
    @Override
    protected int getDefaultOrdinal()
    {
        return 300;
    }

    /**
     * The given key gets used for a lookup via JNDI
     *
     * @param key for the property
     * @return value for the given key or null if there is no configured value
     */
    @Override
    public String getPropertyValue(String key)
    {
        try
        {
            String jndiKey;
            if (key.startsWith("java:comp/env"))
            {
                jndiKey = key;
            }
            else
            {
                jndiKey = BASE_NAME + key;
            }

            return JndiUtils.lookup(jndiKey, String.class);
        }
        catch (Exception e)
        {
            //do nothing it was just a try
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getConfigName()
    {
        return BASE_NAME;
    }
}
