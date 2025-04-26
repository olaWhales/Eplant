package com.whales.eplant.dto.response.registrationResponse;

import com.whales.eplant.dto.request.registrationRequest.FullName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegistrationResponse {

    private FullName fullName ;
    private String message ;

}
