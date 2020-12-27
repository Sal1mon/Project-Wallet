package fit.bstu.project_wallet.units;

public class AnalistUnit {
    private String CategoryName;
    private float Value;

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public float getValue() {
        if (Value < 0) {
            Value *= -1;
        }
        return Value;
    }

    public void setValue(float value) {
        Value = value;
    }
}
