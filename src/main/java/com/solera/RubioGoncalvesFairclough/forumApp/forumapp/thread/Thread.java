package com.solera.RubioGoncalvesFairclough.forumApp.forumapp.thread;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Thread {
    private @Id int id;
    private String title;
    private String creator;
}