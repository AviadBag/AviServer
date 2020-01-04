import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RequestTest {

    @DisplayName("Test getHeaders()")
    @Test
    void getHeaders() {
        String[] lines = {"Connection: keep-alive\n", "Cache-Control: max-age=0\n", "Upgrade-Insecure-Requests: 1\n"};
        String[] s = lines[0].split(":");
        assertEquals("Connection", s[0]);
    }
}