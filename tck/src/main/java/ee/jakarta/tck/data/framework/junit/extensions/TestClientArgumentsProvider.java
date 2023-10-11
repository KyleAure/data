package ee.jakarta.tck.data.framework.junit.extensions;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.jboss.arquillian.junit5.ArquillianExtension;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import ee.jakarta.tck.data.framework.junit.anno.Assertion;
import ee.jakarta.tck.data.framework.junit.anno.RunAsServletClient;
import ee.jakarta.tck.data.framework.servlet.TestClient;
import jakarta.servlet.annotation.WebServlet;

/**
 * An Argument Provider for a paramaterized test. 
 * Arguments: 
 *     TestClient - The test client class that contains tests to be executed.
 *     TestMethod - The method on the test client to execute.
 */
public class TestClientArgumentsProvider implements ArgumentsProvider {
    
    private static final Logger log = Logger.getLogger(TestClientArgumentsProvider.class.getCanonicalName());

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        
        //Skip test by returning empty stream
        final Stream<? extends Arguments> skip = Stream.of();
        
        //We only run parameterized tests on the client, not in the container.
        if(Boolean.parseBoolean(context.getConfigurationParameter(ArquillianExtension.RUNNING_INSIDE_ARQUILLIAN).orElse("false"))) {
            log.info("Inside Jakarta EE Platform, skipping TestClient param tests."); //TODO fine
            return skip;
        }
        
        Class<?> testClient = context.getTestClass().orElseThrow();
        
        //We only want to run this parameterized test against a test class that is using TestClient
        //This will prevent other test classes from using this argument provider
        if(! TestClient.class.isAssignableFrom(testClient)) {
            log.info("The test class was not a TestClient, skipping TestClient param tests."); //TODO fine
            return skip;
        }
        
        // We only want to run this parameterized test if it is annotated with RunAsServletClient, otherwise
        // we will not know which servlet to test against.
        if(! testClient.isAnnotationPresent(RunAsServletClient.class) ) {
            log.info("The TestClient was not annotated with RunAsServletClient, skipping TestClient template tests."); //TODO fine
            return skip;
        }
        
        // We only want to run this parameterized test if the TestServlet is annotated with @WebServlet
        Class<?> testServlet = testClient.getDeclaredAnnotation(RunAsServletClient.class).value();
        if(! testServlet.isAnnotationPresent(WebServlet.class)) {
            log.info("The TestServlet was not annotated with WebServlet.");
            return skip;
        }
        
        String servletPath = testServlet.getAnnotation(WebServlet.class).value()[0];
        
        /**
         * Only execute test methods that are public, non-static, and annotated with @Assertion
         */
        return Arrays.asList(testServlet.getMethods()).stream()
            .filter(method -> Modifier.isPublic(method.getModifiers()) && !Modifier.isStatic(method.getModifiers()))
            .filter(method -> method.isAnnotationPresent(Assertion.class))
            .map(method -> Arguments.of(servletPath, method.getName()));
    }
}
