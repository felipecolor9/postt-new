package com.lipsoft.postt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    @NotNull
    @Size(min = 4, max = 20, message = "Username betweem 4 and 20 characters")
    private String username;
    @NotNull
    @Size(min = 4, max = 20, message = "passowrd betweem 8 and 20 characters")
    private String password;
    @NotNull
    @Email(message = "type a valid email")
    private String email;
    @NotNull
    @Min(value = 18, message = "Minimum age: 18y")
    private Integer age;


}
