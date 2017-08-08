package com.pagatodo.apolo.utils;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.pagatodo.apolo.App;
import com.pagatodo.apolo.R;
import com.pagatodo.apolo.utils.customviews.MaterialButton;
import com.pagatodo.apolo.utils.customviews.MaterialValidationEditText;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by flima on 21/02/2017.
 */

public class ValidateForm {

    List<String> listOfPattern = new ArrayList<String>();

    private final static Pattern ptVisa = Pattern.compile("^4[0-9]{6,}$");
    private final static Pattern ptMasterCard = Pattern.compile("^5[1-5][0-9]{5,}$");
    private final static Pattern ptAmeExp = Pattern.compile("^3[47][0-9]{5,}$");

    private final static Pattern ptDinClb = Pattern.compile("^3(?:0[0-5]|[68][0-9])[0-9]{4,}$");
    private final static Pattern ptDiscover = Pattern.compile("^6(?:011|5[0-9]{2})[0-9]{3,}$");
    private final static Pattern ptJcb = Pattern.compile("^(?:2131|1800|35[0-9]{3})[0-9]{3,}$");
    private final static Pattern genericCreditCard = Pattern.compile("^[0-9]{16}$");
    private final static Pattern cablePtrn = Pattern.compile("^[0-9]{18}$");

    private final static Pattern phonePattern = Pattern.compile("^[0-9]{8}|[0-9]{10}$");
    private final static Pattern cellPhonePattern = Pattern.compile("^[0-9]{10}$");
    private final static Pattern number = Pattern.compile("[0-9]+");
    /**
     * ^           # Assert position at the beginning of the string.
     * [0-9]{5}    # Match a digit, exactly five times.
     * (?:         # Group but don't capture:
     * -         #   Match a literal "-".
     * [0-9]{4}  #   Match a digit, exactly four times.
     * )           # End the non-capturing group.
     * ?         #   Make the group optional.
     * $           # Assert position at the end of the string.
     **/
    private final static Pattern zipCodePattern = Pattern.compile("^[0-9]{5}$");

    /*
   *   (			# Start of group
       (?=.*\d)		#   must contains one digit from 0-9
       (?=.*[a-z])		#   must contains one lowercase characters
       (?=.*[A-Z])		#   must contains one uppercase characters
             .		    #     match anything with previous condition checking
               {6,20}	#        length at least 6 characters and maximum of 20
           )			# End of group
   * */
    private final static Pattern passwprdPattern = Pattern.compile("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})");

    private final static Pattern emailPattern = Pattern.compile(
            "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");


    public static boolean isEditTextsFill(EditText... edts) {

        for (EditText edt : edts)
            if (edt.getText().toString().isEmpty())
                return false;

        return true;
    }

    public static String isEditTextEmpty(EditText edt, String message) {

        if (edt.getText().toString().isEmpty())
            return message;

        return "";
    }

    public static boolean isValidEmailAddress(String email) {
        return emailPattern.matcher(email).matches();
    }

    public static boolean isValidPassword(String pass) {
        return passwprdPattern.matcher(pass).matches();
    }

    public static boolean isValidPhone(String phone) {
        return phonePattern.matcher(phone).matches();
    }

    public static boolean isValidCellPhone(String cellPhone) {
        return cellPhonePattern.matcher(cellPhone).matches();
    }
    public static boolean isValidNumber(String compare) {
        return number.matcher(compare).matches();
    }

    public static boolean isValidZipCode(String zipCode) {
        return zipCodePattern.matcher(zipCode).matches();
    }

    public static boolean isValidCreditCard(String card) {
        return genericCreditCard.matcher(card).matches();
    }

    public static boolean isValidCABLE(String cable) {
        return cablePtrn.matcher(cable).matches();
    }

    public static void validateEditText(final MaterialButton button, final MaterialValidationEditText... editTexts){
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                boolean btnEnable = true;
                for(MaterialValidationEditText editText: editTexts){
                    if(!editText.isValidField()){
                        btnEnable = false;
                        break;
                    }
                }
                enableBtn(btnEnable, button);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        for(MaterialValidationEditText editText: editTexts){
            editText.setAddTextChangedListener(textWatcher);
        }
    }
    public static void enableBtn(boolean enable, MaterialButton button){
            if(button != null){
                button.setAlpha(enable ? 1.0f : 0.9f);
                button.setTextColor(enable ? ContextCompat.getColor(App.getInstance(), R.color.white) : ContextCompat.getColor(App.getInstance(), R.color.colorBtn));
                button.setHintTextColor(enable ? ContextCompat.getColor(App.getInstance(), R.color.white) : ContextCompat.getColor(App.getInstance(), R.color.colorBtn));
                button.setNormalBackgroundColor(enable ? ContextCompat.getColor(App.getInstance(), R.color.colorPrimary) : ContextCompat.getColor(App.getInstance(), R.color.teal));
                button.setStrokeNormal(enable ? ContextCompat.getColor(App.getInstance(), R.color.colorPrimary) : ContextCompat.getColor(App.getInstance(), R.color.teal));
                button.setEnabled(enable);
            }
    }
}
