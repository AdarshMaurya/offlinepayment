package com.softhinkers_wallet.smartcoinswallet;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.softhinkers.offlinepayment.R;
import com.softhinkers_wallet.models.AccountDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.softhinkers_wallet.utils.TinyDB;

/**
 * Created by adarsh on 05/14/17.
 */
public class PinActivity extends BaseActivity {


   @BindView(R.id.etOldPin)
    EditText etOldPin;

   @BindView(R.id.etPin)
    EditText etPin;

   @BindView(R.id.etPinConfirmation)
    EditText etPinConfirmation;

   @BindView(R.id.btnEdit)
    Button btnEdit;

    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pin);
        ButterKnife.bind(this);
        tinyDB = new TinyDB(getApplicationContext());
        setBackButton(true);
        setTitle(getResources().getString(R.string.app_name));

    }

    @OnClick(R.id.btnEdit)
    public void create(Button button) {
        ArrayList<AccountDetails> accountDetails = tinyDB.getListObject(getString(R.string.pref_wallet_accounts), AccountDetails.class);
        String pin = "";
        for (int i = 0; i < accountDetails.size(); i++) {
            if (accountDetails.get(i).isSelected) {
                pin = accountDetails.get(i).pinCode;
                accountDetails.get(i).pinCode = etPin.getText().toString();
                break;
            }
        }
        if (etOldPin.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.please_enter_old_6_digit_pin, Toast.LENGTH_SHORT).show();
        } else if (etOldPin.getText().length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.old_pin_number_warning, Toast.LENGTH_SHORT).show();
        } else if (etPin.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.please_enter_6_digit_pin, Toast.LENGTH_SHORT).show();
        } else if (etPin.getText().length() < 6) {
            Toast.makeText(getApplicationContext(), R.string.pin_number_warning, Toast.LENGTH_SHORT).show();
        } else if (!etPinConfirmation.getText().toString().equals(etPin.getText().toString())) {
            Toast.makeText(getApplicationContext(), R.string.mismatch_pin, Toast.LENGTH_SHORT).show();
        } else if (!etOldPin.getText().toString().equals(pin)) {
            Toast.makeText(getApplicationContext(), R.string.incorrect_old_pin, Toast.LENGTH_SHORT).show();
        } else {
            tinyDB.putListObject(getString(R.string.pref_wallet_accounts), accountDetails);
            Toast.makeText(getApplicationContext(), R.string.pin_changed_successfully, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
