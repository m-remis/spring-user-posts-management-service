package com.michal.examples.user.posts.management.cache;

import com.michal.examples.user.posts.management.client.JsonPlaceHolderClient;
import com.michal.examples.user.posts.management.dto.response.JsonPlaceHolderUserResponseDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.michal.examples.user.posts.management.config.CacheConfig.FIND_USER_BY_ID_CACHE_NAME;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(value = "test")
class CacheTest {

    @Autowired
    private JsonPlaceHolderClient jsonPlaceHolderClient;

    @Autowired
    private CacheManager cacheManager;

    @Value("${application.placeholder-client.base-url}") String clientBaseUrl;

    @BeforeEach
    void setup() {
        Objects.requireNonNull(cacheManager.getCache(FIND_USER_BY_ID_CACHE_NAME)).clear();
    }

    @Test
    @DisplayName("Fetch data from third party, result should be cached")
    void testCache() {
        final Integer existingUserId = 1;
        List<JsonPlaceHolderUserResponseDto> clientResponse = jsonPlaceHolderClient.findUserById(clientBaseUrl, existingUserId);
        final var cache = cacheManager.getCache(FIND_USER_BY_ID_CACHE_NAME);
        Assertions.assertNotNull(cache);
        Assertions.assertNotNull(cache.get(List.of(existingUserId)));
        var stored = (ArrayList<JsonPlaceHolderUserResponseDto>) cache.get(List.of(existingUserId)).get();
        Assertions.assertEquals(clientResponse, stored);
    }
}
