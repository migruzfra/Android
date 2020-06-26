package es.iessaladillo.pedrojoya.pr01.ui.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import es.iessaladillo.pedrojoya.pr01.R;
import es.iessaladillo.pedrojoya.pr01.bmi.BmiCalculator;
import es.iessaladillo.pedrojoya.pr01.utils.SoftInputUtils;

public class MainActivity extends AppCompatActivity {

    private BmiCalculator bmiCalculator = new BmiCalculator();
    private EditText txtWeight, txtHeight;
    private TextView lblResult;

    private Button btnReset, btnCalculate;
    private ImageView imgBmi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        //inicializamos vistas y funcionalidad
        setupViews();
    }

    private void setupViews() {
        //Asociamos las vistas a cada variable
        txtWeight = ActivityCompat.requireViewById(this, R.id.txtWeight);
        txtHeight = ActivityCompat.requireViewById(this, R.id.txtHeight);
        btnReset = ActivityCompat.requireViewById(this, R.id.btnReset);
        btnCalculate = ActivityCompat.requireViewById(this, R.id.btnCalculate);
        lblResult = ActivityCompat.requireViewById(this, R.id.lblResult);
        imgBmi = ActivityCompat.requireViewById(this, R.id.imgBmi);
        //Indicamos qué tiene que hacer cada botón mediante escuchador de eventos
        btnReset.setOnClickListener(v -> reset());
        btnCalculate.setOnClickListener(v -> analyzeData());
    }


    private void analyzeData() {
        String stringWeight = txtWeight.getText().toString();
        String stringHeight = txtHeight.getText().toString();

        if (isValidForm(stringWeight, stringHeight)) {
            SoftInputUtils.hideKeyboard(this.btnCalculate);
            calculateBmi();
        } else {
            validateWeight(stringWeight);
            validateHeight(stringHeight);
        }
    }

    private void calculateBmi() throws IllegalArgumentException {
        float bmi, weight, height;
        BmiCalculator.BmiClasification bmiClasification;
        weight = Float.valueOf(txtWeight.getText().toString());
        height = Float.valueOf(txtHeight.getText().toString());
        bmi = bmiCalculator.calculateBmi(weight, height);
        bmiClasification = bmiCalculator.getBmiClasification(bmi);
        showBmi(bmi, bmiClasification);
    }

    private void validateWeight(String stringWeight) {
        if (isValidWeight(stringWeight)) {
            txtWeight.setError(null);
        } else {
            txtWeight.setError(getString(R.string.main_invalid_weight));
        }
    }

    private void validateHeight(String stringHeight) {
        if (isValidHeight(stringHeight)) {
            txtHeight.setError(null);
        } else {
            txtHeight.setError(getString(R.string.main_invalid_height));
        }
    }

    private boolean isValidForm(String stringWeight, String stringHeight) {
        return isValidWeight(stringWeight) && isValidHeight(stringHeight);
    }

    //Voy a separar isValidWeight y isValidHeight porque no sé si en el futuro querré validarlos con requisitos diferentes
    private boolean isValidWeight(String stringWeight) {
        return isFloat(stringWeight) && isPositive(Float.parseFloat(stringWeight));
        //Si isFloat sale falso, isPositive ni siquiera lo evalúa así que no problem
    }

    private boolean isValidHeight(String stringHeight) {
        return isFloat(stringHeight) && isPositive(Float.parseFloat(stringHeight));
    }

    private boolean isFloat(String number) {
        try {
            Float.parseFloat(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isPositive(float number) {
        return (number > 0);
    }


    private void showBmi(float bmi, BmiCalculator.BmiClasification bmiClasification) {
        switch (bmiClasification) {
            case LOW_WEIGHT:
                lblResult.setText(getString(R.string.main_bmi, bmi, getString(R.string.main_low_weight)));
                imgBmi.setImageResource(R.drawable.underweight);
                break;

            case NORMAL_WEIGHT:
                lblResult.setText(getString(R.string.main_bmi, bmi, getString(R.string.main_normal_weight)));
                imgBmi.setImageResource(R.drawable.normal_weight);
                break;

            case OVERWWEIGHT:
                lblResult.setText(getString(R.string.main_bmi, bmi, getString(R.string.main_overweight)));
                imgBmi.setImageResource(R.drawable.overweight);
                break;

            case OBESITY_GRADE_1:
                lblResult.setText(getString(R.string.main_bmi, bmi, getString(R.string.main_obesity_grade_1)));
                imgBmi.setImageResource(R.drawable.obesity1);
                break;

            case OBESITY_GRADE_2:
                lblResult.setText(getString(R.string.main_bmi, bmi, getString(R.string.main_obesity_grade_2)));
                imgBmi.setImageResource(R.drawable.obesity2);
                break;

            case OBESITY_GRADE_3:
                lblResult.setText(getString(R.string.main_bmi, bmi, getString(R.string.main_obesity_grade_3)));
                imgBmi.setImageResource(R.drawable.obesity3);
                break;
        }
    }

    private void reset() {
        txtWeight.setText("");
        txtHeight.setText("");
        lblResult.setText("");
        txtWeight.setError(null);
        txtHeight.setError(null);
        imgBmi.setImageResource(R.drawable.bmi);
    }

}
