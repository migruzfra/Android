package es.iessaladillo.pedrojoya.pr01.bmi;

/**
 * Allow Bmi calculation and clasification
 */
public class BmiCalculator {

    /**
     * @param weightInKgs Weight of the person in kgs
     * @param heightInMeters Height of the person in meters
     * @return The body mass index (BMI)
     */
    public float calculateBmi(float weightInKgs, float heightInMeters) throws IllegalArgumentException {
        if (weightInKgs <= 0 || heightInMeters <= 0) {
            throw new IllegalArgumentException();
        } else {
            return weightInKgs / (float) Math.pow(heightInMeters, 2);
        }
    }

    /**
     * @param bmi Body mass index to get clasification from
     * @return A BmiClasification enum with the clasification of BMI
     */
    public BmiClasification getBmiClasification(float bmi) {
        if (bmi<18.5f) {
            return BmiClasification.LOW_WEIGHT;
        } else if (bmi < 25f) {
            return BmiClasification.NORMAL_WEIGHT;
        } else if (bmi < 30f) {
            return BmiClasification.OVERWWEIGHT;
        } else if (bmi < 35f) {
            return BmiClasification.OBESITY_GRADE_1;
        } else if (bmi < 40f) {
            return BmiClasification.OBESITY_GRADE_2;
        } else {
            return BmiClasification.OBESITY_GRADE_3;
        }
    }

    public enum BmiClasification {
        LOW_WEIGHT, NORMAL_WEIGHT, OVERWWEIGHT, OBESITY_GRADE_1, OBESITY_GRADE_2, OBESITY_GRADE_3
    }
}
