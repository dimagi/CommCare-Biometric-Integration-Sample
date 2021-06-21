package org.commcare.identity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.commcare.commcaresupportlibrary.identity.IdentityResponseBuilder;
import org.commcare.commcaresupportlibrary.identity.model.IdentificationMatch;
import org.commcare.commcaresupportlibrary.identity.model.MatchResult;
import org.commcare.commcaresupportlibrary.identity.model.MatchStrength;

import java.security.SecureRandom;
import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private final String REGISTRATION_ACTION = "org.commcare.identity.bioenroll";
    private final String VERIFICATION_ACTION = "org.commcare.identity.bioverify";
    private final String IDENTIFICATION_ACTION = "org.commcare.identity.bioidentify";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button returnButton = findViewById(R.id.returnbutton);
        if (getIntent() != null && getIntent().getAction() != null) {
            final String action = getIntent().getAction();
            switch (action) {
                case REGISTRATION_ACTION:
                    showMessage("Beneficiary Registration");
                    break;
                case VERIFICATION_ACTION:
                    showMessage("Beneficiary Verification for beneficiary id " + getIntent().getStringExtra("guid"));
                    break;
                case IDENTIFICATION_ACTION:
                    showMessage("Beneficiary Identification");
                    break;
                default:
                    returnButton.setVisibility(View.GONE);
                    showMessage("Invalid Action");
            }

            returnButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (action) {
                        case REGISTRATION_ACTION:
                            returnFakeRegistrationResult();
                            break;
                        case VERIFICATION_ACTION:
                            returnFakeVerificationResult();
                            break;
                        case IDENTIFICATION_ACTION:
                            returnFakeIdentificationResult();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    /**
     * Constitutes a mock result that can be passed back to CommCare in response to a identification request by CommCare.
     * A Biometric Provider app should match the scanned biometric against all the biometrics stored in their database to form this result
     */
    private void returnFakeIdentificationResult() {
        ArrayList<IdentificationMatch> identifications = new ArrayList<>();
        identifications.add(new IdentificationMatch("1956b120ec4e173ed6a5a160b76fb648", new MatchResult(90, MatchStrength.FIVE_STARS)));
        IdentityResponseBuilder.identificationResponse(identifications).finalizeResponse(this);
    }

    /**
     * Constitutes a mock result that can be passed back to CommCare in response to a verification request by CommCare.
     * A Biometric Provider app should match the scanned biometric with the biometric stored against the given beneficiary id to verify the beneficiary
     */
    private void returnFakeVerificationResult() {
        String beneficiaryId = getIntent().getStringExtra("guid");
        IdentityResponseBuilder.verificationResponse(beneficiaryId, new MatchResult(100, MatchStrength.FIVE_STARS))
                .finalizeResponse(this);
    }

    /**
     * Constitutes a mock result that can be passed back to CommCare in response to a registration request by CommCare.
     * A Biometric Provider app should check for any possible duplicates here and only register the beneficiary's biometric record only when it's unique
     */
    private void returnFakeRegistrationResult() {
        if (noDuplicatesFound()) {
            // A unique id for the the biometric record created by the Biometric Provider when no duplicates
            String newBeneficiaryBiometricId = genGUID(32);
            IdentityResponseBuilder.registrationResponse(newBeneficiaryBiometricId).finalizeResponse(this);
        } else {
            // return the possible duplicates as result
            ArrayList<IdentificationMatch> duplicates = new ArrayList<>();
            duplicates.add(new IdentificationMatch("3c2519e9ff3dd7f0d01d0f58c37281e2", new MatchResult(90, MatchStrength.FIVE_STARS)));
            IdentityResponseBuilder.registrationResponse(duplicates)
                    .finalizeResponse(this);
        }
    }

    private boolean noDuplicatesFound() {
        return true;
    }

    private void showMessage(String message) {
        ((TextView)findViewById(R.id.messagetv)).setText(message);
    }

    public static String genGUID(int len) {
        String guid = "";
        for (int i = 0; i < len; i++) { // 25 == 128 bits of entropy
            guid += Integer.toString( new SecureRandom().nextInt(36), 36);
        }
        return guid.toUpperCase();
    }
}
