package org.gadlets.scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: karl
 * Date: 6/19/11
 * Time: 3:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class GadletScanner {
    public static final String META_INF_GADLETS_XML = "META-INF/gadlets.xml";
    private ClassLoader classLoader;
    private static final Logger log = LoggerFactory.getLogger(GadletScanner.class);

    public GadletScanner() {
        classLoader = getClass().getClassLoader();
    }

    public GadletScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;

    }

    public Set<URL> getGadletXmlUrls() {
        try {
            Enumeration<URL> urlEnum = classLoader.getResources(META_INF_GADLETS_XML);
            Set<URL> urls = new HashSet<URL>();
            while (urlEnum.hasMoreElements()) {
                urls.add(urlEnum.nextElement());
            }
            return urls;
        } catch (Exception e) {
            throw new RuntimeException("Unable to scan for gadlets.xml", e);
        }

    }


}
