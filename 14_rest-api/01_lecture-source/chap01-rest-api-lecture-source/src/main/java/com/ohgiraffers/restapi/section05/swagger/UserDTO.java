package com.ohgiraffers.restapi.section05.swagger;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "회원정보 관련 DTO")
public class UserDTO {

    @Schema(description = "회원번호(PK)")
    private int no;

    @Schema(description = "회원 ID")
    private String id;

    @Schema(description = "회원 비밀번호")
    private String pwd;

    @Schema(description = "회원 성명", example = "조평훈")
    private String name;

    @Schema(description = "회원 등록 일시")
    private LocalDate enrollDate;

    public UserDTO() {
    }

    public UserDTO(int no, String id, String pwd, String name, LocalDate enrollDate) {
        this.no = no;
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.enrollDate = enrollDate;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(LocalDate enrollDate) {
        this.enrollDate = enrollDate;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "no=" + no +
                ", id='" + id + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", enrollDate=" + enrollDate +
                '}';
    }
}

