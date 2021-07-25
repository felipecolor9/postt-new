package com.lipsoft.postt.dto.request;

import com.lipsoft.postt.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
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
