package fr.fistin.fistinframework.configuration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConfigurationMappingTest
{
    @Mock
    private ConfigurationMapping<String, String> testMappings;

    @BeforeEach
    public void setup() {
        final Map<String, Function<String, String>> mappings = new HashMap<>();
        when(this.testMappings.mappings()).thenReturn(mappings);
    }

    @Test
    public void testMap() {
        this.testMappings.mappings().put("%TEST_KEY%", String::toUpperCase);
        this.testMappings.mappings().put("%OTHER_KEY%", String::toLowerCase);
        when(this.testMappings.map("It is an %TEST_KEY%, and another %OTHER_KEY%.", "AmaZIng KEy nOW mAppED")).thenCallRealMethod();
        final String mapped = this.testMappings.map("It is an %TEST_KEY%, and another %OTHER_KEY%.", "AmaZIng KEy nOW mAppED");
        assertEquals("It is an AMAZING KEY NOW MAPPED, and another amazing key now mapped.", mapped);
    }
}