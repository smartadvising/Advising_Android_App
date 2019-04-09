/*
    MAJORITY OF CREDIT FOR THE AUTHENICATION CODE GOES TO Andrew Column AT MICROSOFT 365 ON YOUTUBE
*/
package com.example.advising_app_v4;

import com.microsoft.identity.client.AuthenticationResult;
import com.microsoft.identity.client.exception.MsalException;

public interface MSALAuthenticationCallback {
    void onMsalAuthSuccess(AuthenticationResult authenticationResult);
    void onMsalAuthError(MsalException exception);
    void onMsalAuthError(Exception exception);
    void onMsalAuthCancel();
}
