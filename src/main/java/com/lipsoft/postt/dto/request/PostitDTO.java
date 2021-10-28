package com.lipsoft.postt.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostitDTO {

    private Long id;
    @NotBlank
    @Size(min = 2, max = 30)
    private String title;
    @NotBlank
    @Size(min = 2)
    private String details;

}
