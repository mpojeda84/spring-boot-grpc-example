package com.demo.backend.service;

import com.demo.backend.domain.User;
import com.demo.backend.dto.UserDto;
import com.demo.backend.repository.UserRepository;
import com.demo.backend.util.Mapper;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@GrpcService
public class UserServiceImpl extends UserServiceGrpc.UserServiceImplBase implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void retrieve(RetrieveUserRequest request, StreamObserver<UserResponse> responseObserver) {
        System.out.println(request.toString());

        UserResponse userResponse = getUserResponse(request.getId());

        responseObserver.onNext(userResponse);
        responseObserver.onCompleted();

    }

    private UserResponse getUserResponse(Integer id) {
        User user = userRepository.findById(id).orElse(null);
        UserResponse userResponse = Mapper.getInstance().toUserResponse(user);
        return userResponse;
    }

    @Override
    public UserDto create(UserDto userDto) {
        User user = Mapper.getInstance().toUser(userDto);
        User saved = userRepository.save(user);
        return Mapper.getInstance().toUserDto(saved);
    }

    @Override
    public Set<UserDto> getByCountry(String country) {
        return userRepository.findByAddresses_CountryEquals(country).stream().map(x -> Mapper.getInstance().toUserDto(x)).collect(Collectors.toSet());
  }


}
