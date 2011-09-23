package org.gadlets.match;

import org.gadlets.core.GadletDefinition;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class MatchAllMatcherTest {

    @Test
    public void testMatch() {
        MatchAllMatcher matcher = new MatchAllMatcher();
        GadletDefinition gadletDefinition1 = new GadletDefinition("test1Name", "testPath", false);
        gadletDefinition1.putKeyword("test1");

        GadletDefinition gadletDefinition2 = new GadletDefinition("test2Name", "testPath", false);
        gadletDefinition2.putKeyword("test2");

        Set<GadletDefinition> gadletDefinitions = new HashSet<GadletDefinition>();
        gadletDefinitions.add(gadletDefinition1);
        gadletDefinitions.add(gadletDefinition2);

        Collection<GadletDefinition> actual = matcher.match(gadletDefinitions);
        Assert.assertTrue(actual.size() == 2);
    }

}
