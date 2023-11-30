package com.example.Seulah.request;

import com.example.Seulah.entity.ScreenFlow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppDesignRequest {
    private String designName;

    private String signupButtonColor;

    private String loginButtonColor;

    private String headingSize;

    private String headingColor;

    private String textSize;

    private String textColor;

    private List<ScreenFlowRequest> screenFlows;
}
