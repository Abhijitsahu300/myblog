package com.myblog.payload;


import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


//pojo class
@Data
public class PostDto {
    private long id;
   @NotEmpty(message = "Is mendetary")
   @Size(min=2,message ="post title should have atleast two character")
    private String title;
    @NotEmpty
    @Size(min=10,message ="post description should have atleast ten character")
    private String description;
    @NotEmpty
    @Size(min=10,message ="post content should have atleast ten character")
    private String content;

}
