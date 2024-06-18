package com.ohgiraffers.restapi.section05.swagger;

import com.ohgiraffers.restapi.section02.responseentity.ResponseMessage;
import com.ohgiraffers.restapi.section04.hateoas.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/* @Tag : API 들의 그룹을 짓기 위한 어노테이션 */
@Tag(name = "Spring Boot Swagger 연동 (USER 기능)")
@RestController
@RequestMapping("/swagger")
public class SwaggerTestController {

    private List<UserDTO> users;

    public SwaggerTestController() {
        users = new ArrayList<>();
        users.add(new UserDTO(1, "user01", "pass01", "너구리", LocalDate.now()));
        users.add(new UserDTO(2, "user02", "pass02", "코알라", LocalDate.now()));
        users.add(new UserDTO(3, "user03", "pass03", "푸바오", LocalDate.now()));
    }

    @Operation(summary = "전체 회원 조회", description = "우리 사이트의 전체 회원 목록 조회")
    @GetMapping("/users")
    public ResponseEntity<ResponseMessage> findAllUsers() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("users", users);

        ResponseMessage responseMessage = new ResponseMessage(200, "조회 성공!!!", responseMap);

        return new ResponseEntity<>(responseMessage, headers, HttpStatus.OK);
    }

    @Operation(summary = "회원번호로 회원 조회", description = "회원번호를 통해 해당되는 회원을 조회한다.",
            parameters = { @Parameter(name = "userNo", description = "사용자 화면에서 넘어오는 user 의 pk") })
    @GetMapping("/users/{userNo}")
    public ResponseEntity<ResponseMessage> findUserByNo(@PathVariable int userNo) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        Optional<UserDTO> foundUser = users.stream().filter(user -> user.getNo() == userNo).findFirst();

        if (!foundUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("user", foundUser.get());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new ResponseMessage(200, "조회 성공!!!", responseMap));
    }

    @Operation(summary = "신규회원 등록")
    @PostMapping("/users")
    public ResponseEntity<?> regist(@RequestBody UserDTO newUser) {
        int lastUserNo = users.get(users.size() - 1).getNo();
        newUser.setNo(lastUserNo + 1);

        users.add(newUser);

        return ResponseEntity
                .created(URI.create("/swagger/users/" + newUser.getNo()))
                .build();
    }

    @Operation(summary = "회원 정보 수정")
    @PutMapping("/users/{userNo}")
    public ResponseEntity<?> modifyUser(@PathVariable int userNo, @RequestBody UserDTO modifyInfo) {
        Optional<UserDTO> foundUser = users.stream().filter(user -> user.getNo() == userNo).findFirst();

        if (!foundUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        UserDTO user = foundUser.get();
        user.setId(modifyInfo.getId());
        user.setPwd(modifyInfo.getPwd());
        user.setName(modifyInfo.getName());

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "회원 정보 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "회원정보 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "잘못 입력 된 파라미터")
    })
    @DeleteMapping("/users/{userNo}")
    public ResponseEntity<?> removeUser(@PathVariable int userNo) {
        Optional<UserDTO> foundUser = users.stream().filter(user -> user.getNo() == userNo).findFirst();

        if (!foundUser.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        users.remove(foundUser.get());

        return ResponseEntity.noContent().build();
    }
}
