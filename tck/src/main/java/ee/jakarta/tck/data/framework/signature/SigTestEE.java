/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */
package ee.jakarta.tck.data.framework.signature;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Properties;

import ee.jakarta.tck.data.framework.utilities.TestProperty;

/**
 * This class should be extended by TCK developers that wish to create a set of
 * signature tests that run inside all the Java EE containers. Developers must
 * implement the getPackages method to specify which packages are to be tested
 * by the signature test framework within which container.
 */
public abstract class SigTestEE {

    String[] sVehicles;

//    private Object theSharedObject;
//
//    private Object theSharedObjectArray[];

    protected SignatureTestDriver driver;

    /**
     * <p>
     * Returns a {@link SignatureTestDriver} appropriate for the particular TCK
     * (using API check or the Signature Test Framework).
     * </p>
     *
     * <p>
     * The default implementation of this method will return a
     * {@link SignatureTestDriver} that will use API Check. TCK developers can
     * override this to return the desired {@link SignatureTestDriver} for their
     * TCK.
     * 
     * @return Instance of SignatureTestDriver
     */
    protected SignatureTestDriver getSigTestDriver() {

        if (driver == null) {
            driver = SignatureTestDriverFactory.getInstance(SignatureTestDriverFactory.SIG_TEST);
        }

        return driver;

    } // END getSigTestDriver

    /**
     * <p>
     * Returns the location of the package list file. This file denotes the valid
     * sub-packages of any package being verified in the signature tests.
     * </p>
     * 
     * Sub-classes are free to override this method if they use a different path or
     * filename for their package list file. Most users should be able to use this
     * default implementation.
     *
     * @return String The path and name of the package list file.
     */
    protected String getPackageFile() {
        return getSigTestDriver().getPackageFileImpl();
    }

    /**
     * <p>
     * Returns the path and name of the signature map file that this TCK uses when
     * conducting signature tests. The signature map file tells the signature test
     * framework which API versions of tested packages to use. To keep this code
     * platform independent, be sure to use the File.separator string (or the
     * File.separatorChar) to denote path separators.
     * </p>
     * 
     * Sub-classes are free to override this method if they use a different path or
     * filename for their signature map file. Most users should be able to use this
     * default implementation.
     *
     * @return String The path and name of the signature map file.
     */
    protected String getMapFile() {
        return getSigTestDriver().getMapFileImpl();
    }

    /**
     * <p>
     * Returns the directory that contains the signature files.
     * </p>
     * 
     * Sub-classes are free to override this method if they use a different
     * signature repository directory. Most users should be able to use this default
     * implementation.
     *
     * @return String The signature repository directory.
     */
    protected String getRepositoryDir() {
        return getSigTestDriver().getRepositoryDirImpl();
    }

    /**
     * <p>
     * Returns the list of Optional Packages which are not accounted for. By
     * 'unlisted optional' we mean the packages which are Optional to the technology
     * under test that the user did NOT specifically list for testing. For example,
     * with Java EE 7 implementation, a user could additionally opt to test a JSR-88
     * technology along with the Java EE technology. But if the user chooses NOT to
     * list this optional technology for testing (via ts.jte javaee.level prop) then
     * this method will return the packages for JSR-88 technology with this method
     * call.
     * </p>
     * 
     * <p>
     * This is useful for checking for a scenarios when a user may have forgotten to
     * identify a whole or partial technology implementation and in such cases, Java
     * EE platform still requires testing it.
     * </p>
     * 
     * <p>
     * Any partial or complete impl of an unlistedOptionalPackage sends up a red
     * flag indicating that the user must also pass tests for this optional
     * technology area.
     * </p>
     * 
     * Sub-classes are free to override this method if they use a different
     * signature repository directory. Most users should be able to use this default
     * implementation - which means that there was NO optional technology packages
     * that need to be tested.
     *
     * @return List of unlisted packages
     */
    protected ArrayList<String> getUnlistedOptionalPackages() {
        return null;
    }

    /**
     * Returns the list of packages that must be tested by the signature test
     * framework. TCK developers must implement this method in their signature test
     * sub-class.
     *
     * @return String[] A list of packages that the developer wishes to test using
     *         the signature test framework. If the developer does not wish to test
     *         any package signatures in the specified vehicle this method should
     *         return null.
     *         <p>
     *         Note, The proper way to insure that this method is not called with a
     *         vehicle name that has no package signatures to verify is to modify
     *         the vehicle.properties in the $TS_HOME/src directory. This file
     *         provides a mapping that maps test directories to a list of vehicles
     *         where the tests in those directory should be run. As an extra
     *         precaution users are encouraged to return null from this method if
     *         the specified vehicle has no package signatures to be verified within
     *         it.
     */
    protected abstract String[] getPackages();

    /**
     * <p>
     * Returns an array of individual classes that must be tested by the signature
     * test framework within the specified vehicle. TCK developers may override this
     * method when this functionality is needed. Most will only need package level
     * granularity.
     * </p>
     *
     * <p>
     * If the developer doesn't wish to test certain classes within a particular
     * vehicle, the implementation of this method must return a zero-length array.
     * </p>
     *
     * @return an Array of Strings containing the individual classes the framework
     *         should test based on the specifed vehicle. The default implementation
     *         of this method returns a zero-length array no matter the vehicle
     *         specified.
     */
    protected String[] getClasses() {
        return new String[] {};
    } // END getClasses

    /**
     * Called by the test framework to initialize this test. The method simply
     * retrieves some state information that is necessary to run the test when when
     * the test framework invokes the run method (actually the test1 method).
     */
    public void setup() {
        try {
            System.out.println("$$$ SigTestEE.setup() called");
            System.out.println("$$$ SigTestEE.setup() complete");
        } catch (Exception e) {
            System.out.println("Unexpected exception " + e.getMessage());
        }
    }

