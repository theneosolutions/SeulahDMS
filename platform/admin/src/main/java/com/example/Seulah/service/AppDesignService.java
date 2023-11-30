package com.example.Seulah.service;

import com.example.Seulah.entity.AppDesign;
import com.example.Seulah.entity.ScreenFlow;
import com.example.Seulah.repository.AppDesignRepository;
import com.example.Seulah.repository.ScreenFlowRepository;
import com.example.Seulah.request.AppDesignRequest;
import com.example.Seulah.request.MessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppDesignService {
    private final AppDesignRepository appDesignRepository;
    private final ScreenFlowRepository screenFlowRepository;

    public AppDesignService(AppDesignRepository appDesignRepository, ScreenFlowRepository screenFlowRepository) {
        this.appDesignRepository = appDesignRepository;
        this.screenFlowRepository = screenFlowRepository;
    }

    public ResponseEntity<MessageResponse> saveAppDesign(AppDesignRequest appDesignRequest) {
        // Create and set values for AppDesign
        AppDesign appDesign = new AppDesign();
        appDesign.setDesignName(appDesignRequest.getDesignName());
        appDesign.setHeadingColor(appDesignRequest.getHeadingColor());
        appDesign.setHeadingSize(appDesignRequest.getHeadingSize());
        appDesign.setTextColor(appDesignRequest.getTextColor());
        appDesign.setTextSize(appDesignRequest.getTextSize());
        appDesign.setLoginButtonColor(appDesignRequest.getLoginButtonColor());
        appDesign.setSignupButtonColor(appDesignRequest.getSignupButtonColor());


        List<ScreenFlow> screenFlows = saveScreenFlow(appDesignRequest);
        appDesign.setScreenFlows(screenFlows);

        appDesign = appDesignRepository.save(appDesign);

        return new ResponseEntity<>(new MessageResponse("Successfully Created App Design", appDesign, false), HttpStatus.CREATED);
    }


    public ResponseEntity<MessageResponse> getAppDesignById(Long id) {
        Optional<AppDesign> appDesign = appDesignRepository.findById(id);
        if (appDesign.isPresent()) {
            return new ResponseEntity<>(new MessageResponse("Success", appDesign, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> getAll() {
        List<AppDesign> appDesignList = appDesignRepository.findAll();
        return new ResponseEntity<>(new MessageResponse("Success", appDesignList, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> deleteById(Long id) {
        Optional<AppDesign> appDesign = appDesignRepository.findById(id);
        if (appDesign.isPresent()) {
            appDesignRepository.delete(appDesign.get());
            return new ResponseEntity<>(new MessageResponse("Success", null, false), HttpStatus.OK);
        }

        return new ResponseEntity<>(new MessageResponse("No Record Found", null, false), HttpStatus.OK);
    }

    public ResponseEntity<MessageResponse> updateById(Long id, AppDesignRequest appDesignRequest) {
        Optional<AppDesign> appDesignOptional = appDesignRepository.findById(id);
        if (appDesignOptional.isPresent()) {
            AppDesign appDesign = appDesignOptional.get();
            if (appDesignRequest.getDesignName() != null && !appDesignRequest.getDesignName().isEmpty()) {
                appDesign.setDesignName(appDesignRequest.getDesignName());
            }
            if (appDesignRequest.getHeadingColor() != null && !appDesignRequest.getHeadingColor().isEmpty()) {
                appDesign.setHeadingColor(appDesignRequest.getHeadingColor());
            }
            if (appDesignRequest.getHeadingSize() != null && !appDesignRequest.getHeadingSize().isEmpty()) {
                appDesign.setHeadingSize(appDesignRequest.getHeadingSize());
            }

            if (appDesignRequest.getTextColor() != null && !appDesignRequest.getTextColor().isEmpty()) {
                appDesign.setTextColor(appDesignRequest.getTextColor());
            }
            if (appDesignRequest.getTextSize() != null && !appDesignRequest.getTextSize().isEmpty()) {
                appDesign.setTextSize(appDesignRequest.getTextSize());
            }
            if (!appDesignRequest.getScreenFlows().isEmpty()) {
                saveScreenFlow(appDesignRequest);
            }
            if (appDesignRequest.getLoginButtonColor() != null && !appDesignRequest.getLoginButtonColor().isEmpty()) {
                appDesign.setLoginButtonColor(appDesignRequest.getLoginButtonColor());
            }
            if (appDesignRequest.getSignupButtonColor() != null && !appDesignRequest.getSignupButtonColor().isEmpty()) {
                appDesign.setSignupButtonColor(appDesignRequest.getSignupButtonColor());
            }

            appDesign = appDesignRepository.save(appDesign);
            return new ResponseEntity<>(new MessageResponse("Successfully Updated", appDesign, false), HttpStatus.OK);
        }
        return new ResponseEntity<>(new MessageResponse("No Record Found Against this Id", null, false), HttpStatus.OK);
    }

    private List<ScreenFlow> saveScreenFlow(AppDesignRequest appDesignRequest) {
        List<ScreenFlow> screenFlows = new ArrayList<>();
        appDesignRequest.getScreenFlows().forEach(screenFlowRequest -> {
            ScreenFlow screenFlow = new ScreenFlow();
            screenFlow.setScreenFlow(screenFlowRequest.getScreenFlow());
            screenFlow.setScreenPicture(screenFlowRequest.getScreenPicture());
            screenFlowRepository.save(screenFlow);
            screenFlows.add(screenFlow);
        });
        return screenFlows;
    }
}
