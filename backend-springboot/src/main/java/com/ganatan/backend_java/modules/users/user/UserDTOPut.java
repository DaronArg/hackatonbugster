package com.ganatan.backend_java.modules.users.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTOPut {
    private Long legajo;
    private String email;
    @JsonProperty("github_user")
    private String githubUser;
    private String name;
    @JsonProperty("last_name")
    private String lastName;
    private MultipartFile photo;
}
