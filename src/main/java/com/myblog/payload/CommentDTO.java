package com.myblog.payload;

import com.myblog.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentDTO {
    private Long commentId;

    @NotEmpty
    @Size(min=2,message = "body must be atleast 2 characters")
    private String body;
    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    @Size(min=5,message = "name must be atleast 5 characters")
    private String name;

    private Post post;
}
