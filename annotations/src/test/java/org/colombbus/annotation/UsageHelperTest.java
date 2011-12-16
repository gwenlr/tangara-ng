package org.colombbus.annotation;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

@SuppressWarnings("nls")
public class UsageHelperTest {

    private Collection<String> USAGE_LIST = Arrays.asList("getAddress(\"bob\")",
            "getVersion()", "addAddress(\"bob\", \"bob's address\")");
    private Collection<String> USAGE_METHOD_LIST = Arrays.asList("getAddress()", "getVersion()",
            "addAddress()");

    /**
     * @throws Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
    }

    /**
     * @throws Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindUsagesClass_nullArg() {
        Class<?> clazz = null;
        UsageHelper.findUsages(clazz);
    }

    @Test
    public void testFindUsagesClass() {
        List<String> usageList = UsageHelper.findUsages(UsageAddressBook.class);
        assertEquals(USAGE_LIST.size(), usageList.size());
        assertTrue( USAGE_LIST.containsAll(usageList));
        for (String usage : usageList) {
            System.out.println("findUsages: " + usage); //$NON-NLS-1$
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindUsagesObject_nullArg() {
        Object obj = null;
        UsageHelper.findUsages(obj);
    }

    @Test
    public void testFindUsagesObject() {
        UsageAddressBook addressBook = new UsageAddressBook();
        List<String> usageList = UsageHelper.findUsages(addressBook);
        assertEquals(USAGE_LIST.size(), usageList.size());
        assertTrue( USAGE_LIST.containsAll(usageList));

        for (String usage : usageList) {
            System.out.println("findUsages: " + usage); //$NON-NLS-1$
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindUsageMethodsClass_nullArg() {
        Class<?> clazz = null;
        UsageHelper.findUsages(clazz);
    }

    @Test
    public void testFindUsageMethodsClass() {
        List<String> usageMethodList = UsageHelper.findUsageMethods(UsageAddressBook.class);
        assertEquals(USAGE_METHOD_LIST.size(), usageMethodList.size());
        assertTrue( USAGE_METHOD_LIST.containsAll(usageMethodList));

        for (String usageMethod : usageMethodList) {
            System.out.println("findUsageMethods: " + usageMethod);
            assertTrue(usageMethod.contains("()")); //$NON-NLS-1$
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindUsageMethodsObject_nullArg() {
        Object obj = null;
        UsageHelper.findUsages(obj);
    }

    @Test
    public void testFindUsageMethodsObject() {
        UsageAddressBook addressBook = new UsageAddressBook();
        List<String> usageMethodList = UsageHelper.findUsageMethods(addressBook);
        assertEquals(USAGE_METHOD_LIST.size(), usageMethodList.size());
        assertTrue( USAGE_METHOD_LIST.containsAll(usageMethodList));

        for (String usageMethod : usageMethodList) {
            System.out.println("findUsageMethods: " + usageMethod);
            assertTrue(usageMethod.contains("()")); //$NON-NLS-1$
        }
    }

}
