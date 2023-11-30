package com.example.Seulah.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AppDesign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String designName;

    private String signupButtonColor;

    private String loginButtonColor;

    private String headingSize;

    private String headingColor;

    private String textSize;

    private String textColor;

    @OneToMany
    private List<ScreenFlow> screenFlows;

}
