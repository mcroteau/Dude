package dev.yougo;

import dev.yougo.access.MockAccessor;
import dev.yougo.filters.DudeFilter;
import dev.yougo.utils.TestConstants;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SessionUserTest {

    private static Logger log = Logger.getLogger(SessionUserTest.class);

    public SessionUserTest(){
        BasicConfigurator.configure();
    }

    @Test
    public void testGetUser() throws InterruptedException{

        DudeFilter filter = new DudeFilter();
        MockAccessor accessor =  new MockAccessor();
        Dude.setAccessor(accessor);

        MockUtil mockUtil = new MockUtil();

        for(int n = 0; n < TestConstants.MOCK_REQUESTS; n++) {

            Thread thread = new Thread(){
                public void run() {
                    try {

                        HttpServletRequest req = new MockHttpServletRequest();
                        HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

                        FilterChain filterChain = Mockito.mock(FilterChain.class);
                        FilterConfig config = Mockito.mock(FilterConfig.class);

                        filter.init(config);
                        filter.doFilter(req, resp, filterChain);

                        Dude.login(mockUtil.getUser(), TestConstants.PASS);

                        filter.doFilter(req, resp, filterChain);

                        if(!Dude.getUser().equals(mockUtil.getUser())){
                            mockUtil.incrementCount();
                            log.info(Dude.getUser() + ":" + mockUtil.getUser());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            thread.start();
            thread.join();
            mockUtil.incrementIndex();
        }

        assertTrue(mockUtil.getCount() == 0);
    }

    public class MockUtil{
        int index = 0;
        int count = 0;

        public void incrementCount(){
            this.count++;
        }

        public void incrementIndex(){
            this.index++;
        }

        public int getCount(){
            return this.count;
        }

        public String getUser(){
            return "user" + this.index;
        }
    }
}
