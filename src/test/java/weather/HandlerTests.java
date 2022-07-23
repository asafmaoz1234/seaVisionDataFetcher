package weather;

import com.amazonaws.services.lambda.runtime.Context;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.spy;

@RunWith(MockitoJUnitRunner.class)
public class HandlerTests {

    @Spy
    Handler handler = spy(new Handler());

    Map<String, String> eventMap;
    Context context = new TestContext();

    @Before
    public void setUp() {
        eventMap = new HashMap<>();
        eventMap.put("key1", "val1");
        eventMap.put("key2", "val2");
    }

    @Test
    public void initTest() {
        handler.handleRequest(eventMap, context);
    }
}
