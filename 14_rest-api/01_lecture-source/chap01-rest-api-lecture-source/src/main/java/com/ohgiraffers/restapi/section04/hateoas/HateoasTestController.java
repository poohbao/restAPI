package com.ohgiraffers.restapi.section04.hateoas;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class HateoasTestController {

    private List<UserDTO> users;

    public HateoasTestController() {
        users = new ArrayList<UserDTO>();

        users.add(new UserDTO(1, "user01", "pass01", "너구리", LocalDate.now()));
        users.add(new UserDTO(2, "user02", "pass02", "코알라", LocalDate.now()));
        users.add(new UserDTO(3, "user03", "pass03", "푸바오", LocalDate.now()));
    }

    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        UserDTO foundUser = users
                .stream()
                .filter(user -> user.getNo() == userNo)
                .findFirst()
                .orElse(null);

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공!!", responseMap);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(responseMessage);
    }

    /* Hateoas 적용 전체 조회 */
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        List<EntityModel<UserDTO>> userWithRel = users.stream().map(user -> EntityModel.of(
                user,
                linkTo(methodOn(HateoasTestController.class).findUserByNo(user.getNo())).withSelfRel(),
                linkTo(methodOn(HateoasTestController.class).findAllUsers()).withRel("users")
        )).collect(Collectors.toList());

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", userWithRel);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공!!", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }
}
