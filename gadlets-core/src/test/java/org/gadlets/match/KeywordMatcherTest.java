package org.gadlets.match;


import org.gadlets.core.GadletDefinition;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class KeywordMatcherTest {

    @Test
    public void testMatch() {
        KeywordMatcher matcher = new KeywordMatcher("test");
        GadletDefinition gadletDefinition1 = new GadletDefinition("test1Name", "testPath", false);
        gadletDefinition1.putKeyword("test");

        GadletDefinition gadletDefinition2 = new GadletDefinition("test2Name", "testPath", false);
        gadletDefinition2.putKeyword("test2");

        Set<GadletDefinition> gadletDefinitions = new HashSet<GadletDefinition>();
        gadletDefinitions.add(gadletDefinition1);
        gadletDefinitions.add(gadletDefinition2);

        Collection<GadletDefinition> actual = matcher.match(gadletDefinitions);
        Assert.assertTrue(actual.size() == 1);
        Assert.assertEquals(actual.iterator().next().getName(), "test1Name");
    }

}