    /**
     * Called by the test framework to run this test. This method utilizes the state
     * information set in the setup method to run the signature tests. All signature
     * test code resides in the utility class so it can be reused by the signature
     * test framework base classes.
     *
     * @throws Fault When an error occurs executing the signature tests.
     */
    public void signatureTest() throws Fault {
        System.out.println("$$$ SigTestEE.signatureTest() called");
        SigTestResult results = null;
        String mapFile = getMapFile();
        String repositoryDir = getRepositoryDir();
        String[] packages = getPackages();
        String[] classes = getClasses();
        String packageFile = getPackageFile();
        String testClasspath = TestProperty.signatureClasspath.getValue();
        String optionalPkgToIgnore = "";

        // unlisted optional packages are technology packages for those optional
        // technologies (e.g. jsr-88) that might not have been specified by the
        // user.
        // We want to ensure there are no full or partial implementations of an
        // optional technology which were not declared
        ArrayList<String> unlistedTechnologyPkgs = getUnlistedOptionalPackages();

        // If testing with Java 9+, extract the JDK's modules so they can be used
        // on the testcase's classpath.
        Properties sysProps = System.getProperties();
        String version = (String) sysProps.get("java.version");
        if (!version.startsWith("1.")) {
            String jimageDir = TestProperty.signatureImageDir.getValue();
            File f = new File(jimageDir);
            f.mkdirs();

            String javaHome = (String) sysProps.get("java.home");
            System.out.println("Executing JImage");

            try {
                ProcessBuilder pb = new ProcessBuilder(javaHome + "/bin/jimage", "extract", "--dir=" + jimageDir,
                        javaHome + "/lib/modules");
                System.out
                        .println(javaHome + "/bin/jimage extract --dir=" + jimageDir + " " + javaHome + "/lib/modules");
                pb.redirectErrorStream(true);
                Process proc = pb.start();
                BufferedReader out = new BufferedReader(new InputStreamReader(proc.getInputStream()));
                String line = null;
                while ((line = out.readLine()) != null) {
                    System.out.println(line);
                }

                int rc = proc.waitFor();
                System.out.println("JImage RC = " + rc);
                out.close();
            } catch (Exception e) {
                System.out.println("Exception while executing JImage!  Some tests may fail.");
                e.printStackTrace();
            }
        }

        try {
            results = getSigTestDriver().executeSigTest(packageFile, mapFile, repositoryDir, packages, classes,
                    testClasspath, unlistedTechnologyPkgs, optionalPkgToIgnore);
            System.out.println(results.toString());
            if (!results.passed()) {
                System.out.println("results.passed() returned false");
                throw new Exception();
            }

            System.out.println("$$$ SigTestEE.signatureTest() returning");
        } catch (Exception e) {
            if (results != null && !results.passed()) {
                throw new Fault("SigTestEE.signatureTest() failed!, diffs found");
            } else {
                System.out.println("Unexpected exception " + e.getMessage());
                throw new Fault("signatureTest failed with an unexpected exception", e);
            }
        }
    }

    /**
     * Called by the test framework to cleanup any outstanding state. This method
     * simply passes the message through to the utility class so the implementation
     * can be used by both framework base classes.
     *
     * @throws Fault When an error occurs cleaning up the state of this test.
     */
    public void cleanup() throws Fault {
        System.out.println("$$$ SigTestEE.cleanup() called");
        try {
            getSigTestDriver().cleanupImpl();
            System.out.println("$$$ SigTestEE.cleanup() returning");
        } catch (Exception e) {
            throw new Fault("Cleanup failed!", e);
        }
    }

    public static class Fault extends Exception {

        private static final long serialVersionUID = -1574745208867827913L;


        public Throwable t;

        /**
         * creates a Fault with a message
         * @param msg - error message
         */
        public Fault(String msg) {
            super(msg);
            System.out.println(msg);
        }

        /**
         * creates a Fault with a message.
         *
         * @param msg the message
         * @param t   prints this exception's stacktrace
         */
        public Fault(String msg, Throwable t) {
            super(msg);
            this.t = t;
            System.out.println(msg);
            t.printStackTrace();
        }

        /**
         * creates a Fault with a Throwable.
         *
         * @param t the Throwable
         */
        public Fault(Throwable t) {
            super(t);
            this.t = t;
        }

        /**
         * Prints this Throwable and its backtrace to the standard error stream.
         *
         */
        public void printStackTrace() {
            if (this.t != null) {
                this.t.printStackTrace();
            } else {
                super.printStackTrace();
            }
        }

        /**
         * Prints this throwable and its backtrace to the specified print stream.
         */
        public void printStackTrace(PrintStream s) {
            if (this.t != null) {
                this.t.printStackTrace(s);
            } else {
                super.printStackTrace(s);
            }
        }

        /**
         * Prints this throwable and its backtrace to the specified print writer.
         */
        public void printStackTrace(PrintWriter s) {
            if (this.t != null) {
                this.t.printStackTrace(s);
            } else {
                super.printStackTrace(s);
            }
        }

        @Override
        public Throwable getCause() {
            return t;
        }

        @Override
        public synchronized Throwable initCause(Throwable cause) {
            if (t != null)
                throw new IllegalStateException("Can't overwrite cause");
            if (!Exception.class.isInstance(cause))
                throw new IllegalArgumentException("Cause not permitted");
            this.t = (Exception) cause;
            return this;
        }
    }

} // end class SigTestEE
