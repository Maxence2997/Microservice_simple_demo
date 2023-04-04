package idv.laborLab.userService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import idv.laborLab.userService.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.time.LocalDate;

//@SpringBootTest
class UserServerApplicationTest {

	@Test
	void contextLoads() throws JsonProcessingException {

		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule())
                                        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false).build();
		GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(mapper);
		UserDTO userDTO = UserDTO.builder().userName("Maxxence2997").dateOfBirth(LocalDate.now()).build();
//        LocalDate localDate = LocalDate.now();
        String result = mapper.writeValueAsString(userDTO);
        System.out.println(result);
	}
}
