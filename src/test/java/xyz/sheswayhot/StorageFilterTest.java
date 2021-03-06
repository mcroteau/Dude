package xyz.sheswayhot;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.junit.jupiter.api.Test;
import org.apache.log4j.Logger;
import org.apache.log4j.BasicConfigurator;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import xyz.sheswayhot.access.MockAccessor;
import xyz.sheswayhot.filters.DudeFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xyz.sheswayhot.utils.AuthdIncrementor;
import xyz.sheswayhot.utils.TestConstants;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class StorageFilterTest {

    private static Logger log = Logger.getLogger(StorageFilterTest.class);

    StorageFilterTest(){
        BasicConfigurator.configure();
    }

    @Test
    public void stressTestFilter() throws ServletException, IOException, InterruptedException {

        AuthdIncrementor incrementer = new AuthdIncrementor();
        DudeFilter filter = new DudeFilter();
        MockAccessor accessor = new MockAccessor();
        Dude.setAccessor(accessor);

        for(int n = 0; n < TestConstants.MOCK_REQUESTS; n++) {
            Thread thread = new Thread(){
                @Override
                public void run(){
                    try {

                        HttpServletRequest req = new MockHttpServletRequest();
                        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);
                        FilterConfig config = Mockito.mock(FilterConfig.class);
                        FilterChain filterChain = Mockito.mock(FilterChain.class);

                        filter.init(config);
                        filter.doFilter(req, resp, filterChain);

                        Dude.login(TestConstants.USER, TestConstants.PASS);

                        if(Dude.isAuthenticated()){
                            incrementer.increment();
                        }

                        filter.destroy();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ServletException e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.join();
        }

        log.info(incrementer.getCount() + " .");
        assertTrue(incrementer.getCount() == TestConstants.MOCK_REQUESTS);
    }

}
