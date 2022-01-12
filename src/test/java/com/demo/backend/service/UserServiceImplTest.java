package com.demo.backend.service;

import com.demo.backend.domain.Address;
import com.demo.backend.domain.User;
import com.demo.backend.dto.AddressDto;
import com.demo.backend.dto.UserDto;
import com.demo.backend.repository.UserRepository;
import com.demo.backend.util.Mapper;
import io.grpc.internal.testing.StreamRecorder;
import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldReturnAUser() throws Exception {
        Mockito.when(userRepository.findById(1)).thenReturn(Optional.of(new User("Maikel", "Pereira", "maikel@gmail.com", "abc123",null)));

        RetrieveUserRequest request = RetrieveUserRequest.newBuilder().setId(1).build();
        StreamRecorder<UserResponse> responseObserver = StreamRecorder.create();

        userService.retrieve(request, responseObserver);
        if (!responseObserver.awaitCompletion(5, TimeUnit.SECONDS)) {
            fail("The call did not terminate in time");
        }
        assertNull(responseObserver.getError());
        List<UserResponse> results = responseObserver.getValues();
        assertNotNull(results);
        assertEquals("maikel@gmail.com", results.get(0).getEmail());
    }

    @Test
    void shouldCreate() {
        UserDto dto = new UserDto(1, "Maikel", "Pereira", "maikel@gmail.com",
                "abc123", Set.of(new AddressDto("a1","a2", "Miami", "FL",
                "33066", "USA" )));

        User user = new User("Maikel", "Pereira", "maikel@gmail.com",
                "abc123", null);

        Address address = new Address("a1","a2", "Miami", "FL",
                "33066", "USA" , user);

        user.setAddresses(Set.of(address));

        Mockito.when(userRepository.save(user)).thenReturn(user);

        UserDto result = userService.create(dto);

        assertEquals("maikel@gmail.com", result.getEmail());

    }

    @Test
    void shouldGetByCountry() {
        UserDto dto = new UserDto(1, "Maikel", "Pereira", "maikel@gmail.com",
                "abc123", Set.of(new AddressDto("a1","a2", "Miami", "FL",
                "33066", "USA" )));

        User user = new User("Maikel", "Pereira", "maikel@gmail.com",
                "abc123", null);

        Address address = new Address("a1","a2", "Miami", "FL",
                "33066", "USA" , user);

        user.setAddresses(Set.of(address));

        Mockito.when(userRepository.findByAddresses_CountryEquals("USA")).thenReturn(List.of(user));

        Set<UserDto> result = userService.getByCountry("USA");

        assertEquals(1, result.size());
        UserDto result1 = result.stream().findFirst().get();
        assertEquals("maikel@gmail.com", result1.getEmail());

    }
}