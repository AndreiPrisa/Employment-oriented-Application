public class Constraint {
    private Double inferiorLimit;
    private Double superiorLimit;

    public Double getInferiorLimit() {
        return inferiorLimit;
    }

    public void setInferiorLimit(Double inferiorLimit) {
        this.inferiorLimit = inferiorLimit;
    }

    public Double getSuperiorLimit() {
        return superiorLimit;
    }

    public void setSuperiorLimit(Double superiorLimit) {
        this.superiorLimit = superiorLimit;
    }

    public Constraint(Double inferiorLimit, Double superiorLimit) {
        this.inferiorLimit = inferiorLimit;
        this.superiorLimit = superiorLimit;
    }

    // functie pentru a verifica daca se respecta constraintul
    public boolean check(Double value) {
        if (this.inferiorLimit == null && this.superiorLimit == null)
            return true;
        if (value == null)
            return false;

        if (this.inferiorLimit == null)
            return value <= this.superiorLimit;
        if (this.superiorLimit == null)
            return value >= this.inferiorLimit;
        return value >= this.inferiorLimit && value <= this.superiorLimit;
    }

    @Override
    public String toString() {
        return "inferior limit is " + inferiorLimit + " and superior limit is " + superiorLimit;
    }
}