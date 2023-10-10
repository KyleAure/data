package ee.jakarta.tck.data.full.cdi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import ee.jakarta.tck.data.framework.junit.anno.Assertion;
import ee.jakarta.tck.data.framework.servlet.TestServlet;
import ee.jakarta.tck.data.web.example.ComplexServlet;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
@WebServlet(ComplexServlet.URL_PATTERN)
public class ExtensionServlet extends TestServlet {
    
    public static final String URL_PATTERN = "/ExtensionTestServlet";
    
    @Inject
    Directory directory;
    
    @Assertion(id = "133", strategy="TODO")
    public void testDataProviderWithExtension() {
        assertEquals(List.of("Kyle"), directory.findFirstNameByIdInOrderByAgeDesc(List.of(04L, 05L, 011L)));
        
    }

}
